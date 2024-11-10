package com.prog.mixin.compat.universalenchants;

import com.prog.utils.EnchantmentUtils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import fuzs.universalenchants.handler.ItemCompatHandler;

@Mixin(value = ItemCompatHandler.class, remap = false)
public class ItemCompatHandlerMixin {

    @Inject(method = "applyPowerEnchantment", at = @At("HEAD"), cancellable = true)
    private static void onApplyPowerEnchantment(PersistentProjectileEntity arrow, ItemStack stack, CallbackInfo ci) {
        int powerLevel = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
        double modifiedDamage = arrow.getDamage() * EnchantmentUtils.getCommonDamageMultiplier(powerLevel);
        arrow.setDamage(modifiedDamage);
        ci.cancel();
    }
}