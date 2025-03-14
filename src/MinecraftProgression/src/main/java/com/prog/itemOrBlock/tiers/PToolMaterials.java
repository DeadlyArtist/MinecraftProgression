package com.prog.itemOrBlock.tiers;

import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public enum PToolMaterials implements ToolMaterial {
    STEEL(MiningLevels.IRON, 6.0F, -1, 14,
            new ToolStats(7, -2.4F),   // Sword
            new ToolStats(9, -3.1F),   // Axe
            new ToolStats(5, -2.8F),   // Pickaxe
            new ToolStats(4, -3.0F),   // Shovel
            new ToolStats(2, -1.0F)   // Hoe
    ),

    ULTIMATE_DIAMOND(MiningLevels.DIAMOND, 8.0F, -1, 18,
            new ToolStats(10, -2.4F),   // Sword
            new ToolStats(13, -3.0F),   // Axe
            new ToolStats(8, -2.8F),   // Pickaxe
            new ToolStats(7, -3.0F),   // Shovel
            new ToolStats(4, 0.0F)   // Hoe
    ),

    REFINED_OBSIDIAN(PMiningLevels.REFINED_OBSIDIAN, 9.0F, -1, 20,
            new ToolStats(14, -2.4F),   // Sword
            new ToolStats(18, -3.0F),   // Axe
            new ToolStats(12, -2.8F),   // Pickaxe
            new ToolStats(10, -3.0F),   // Shovel
            new ToolStats(6, 0.0F)   // Hoe
    ),

    TITAN(PMiningLevels.TITAN, 10.0F, -1, 24,
            new ToolStats(25, -2.4F),   // Sword
            new ToolStats(30, -3.0F),   // Axe
            new ToolStats(20, -2.8F),   // Pickaxe
            new ToolStats(18, -3.0F),   // Shovel
            new ToolStats(11, 2.0F)   // Hoe
    ),

    PRIMAL_NETHERITE(PMiningLevels.PRIMAL_NETHERITE, 11.0F, -1, 25,
            new ToolStats(32, -2.4F),   // Sword
            new ToolStats(50, -3.0F),   // Axe
            new ToolStats(31, -2.8F),   // Pickaxe
            new ToolStats(26, -3.0F),   // Shovel
            new ToolStats(17, 0.0F)   // Hoe
    ),

    EVERGLOOM(PMiningLevels.EVERGLOOM, 12.0F, -1, 27,
            new ToolStats(61, -2.4F),   // Sword
            new ToolStats(75, -3.0F),   // Axe
            new ToolStats(48, -2.8F),   // Pickaxe
            new ToolStats(41, -3.0F),   // Shovel
            new ToolStats(24, 2.0F)   // Hoe
    ),

    END(PMiningLevels.END, 14.0F, -1, 30,
            new ToolStats(86, -2.4F),   // Sword
            new ToolStats(110, -3.0F),   // Axe
            new ToolStats(71, -2.8F),   // Pickaxe
            new ToolStats(62, -3.0F),   // Shovel
            new ToolStats(34, 2.0F)   // Hoe
    );

    public static class ToolStats {
        public final int damage;
        public final float attackSpeed;

        public ToolStats(int damage, float attackSpeed) {
            this.damage = damage;
            this.attackSpeed = attackSpeed;
        }
    }

    private final int miningLevel;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;

    public final ToolStats sword;
    public final ToolStats axe;
    public final ToolStats pickaxe;
    public final ToolStats shovel;
    public final ToolStats hoe;

    PToolMaterials(int miningLevel, float miningSpeed, float attackDamage, int enchantability,
                   ToolStats sword, ToolStats axe, ToolStats pickaxe, ToolStats shovel, ToolStats hoe) {
        this.miningLevel = miningLevel;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;

        this.sword = sword;
        this.axe = axe;
        this.pickaxe = pickaxe;
        this.shovel = shovel;
        this.hoe = hoe;
    }

    public int getDurability() {
        return 1;
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
