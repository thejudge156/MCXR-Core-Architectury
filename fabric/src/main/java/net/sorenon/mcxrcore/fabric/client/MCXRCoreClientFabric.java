package net.sorenon.mcxrcore.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.sorenon.mcxrcore.MCXRCore;
import net.sorenon.mcxrcore.config.MCXRCoreConfigImpl;

public class MCXRCoreClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) ->
                ((MCXRCoreConfigImpl) MCXRCore.getCoreConfig()).xrEnabled = false
        );
    }
}
