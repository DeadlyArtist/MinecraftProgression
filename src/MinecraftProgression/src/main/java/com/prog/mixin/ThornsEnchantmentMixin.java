package com.prog.mixin;

import com.prog.utils.ThornsUtils;
import net.minecraft.enchantment.ThornsEnchantment;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ThornsEnchantment.class)
public class ThornsEnchantmentMixin {
    @Inject(method = "getDamageAmount", at = @At("HEAD"), cancellable = true)
    private static void getDamageAmount(int level, Random random, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(ThornsUtils.getDamage(level, random));
        cir.cancel();
    }
}
