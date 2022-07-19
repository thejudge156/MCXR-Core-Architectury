package net.sorenon.mcxrcore.fabric;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.Entity;
import net.sorenon.mcxrcore.MCXRCoreExpectPlatform;
import virtuoel.pehkui.api.ScaleTypes;
import virtuoel.pehkui.util.ScaleUtils;

import java.nio.file.Path;

public class MCXRCoreExpectPlatformImpl {
    /**
     * This is our actual method to {@link MCXRCoreExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }

    public static float getScale(Entity entity, float delta) {
        if (FabricLoader.getInstance().isModLoaded("pehkui")) {
            var scaleData = ScaleTypes.BASE.getScaleData(entity);
            return scaleData.getScale(delta);
        } else {
            return 1;
        }
    }

    public static float getMotionScale(Entity entity) {
        if (FabricLoader.getInstance().isModLoaded("pehkui")) {
            return ScaleUtils.getMotionScale(entity);
        } else {
            return 1;
        }
    }

    public static boolean isModLoaded(String modid) {
        return FabricLoader.getInstance().isModLoaded(modid);
    }
}
