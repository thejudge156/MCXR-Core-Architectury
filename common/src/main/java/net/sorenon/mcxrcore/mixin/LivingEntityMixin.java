package net.sorenon.mcxrcore.mixin;

import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.sorenon.mcxrcore.MCXRCore;
import net.sorenon.mcxrcore.accessor.PlayerExt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Inject(method = "getEyeHeight", at = @At("HEAD"), cancellable = true)
    void overrideEyeHeight(Pose pose, EntityDimensions dimensions, CallbackInfoReturnable<Float> cir) {
        if (!MCXRCore.getCoreConfig().dynamicPlayerEyeHeight()) {
            return;
        }

        if (this instanceof PlayerExt acc && acc.isXR()) {
            cir.setReturnValue(acc.getHeadPose().pos.y - (float) this.position().y);
        }
    }
}
