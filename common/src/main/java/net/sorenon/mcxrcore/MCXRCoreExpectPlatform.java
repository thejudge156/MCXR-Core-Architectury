package net.sorenon.mcxrcore;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.Entity;

import java.nio.file.Path;

public class MCXRCoreExpectPlatform {

    @ExpectPlatform
    public static Path getConfigDirectory() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static float getScale(Entity entity) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isModLoaded(String modid) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static float getMotionScale(Entity entity) {
        throw new AssertionError();
    }
}
