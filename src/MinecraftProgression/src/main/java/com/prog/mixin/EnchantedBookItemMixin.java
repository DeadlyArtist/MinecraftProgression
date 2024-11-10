package com.prog.mixin;

import com.prog.utils.EnchantmentUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.EnchantedBookItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnchantedBookItem.class)
public class EnchantedBookItemMixin {

    @Redirect(method = "appendStacks", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;getMaxLevel()I"))
    public int redirectGetMaxLevel(Enchantment instance) {
        return Math.min(10, EnchantmentUtils.getMaxEnchantmentLevelForAnvil(instance));
    }
}
