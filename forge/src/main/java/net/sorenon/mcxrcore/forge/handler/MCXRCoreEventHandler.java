package net.sorenon.mcxrcore.forge.handler;

import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sorenon.mcxrcore.MCXRCore;
import net.sorenon.mcxrcore.config.MCXRCoreConfigImpl;

@Mod.EventBusSubscriber
public class MCXRCoreEventHandler {
    @SubscribeEvent
    public static void onDisconnect(ClientPlayerNetworkEvent.LoggingOut event) {
        ((MCXRCoreConfigImpl) MCXRCore.getCoreConfig()).xrEnabled = false;
    }
}
