package com.prog.itemOrBlock.tiers;

import com.prog.Prog;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public enum SpecialArmorMaterials implements ArmorMaterial {
    MECHANICAL_BOOTS("MECHANICAL_BOOTS", 0, new int[]{1, 2, 3, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F, 0.0F);

    private final String name;
    private final int durability;
    private final int[] protectionAmounts;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;

    private SpecialArmorMaterials(
            String name,
            int durability,
            int[] protectionAmounts,
            int enchantability,
            SoundEvent equipSound,
            float toughness,
            float knockbackResistance
    ) {
        this.name = name.toLowerCase();
        this.durability = durability;
        this.protectionAmounts = protectionAmounts;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
    }

    @Override
    public int getDurability(EquipmentSlot slot) {
        return this.durability;
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