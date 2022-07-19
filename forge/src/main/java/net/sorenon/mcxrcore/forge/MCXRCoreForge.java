package net.sorenon.mcxrcore.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.sorenon.mcxrcore.MCXRCore;

@Mod(MCXRCore.MOD_ID)
public class MCXRCoreForge {
    public MCXRCoreForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(MCXRCore.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        new MCXRCore().init();
    }
}
