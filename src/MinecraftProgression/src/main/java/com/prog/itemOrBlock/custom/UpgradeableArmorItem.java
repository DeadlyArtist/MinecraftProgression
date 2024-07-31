package com.prog.itemOrBlock.custom;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.prog.Prog;
import com.prog.text.PTexts;
import com.prog.utils.StringUtils;
import com.prog.utils.UpgradeUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
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
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.logging.Logger;

public class UpgradeableArmorItem extends ArmorItem {
    public Map<String, NbtElement> upgrades = new HashMap<>();

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
        mutableMultimap.removeAll(EntityAttributes.GENERIC_ARMOR);
        mutableMultimap.put(
                EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(uUID, "Armor modifier", (double) getProtection(), EntityAttributeModifier.Operation.ADDITION)
        );

        return ImmutableMultimap.copyOf(mutableMultimap);
    }

    public void updateUpgrades(ItemStack stack) {
        if (!stack.hasNbt()) {
            stack.setNbt(new NbtCompound());
        }

        NbtCompound nbt = stack.getNbt();
        if (!nbt.contains(ItemStack.UNBREAKABLE_KEY)) nbt.put(ItemStack.UNBREAKABLE_KEY, NbtByte.ONE);
        upgrades = UpgradeUtils.extractUpgradeData(nbt);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient) return;

        updateUpgrades(stack);
    }

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) {
        updateUpgrades(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        if (upgrades.isEmpty()) return;

        tooltip.add(Text.of("\n" + PTexts.UPGRADEABLE_UPGRADE_TOOLTIP.get().getString() + ": " + upgrades.size()));
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.of("    " + String.join(", ", upgrades.values().stream().map(nbt -> UpgradeUtils.getItemFromUpgradeNbt(nbt).getName().getString()).toList())));
        }
    }
}
