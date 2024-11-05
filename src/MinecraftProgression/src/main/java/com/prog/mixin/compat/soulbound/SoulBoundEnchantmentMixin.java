package com.prog.mixin.compat.soulbound;

import com.imoonday.soulbound.SoulBoundEnchantment;
import com.prog.itemOrBlock.PItemTags;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SoulBoundEnchantment.class, remap = false)
public class SoulBoundEnchantmentMixin {
    @Inject(method = "hasSoulbound", at = @At("HEAD"), cancellable = true)
    private static void injectHasSoulbound(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.isIn(PItemTags.UPGRADABLE)) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}