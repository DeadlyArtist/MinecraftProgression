package com.prog.mixin;


import net.minecraft.enchantment.SwiftSneakEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(SwiftSneakEnchantment.class)
public class SwiftSneakEnchantmentMixin {

    @Overwrite
    public boolean isTreasure() {
        return false;
    }
}
