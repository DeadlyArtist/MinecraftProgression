package com.prog.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import com.prog.utils.EnchantmentUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.tag.ItemTags;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.*;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {

    @Shadow
    @Final
    private Property levelCost;

    @Shadow @Final private static Logger LOGGER;

    public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }

    @Inject(method = "updateResult()V", at = @At("TAIL"))
    private void capNameChangeCost(CallbackInfo ci) {
        if (this.input.getStack(1).isEmpty()) {
            levelCost.set(1);
        }
    }

    @ModifyConstant(method = "updateResult", constant = @Constant(intValue = 40, ordinal = 1))
    private int changeMaxLevel(int input) {
        return Integer.MAX_VALUE;
    }

    @ModifyConstant(method = "updateResult", constant = @Constant(intValue = 40, ordinal = 2))
    private int changeMaxLevel2(int input) {
        return Integer.MAX_VALUE;
    }

    @Redirect(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;getMaxLevel()I"))
    private int redirectGetMaxLevel(Enchantment enchantment) {
        return EnchantmentUtils.getMaxEnchantmentLevelForAnvil(enchantment);
    }

    @Inject(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getCount()I", ordinal = 1, shift = At.Shift.BEFORE))
    private void injectUpdateResult(CallbackInfo ci, @Local(ordinal = 0) LocalIntRef costRef, @Local(ordinal = 3) int oldEnchantmentLevel, @Local(ordinal = 4) int newEnchantmentLevel, @Local(ordinal = 5) int s, @Local(ordinal = 0) ItemStack stack, @Local Enchantment enchantment) {
        costRef.set(costRef.get() - newEnchantmentLevel * s); // Undo previous line

        var levelDifference = newEnchantmentLevel - oldEnchantmentLevel;
        var newCost = (int) Math.pow(2, newEnchantmentLevel) - (int) Math.pow(2, oldEnchantmentLevel);
        if (oldEnchantmentLevel == 0) newCost += getBonusCost(enchantment);

        if (stack.isOf(Items.ENCHANTED_BOOK)) newCost = newEnchantmentLevel - oldEnchantmentLevel;
        costRef.set(costRef.get() + newCost);
    }

    @Unique
    private int getBonusCost(Enchantment enchantment) {
        var rarity = enchantment.getRarity();
        var cost = 1;
        if (rarity == Enchantment.Rarity.UNCOMMON) cost = 2;
        if (rarity == Enchantment.Rarity.RARE) cost = 5;
        if (rarity == Enchantment.Rarity.VERY_RARE) cost = 10;

        return cost;
    }
}
