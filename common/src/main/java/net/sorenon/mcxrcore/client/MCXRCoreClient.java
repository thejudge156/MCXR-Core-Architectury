package net.sorenon.mcxrcore.client;

import dev.architectury.networking.NetworkManager;
import net.sorenon.mcxrcore.MCXRCore;
import net.sorenon.mcxrcore.MCXRExpectPlatform;
import net.sorenon.mcxrcore.config.MCXRCoreConfigImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MCXRCoreClient {

    public static MCXRCoreClient INSTANCE;

    private static final Logger LOGGER = LogManager.getLogger("MCXR Core");

    public boolean playInstalled = false;

    public void init() {
        INSTANCE = this;

        playInstalled = MCXRExpectPlatform.isModLoaded("mcxrplay");

        NetworkManager.registerReceiver(NetworkManager.Side.S2C, MCXRCore.S2C_CONFIG, (buf, ctx) -> {
            LOGGER.info("Received login packet");
            buf.writeBoolean(playInstalled);
            ((MCXRCoreConfigImpl) MCXRCore.getCoreConfig()).xrEnabled = true;
        });
    }
}
