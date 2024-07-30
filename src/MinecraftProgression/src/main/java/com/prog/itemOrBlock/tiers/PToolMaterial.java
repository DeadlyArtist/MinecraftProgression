package com.prog.itemOrBlock.tiers;

import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

import java.util.function.Supplier;

public enum PToolMaterial implements ToolMaterial {
    STEEL(MiningLevels.IRON, 6.0F, 2.0F, 14);

    private final int miningLevel;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;

    private PToolMaterial(int miningLevel, float miningSpeed, float attackDamage,
                            int enchantability) {
        this.miningLevel = miningLevel;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
    }

    public int getDurability() {
        return 0;
    }

    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    public int getMiningLevel() {
        return this.miningLevel;
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public Ingredient getRepairIngredient() {
        return null;
    }
}