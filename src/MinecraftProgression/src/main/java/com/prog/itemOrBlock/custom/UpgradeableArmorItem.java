package com.prog.itemOrBlock.custom;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.prog.Prog;
import com.prog.utils.UpgradeUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtByte;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class UpgradeableArmorItem extends ArmorItem {
    public List<NbtElement> upgrades = List.of();

    public UpgradeableArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
        this.attributeModifiers = HashMultimap.create(super.getAttributeModifiers(slot));
    }

    @Override
    public int getProtection() {
        return this.protection + upgrades.size();
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if (slot != this.slot) return super.getAttributeModifiers(slot);

        Multimap<EntityAttribute, EntityAttributeModifier> mutableMultimap = LinkedHashMultimap.create(this.attributeModifiers);
        UUID uUID = MODIFIERS[slot.getEntitySlotId()];
        mutableMultimap.removeAll(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        mutableMultimap.put(
                EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(uUID, "Armor modifier", (double) getProtection(), EntityAttributeModifier.Operation.ADDITION)
        );

        return ImmutableMultimap.copyOf(mutableMultimap);
    }

    public void updateUpgrades(ItemStack stack){
        NbtCompound nbt = stack.getNbt();
        upgrades = UpgradeUtils.extractUpgradeData(nbt);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient) return;

        if (!stack.hasNbt()) {
            stack.setNbt(new NbtCompound());
            stack.getNbt().put(ItemStack.UNBREAKABLE_KEY, NbtByte.ONE);
        }

        updateUpgrades(stack);
    }

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) {
        updateUpgrades(stack);
    }
}