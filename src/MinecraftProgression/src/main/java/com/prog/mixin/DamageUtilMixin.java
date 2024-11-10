package com.prog.mixin;

import net.minecraft.entity.DamageUtil;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DamageUtil.class)
public class DamageUtilMixin {
    @Inject(method = "getInflictedDamage", at = @At("HEAD"), cancellable = true)
    private static void injectGetInflictedDamage(float damageDealt, float protection, CallbackInfoReturnable<Float> cir) {
        var offset = 40;
        var newDamage = damageDealt * (offset / (offset + protection));
        cir.setReturnValue(newDamage);
    }
}
