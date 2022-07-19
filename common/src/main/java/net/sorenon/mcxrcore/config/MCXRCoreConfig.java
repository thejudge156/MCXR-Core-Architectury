package net.sorenon.mcxrcore.config;

public interface MCXRCoreConfig {
    boolean supportsMCXR();

    boolean dynamicPlayerHeight();

    boolean dynamicPlayerEyeHeight();

    boolean thinnerPlayerBoundingBox();

    boolean controllerRaytracing();

    boolean roomscaleMovement();

    //TODO independent look direction
}
