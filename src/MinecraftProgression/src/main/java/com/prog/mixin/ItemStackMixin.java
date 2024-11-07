package com.prog.mixin;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalDoubleRef;
import com.prog.event.ItemStackEvents;
import com.prog.itemOrBlock.PItemTags;
import com.prog.text.PTexts;
import com.prog.utils.EnchantmentUtils;
import com.prog.utils.LOGGER;
import com.prog.utils.UpgradeUtils;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtByte;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.projectile_damage.internal.Constants;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;
import java.util.stream.Collectors;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Inject(method = "<init>(Lnet/minecraft/item/ItemConvertible;I)V", at = @At("TAIL"))
    private void onConstructorHead(ItemConvertible item, int count, CallbackInfo info) {
        var stack = (ItemStack) (Object) this;
        ItemStackEvents.ITEM_STACK_CTOR.invoker().ctor(stack);
    }

    @Inject(
            method = "getTooltip",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/attribute/EntityAttributeModifier;getId()Ljava/util/UUID;",
                    shift = At.Shift.BEFORE
            )
    )
    private void redirectGetId(@Nullable PlayerEntity player, TooltipContext context, CallbackInfoReturnable<List<Text>> cir, @Local EntityAttributeModifier modifier, @Local LocalDoubleRef d, @Local LocalBooleanRef bl) {
        ItemStack self = (ItemStack) (Object) this;
        var modifierId = modifier.getId();
        if (modifierId.equals(Constants.GENERIC_PROJECTILE_MODIFIER_ID)) {
            d.set(d.get() * EnchantmentUtils.getCommonDamageMultiplier(EnchantmentHelper.getLevel(Enchantments.POWER, self)));
            bl.set(true);
        } else if (Arrays.asList(ArmorItem.MODIFIERS).contains(modifierId)) {
            bl.set(true);
        }
    }

    @Redirect(
            method = "getTooltip",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/enchantment/EnchantmentHelper;getAttackDamage(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityGroup;)F",
                    ordinal = 0
            )
    )
    private float redirectGetAttributeBaseValue(ItemStack stack, EntityGroup group, @Local(ordinal = 0) LocalDoubleRef d) {
        ItemStack self = (ItemStack) (Object) this;
        return (float) EnchantmentUtils.getAttackDamageIncrease(EntityGroup.DEFAULT, self, d.get());
    }

    @Redirect(
            method = "getTooltip",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/nbt/NbtCompound;getBoolean(Ljava/lang/String;)Z"
            )
    )
    private boolean redirectGetUnbreakable(NbtCompound instance, String key) {
        return false;
    }


    @Inject(
            method = "getTooltip",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;hasNbt()Z",
                    ordinal = 1,
                    shift = At.Shift.AFTER
            )
    )
    private void injectInfo(@Nullable PlayerEntity player, TooltipContext context, CallbackInfoReturnable<List<Text>> cir, @Local List<Text> list, @Local(ordinal = 0) int i) {
        var stack = (ItemStack) (Object) this;
        if (!stack.hasNbt()) return;

        if (stack.isSectionVisible(i, ItemStack.TooltipSection.UNBREAKABLE)) {
            List<String> parts = new ArrayList<>();
            var upgradable = stack.isIn(PItemTags.UPGRADABLE);
            if (upgradable || stack.getNbt().getBoolean("Unbreakable")) parts.add(Text.translatable("item.unbreakable").getString());
            if (upgradable || stack.getItem().isFireproof()) parts.add(PTexts.FIREPROOF_TOOLTIP.get().getString());
            if (upgradable) parts.add(PTexts.SOULBOUND_TOOLTIP.get().getString());

            if (!parts.isEmpty()) list.add(Text.literal(String.join(", ", parts)).formatted(Formatting.BLUE));
        }
    }

    @Inject(method = "onCraft", at = @At("HEAD"))
    private void onCraft(World world, PlayerEntity player, int amount, CallbackInfo ci) {
        var stack = (ItemStack) (Object) this;
        if (stack.isIn(PItemTags.UPGRADABLE)) {
            stack.setSubNbt(ItemStack.UNBREAKABLE_KEY, NbtByte.ONE);
            if (stack.getDamage() > 0) {
                stack.setDamage(0);
            }
        }
    }

    @Inject(method = "setNbt", at = @At("TAIL"))
    private void setNbt(NbtCompound nbt, CallbackInfo ci) {
        var stack = (ItemStack) (Object) this;
        if (stack.isIn(PItemTags.UPGRADABLE)) {
            stack.setSubNbt(ItemStack.UNBREAKABLE_KEY, NbtByte.ONE);
            if (stack.getDamage() > 0) {
                stack.setDamage(0);
            }
        }
    }

    @Inject(method = "inventoryTick", at = @At("HEAD"))
    private void inventoryTick(World world, Entity entity, int slot, boolean selected, CallbackInfo ci) {
        var stack = (ItemStack) (Object) this;
        if (stack.isIn(PItemTags.UPGRADABLE)) {
            if (stack.hasNbt()) {
                var nbt = stack.getNbt();
                var keys = nbt.getKeys();
                keys.forEach(key -> {
                    var oldPrefix = "prog_upgrade_";
                    if (key.startsWith(oldPrefix)) {
                        var value = nbt.getCompound(key);
                        nbt.remove(key);
                        value.remove("effects");
                        nbt.put(key.replaceFirst(oldPrefix, UpgradeUtils.UPGRADE_NBT_PREFIX), value);
                    }
                });
            }
        }
    }

    @Inject(method = "getTooltip",
            at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/item/ItemStack;getAttributeModifiers(Lnet/minecraft/entity/EquipmentSlot;)Lcom/google/common/collect/Multimap;", shift = At.Shift.AFTER)
    )
    private void modifyAttributeModifiers(@Nullable PlayerEntity player, TooltipContext context, CallbackInfoReturnable<List<Text>> cir, @Local EquipmentSlot equipmentSlot, @Local Multimap<EntityAttribute, EntityAttributeModifier> multimap) {
        // Create a new multimap after processing the original one
        Multimap<EntityAttribute, EntityAttributeModifier> modifiedMultimap = processAndCombineModifiers(multimap);

        // Clear and replace the original multimap with the modified one
        multimap.clear();
        multimap.putAll(modifiedMultimap);
    }

    /**
     * Combines modifiers with Operation.ADD and excludes those with specific IDs (i.e., ATTACK_DAMAGE and ATTACK_SPEED).
     *
     * @param originalMultimap the original multimap which will be modified
     * @return a new multimap containing the combined modifiers
     */
    @Unique
    private Multimap<EntityAttribute, EntityAttributeModifier> processAndCombineModifiers(Multimap<EntityAttribute, EntityAttributeModifier> originalMultimap) {
        Multimap<EntityAttribute, EntityAttributeModifier> finalMultimap = LinkedHashMultimap.create();

        // Separate the special modifiers and the eligible modifiers
        List<Map.Entry<EntityAttribute, EntityAttributeModifier>> specialModifiers = new ArrayList<>();
        Multimap<EntityAttribute, EntityAttributeModifier> combiningEligible = LinkedHashMultimap.create();

        // Iterate over the original entries to separate special vs. combinable modifiers
        for (Map.Entry<EntityAttribute, EntityAttributeModifier> entry : originalMultimap.entries()) {
            EntityAttribute attribute = entry.getKey();
            EntityAttributeModifier modifier = entry.getValue();

            // Identify special modifiers that should not be combined
            UUID modifierId = modifier.getId();
            if (modifierId.equals(Item.ATTACK_DAMAGE_MODIFIER_ID) ||
                    modifierId.equals(Item.ATTACK_SPEED_MODIFIER_ID) ||
                    modifierId.equals(Constants.GENERIC_PROJECTILE_MODIFIER_ID) ||
                    Arrays.asList(ArmorItem.MODIFIERS).contains(modifierId)) {

                // Special modifiers will be added to the beginning later, store the entry (attribute + modifier)
                specialModifiers.add(entry);

            } else {
                // Eligible for combination
                combiningEligible.put(attribute, modifier);
            }
        }

        // First, add the special modifiers in the expected order
        specialModifiers.forEach(entry -> finalMultimap.put(entry.getKey(), entry.getValue()));

        // Then, combine the eligible modifiers and add them to the map
        combineModifiers(combiningEligible, finalMultimap);

        return finalMultimap;
    }

    /**
     * Combines all eligible modifiers for ADD operation and ensures that they are added properly
     * without mixing with the special ones.
     *
     * @param eligibleMultimap The multimap containing modifiers that are eligible for combination.
     * @param finalMultimap    The final map where accumulated/resultant modifiers should be placed.
     */
    @Unique
    private void combineModifiers(Multimap<EntityAttribute, EntityAttributeModifier> eligibleMultimap, Multimap<EntityAttribute, EntityAttributeModifier> finalMultimap) {

        for (EntityAttribute attribute : eligibleMultimap.keySet()) {
            Collection<EntityAttributeModifier> modifiers = eligibleMultimap.get(attribute);

            // Combine only if there's more than one modifier for this attribute
            if (!modifiers.isEmpty()) {
                double combinedValue = 0.0;

                // Combine the modifiers
                for (EntityAttributeModifier modifier : modifiers) {
                    combinedValue += modifier.getValue();
                }

                // Add the combined value as a new modifier
                // Picking the first one's ID to use for consistency in naming (can adjust as needed)
                EntityAttributeModifier firstModifier = modifiers.iterator().next();
                EntityAttributeModifier combinedModifier = new EntityAttributeModifier(
                        firstModifier.getId(),
                        firstModifier.getName() + "_combined",
                        combinedValue,
                        EntityAttributeModifier.Operation.ADDITION
                );

                // Add to the final multimap
                finalMultimap.put(attribute, combinedModifier);
            }
        }
    }
}
