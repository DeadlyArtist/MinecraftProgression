package com.prog.mixin;

import com.prog.utils.EnchantmentUtils;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ProtectionEnchantment.class)
public class ProtectionEnchantmentMixin {
    @Inject(method = "getProtectionAmount", at = @At("HEAD"), cancellable = true)
    public void injectGetProtectionAmount(int level, DamageSource source, CallbackInfoReturnable<Integer> cir) {
        var self = (ProtectionEnchantment) (Object) this;
        if (self.protectionType == ProtectionEnchantment.Type.FALL && source.isFromFalling()) {
            cir.setReturnValue(level * EnchantmentUtils.FALL_PROTECTION_MULTIPLIER);
        }
    }
}
