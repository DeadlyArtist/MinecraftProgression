package com.prog.mixin;


import net.minecraft.enchantment.SoulSpeedEnchantment;
import net.minecraft.enchantment.SwiftSneakEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(SoulSpeedEnchantment.class)
public class SoulSpeedEnchantmentMixin {

    @Overwrite
    public boolean isTreasure() {
        return false;
    }
}
