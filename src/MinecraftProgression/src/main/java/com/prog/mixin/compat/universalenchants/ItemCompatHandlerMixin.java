package com.prog.mixin.compat.universalenchants;

import com.prog.utils.EnchantmentUtils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import fuzs.universalenchants.handler.ItemCompatHandler;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.OptionalInt;

@Mixin(value = ItemCompatHandler.class, remap = false)
public class ItemCompatHandlerMixin {

    @Inject(method = "onArrowLoose", at = @At("HEAD"), cancellable = true)
    private void onArrowLoose(PlayerEntity player, ItemStack stack, World level, int charge, boolean hasAmmo, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "onItemUseTick", at = @At("HEAD"), cancellable = true)
    private void onItemUseTick(LivingEntity entity, ItemStack item, int duration, CallbackInfoReturnable<OptionalInt> cir) {
        cir.setReturnValue(OptionalInt.empty());
    }

    @Inject(method = "applyPowerEnchantment", at = @At("HEAD"), cancellable = true)
    private static void onApplyPowerEnchantment(PersistentProjectileEntity arrow, ItemStack stack, CallbackInfo ci) {
        int powerLevel = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
        double modifiedDamage = arrow.getDamage() * EnchantmentUtils.getCommonDamageMultiplier(powerLevel);
        arrow.setDamage(modifiedDamage);
        ci.cancel();
    }
}