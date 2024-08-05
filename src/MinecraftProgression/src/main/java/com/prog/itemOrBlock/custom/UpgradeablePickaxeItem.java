package com.prog.itemOrBlock.custom;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.prog.text.PTexts;
import com.prog.utils.UpgradeUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtByte;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpgradeablePickaxeItem extends PickaxeItem {
    public Map<String, NbtElement> upgrades = new HashMap<>();

    public UpgradeablePickaxeItem(ToolMaterial material, int attackDamage, float attackSpeed, Item.Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage + upgrades.size();
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if (slot != EquipmentSlot.MAINHAND) return super.getAttributeModifiers(slot);

        Multimap<EntityAttribute, EntityAttributeModifier> mutableMultimap = LinkedHashMultimap.create(this.attributeModifiers);
        mutableMultimap.removeAll(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        mutableMultimap.put(
                EntityAttributes.GENERIC_ATTACK_DAMAGE,
                new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", (double) getAttackDamage(), EntityAttributeModifier.Operation.ADDITION)
        );

        return ImmutableMultimap.copyOf(mutableMultimap);
    }

    public void updateUpgrades(ItemStack stack) {
        if (!stack.hasNbt()) {
            stack.setNbt(new NbtCompound());
        }

        NbtCompound nbt = stack.getNbt();
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

        UpgradeUtils.addUpgradeTooltip(tooltip, upgrades);
    }
}
