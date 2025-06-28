package com.prog.mixin.compat.soulbound;

import com.imoonday.soulbound.SoulBoundEnchantment;
import com.prog.itemOrBlock.PItemTags;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SoulBoundEnchantment.class, remap = false)
public class SoulBoundEnchantmentMixin {
    @Inject(method = "hasSoulbound", at = @At("HEAD"), cancellable = true)
    private static void injectHasSoulbound(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.isIn(PItemTags.UPGRADABLE)) {
            cir.setReturnValue(true);
        }
    }

    @Redirect(method = "isAcceptableItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isStackable()Z"))
    private boolean injectIsAcceptableItem(ItemStack instance) {
        return instance.getCount() != 1; // This is negated so only a count of 1 is acceptable (doesn't work though, same as always false, hence hardcoded it into AnvilScreenHandler instead)
    }
}