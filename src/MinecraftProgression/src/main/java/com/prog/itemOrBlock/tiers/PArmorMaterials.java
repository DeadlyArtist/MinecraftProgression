package com.prog.itemOrBlock.tiers;

import com.prog.Prog;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public enum PArmorMaterials implements ArmorMaterial {
    STEEL("STEEL", new int[]{2, 5, 6, 2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0F, 0.0F),
    ULTIMATE_DIAMOND("ULTIMATE_DIAMOND", new int[]{3, 7, 9, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, 0.1F),
    REFINED_OBSIDIAN("REFINED_OBSIDIAN", new int[]{5, 10, 12, 5}, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.0F, 0.2F),
    TITAN("TITAN", new int[]{7, 12, 15, 7}, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 4.0F, 0.2F),
    PRIMAL_NETHERITE("PRIMAL_NETHERITE", new int[]{10, 15, 18, 10}, 15, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 5.0F, 0.3F),
    VERUM("VERUM", new int[]{14, 20, 22, 14}, 20, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 8.0F, 0.3F);

    private final String name;
    private final int[] protectionAmounts;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;

    private PArmorMaterials(
            String name,
            int[] protectionAmounts,
            int enchantability,
            SoundEvent equipSound,
            float toughness,
            float knockbackResistance
    ) {
        this.name = name.toLowerCase();
        this.protectionAmounts = protectionAmounts;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
    }

    @Override
    public int getDurability(EquipmentSlot slot) {
        return 1;
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return this.protectionAmounts[slot.getEntitySlotId()];
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return null;
    }

    @Override
    public String getName() {
        return new Identifier(Prog.MOD_ID, this.name).toString();
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
