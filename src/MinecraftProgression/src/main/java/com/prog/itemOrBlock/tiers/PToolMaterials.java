package com.prog.itemOrBlock.tiers;

import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public enum PToolMaterials implements ToolMaterial {
    STEEL(MiningLevels.IRON, 6.0F, 2.0F, 14),
    ULTIMATE_DIAMOND(MiningLevels.DIAMOND, 8.0F, 4.0F, 18),
    REFINED_OBSIDIAN(PMiningLevels.REFINED_OBSIDIAN, 9.0F, 5.0F, 20),
    TITAN(PMiningLevels.TITAN, 10.0F, 7.0F, 24),
    PRIMAL_NETHERITE(PMiningLevels.PRIMAL_NETHERITE, 10.0F, 10.0F, 25);

    private final int miningLevel;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;

    private PToolMaterials(int miningLevel, float miningSpeed, float attackDamage,
                           int enchantability) {
        this.miningLevel = miningLevel;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
    }

    public int getDurability() {
        return 1000000;
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