package net.sorenon.mcxrcore.forge;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;
import net.sorenon.mcxrcore.MCXRExpectPlatform;

import java.nio.file.Path;

public class MCXRCoreExpectPlatformImpl {

    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }

    public static float getScale(Entity entity) {
        return 1;
    }

    public static boolean isModLoaded(String modid) {
        return ModList.get().isLoaded(modid);
    }

    public static float getMotionScale(Entity entity) {
        return 1;
    }
}
