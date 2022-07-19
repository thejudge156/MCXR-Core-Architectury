package net.sorenon.mcxrcore;

import dev.architectury.networking.NetworkManager;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.sorenon.mcxrcore.accessor.PlayerExt;
import net.sorenon.mcxrcore.config.MCXRCoreConfig;
import net.sorenon.mcxrcore.config.MCXRCoreConfigImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector3f;

public class MCXRCore {

    public static final String MOD_ID = "mcxrcore";

    public static final ResourceLocation S2C_CONFIG = new ResourceLocation("mcxr", "config");

    public static final ResourceLocation IS_XR_PLAYER = new ResourceLocation("mcxr", "is_xr_player");
    public static final ResourceLocation POSES = new ResourceLocation("mcxr", "poses");
    public static final ResourceLocation TELEPORT = new ResourceLocation("mcxr", "teleport");

    public static MCXRCore INSTANCE;

    public static final Logger LOGGER = LogManager.getLogger("MCXR Core");

    public final MCXRCoreConfigImpl config = new MCXRCoreConfigImpl();

    public void init() {
        INSTANCE = this;

        NetworkManager.registerReceiver(NetworkManager.Side.C2S, S2C_CONFIG, (buf, ctx) -> {
            boolean xr = buf.readBoolean();
            var profile = ctx.getPlayer().getGameProfile();
            if (xr) {
                LOGGER.info("Received XR login packet from " + profile.getId());
            } else {
                LOGGER.info("Received login packet from " + profile.getId());
            }
        });

        NetworkManager.registerReceiver(NetworkManager.Side.C2S, IS_XR_PLAYER,
                (buf, ctx) -> {
                    Player player = ctx.getPlayer();
                    boolean isXr = buf.readBoolean();
                    PlayerExt acc = (PlayerExt) player;
                    boolean wasXr = acc.isXR();
                    acc.setIsXr(isXr);
                    if (wasXr && !isXr) {
                        player.refreshDimensions();
                    }
                });

        NetworkManager.registerReceiver(NetworkManager.Side.C2S, POSES,
                (buf, ctx) -> {
                    Player player = ctx.getPlayer();
                    var pose1 = new Pose();
                    var pose2 = new Pose();
                    var pose3 = new Pose();
                    pose1.read(buf);
                    pose2.read(buf);
                    pose3.read(buf);
                    var height = buf.readFloat();
                    setPlayerPoses(player, pose1, pose2, pose3, height, 0);
                });

        NetworkManager.registerReceiver(NetworkManager.Side.C2S, TELEPORT,
                (buf, ctx) -> {
                    Player player = ctx.getPlayer();
                    PlayerExt acc = (PlayerExt) player;
                    Pose pose;

                    if (player.getMainArm() == HumanoidArm.LEFT) {
                        pose = acc.getRightHandPose();
                    } else {
                        pose = acc.getLeftHandPose();
                    }

                    Vector3f dir = pose.getOrientation().transform(new Vector3f(0, -1, 0));

                    var pos = Teleport.tp(player, JOMLUtil.convert(pose.getPos()), JOMLUtil.convert(dir));
                    if (pos != null) {
                        player.setPos(pos);
                    } else {
                        LOGGER.warn("Player {} attempted an invalid teleport", player.toString());
                    }
                });
    }

    public void setPlayerPoses(Player player,
                               Pose headPose,
                               Pose leftHandPose,
                               Pose rightHandPose,
                               float height,
                               float stoopid) {
        PlayerExt acc = (PlayerExt) player;
        acc.getHeadPose().set(headPose);
        acc.getLeftHandPose().set(leftHandPose);
        acc.getRightHandPose().set(rightHandPose);
        acc.setHeight(height);

        if (stoopid != 0) {
            acc.getLeftHandPose().orientation.rotateX(stoopid);
            acc.getRightHandPose().orientation.rotateX(stoopid);

            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            acc.getHeadPose().write(buf);
            acc.getLeftHandPose().write(buf);
            acc.getRightHandPose().write(buf);
            buf.writeFloat(height);

            NetworkManager.sendToServer(POSES, buf);
        }
    }

    public static MCXRCoreConfig getCoreConfig() {
        return INSTANCE.config;
    }

    public static HumanoidArm handToArm(LivingEntity entity, InteractionHand hand) {
        if (hand == InteractionHand.MAIN_HAND) {
            return entity.getMainArm();
        } else {
            return entity.getMainArm().getOpposite();
        }
    }
}
