package com.prog.mixinInterfaces;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public interface IPersistentProjectileEntityMixin {
    float getChargeModifier();
    void setChargeModifier(float chargeModifier);

    ItemStack getSourceStack();
    void setSourceStack(ItemStack stack);

    BlockPos getSourcePosition();
    void setSourcePosition(BlockPos blockPos);
}
