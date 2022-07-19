package net.sorenon.mcxrcore.fabric;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerLoginConnectionEvents;
import net.minecraft.network.FriendlyByteBuf;
import net.sorenon.mcxr.core.MCXRCore;

import static net.sorenon.mcxr.core.MCXRCore.S2C_CONFIG;

public class MCXRCoreFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        MCXRCore instance = new MCXRCore();
        instance.init();

        ServerLoginConnectionEvents.QUERY_START.register((handler, server, sender, synchronizer) -> {
            MCXRCore.LOGGER.debug("Sending login packet to " + handler.getUserName());
            var buf = new FriendlyByteBuf(Unpooled.buffer());
            sender.sendPacket(S2C_CONFIG, buf);
        });
    }
}
