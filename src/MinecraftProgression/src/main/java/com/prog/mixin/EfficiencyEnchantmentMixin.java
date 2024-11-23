package com.prog.mixin;

import net.minecraft.enchantment.EfficiencyEnchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EfficiencyEnchantment.class)
public class EfficiencyEnchantmentMixin {
    @Redirect(method = "isAcceptableItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 0))
    public boolean redirectIsOf(ItemStack instance, Item item) {
        return instance.getItem() instanceof ShearsItem;
    }
}
