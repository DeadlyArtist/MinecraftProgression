package com.prog.itemOrBlock.custom;

import com.prog.itemOrBlock.tiers.ShearsMaterial;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.tag.BlockTags;

public class TieredShearsItem extends ShearsItem {
    public ShearsMaterial material;

    public TieredShearsItem(ShearsMaterial material, Settings settings) {
        super(settings);
        this.material = material;
        this.maxCount = 1;
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        return super.getMiningSpeedMultiplier(stack, state) + material.getMiningSpeedBonus();
    }
}
