package com.prog.mixin;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalDoubleRef;
import com.prog.client.utils.TooltipUtils;
import com.prog.entity.PComponents;
import com.prog.entity.attribute.PEntityAttributes;
import com.prog.event.ItemStackEvents;
import com.prog.itemOrBlock.PItemTags;
import com.prog.itemOrBlock.custom.TieredCrossbowItem;
import com.prog.itemOrBlock.custom.TieredFishingRodItem;
import com.prog.itemOrBlock.custom.TieredTridentItem;
import com.prog.text.PTexts;
import com.prog.utils.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtByte;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
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

    @Inject(method = "isOf", at = @At("HEAD"), cancellable = true)
    private void injectIsOf(Item item, CallbackInfoReturnable<Boolean> cir) {
        var stack = (ItemStack) (Object) this;

        // bad hack, but might still be better than a dozen mixins
        //if (item == Items.SHEARS && stack.getItem() instanceof ShearsItem) cir.setReturnValue(true);
        if (item == Items.FLINT_AND_STEEL && stack.getItem() instanceof FlintAndSteelItem) cir.setReturnValue(true);
        //if (item == Items.FISHING_ROD && stack.getItem() instanceof FishingRodItem) cir.setReturnValue(true);
        //if (item == Items.TRIDENT && stack.getItem() instanceof TridentItem) cir.setReturnValue(true);
        //if (item == Items.CROSSBOW && stack.getItem() instanceof CrossbowItem) cir.setReturnValue(true);
        //if (item == Items.SHIELD && stack.getItem() instanceof ShieldItem) cir.setReturnValue(true);
    }

    @Inject(
            method = "getTooltip",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/attribute/EntityAttributeModifier;getId()Ljava/util/UUID;",
                    ordinal = 0,
                    shift = At.Shift.BEFORE
            )
    )
    private void injectGetId(@Nullable PlayerEntity player, TooltipContext context, CallbackInfoReturnable<List<Text>> cir, @Local EntityAttributeModifier modifier, @Local LocalDoubleRef d, @Local LocalBooleanRef bl) {
        ItemStack self = (ItemStack) (Object) this;
        var modifierId = modifier.getId();
        if (modifierId.equals(RangedUtils.PROJECTILE_DAMAGE_BASE_MODIFIER_ID)) {
            d.set(d.get() + EnchantmentUtils.getAttackDamageIncrease(EntityGroup.DEFAULT, self, d.get(), true));
            bl.set(true);
        } else if (modifierId.equals(MeleeUtils.SHIELD_BASE_MODIFIER_ID)) {
            bl.set(true);
        } else if (modifierId.equals(RangedUtils.TREASURE_QUALITY_BASE_MODIFIER_ID)) {
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
    private float redirectGetAttributeBaseValue(ItemStack stack, EntityGroup group, @Local(ordinal = 0) double d) {
        ItemStack self = (ItemStack) (Object) this;
        return (float) EnchantmentUtils.getAttackDamageIncrease(EntityGroup.DEFAULT, self, d);
    }

    // Redirect the first list.add with positive "d"
    @Environment(EnvType.CLIENT)
    @Redirect(method = "getTooltip",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 8))
    private boolean redirectBlueText(List<Text> list, Object text, @Local Map.Entry<EntityAttribute, EntityAttributeModifier> entry) {
        return list.add(TooltipUtils.tryAppendDisabled((Text) text, entry.getKey()));
    }

    // Redirect the second list.add with negative "d"
    @Environment(EnvType.CLIENT)
    @Redirect(method = "getTooltip",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 9))
    private boolean redirectRedText(List<Text> list, Object text, @Local Map.Entry<EntityAttribute, EntityAttributeModifier> entry) {
        return list.add(TooltipUtils.tryAppendDisabled((Text) text, entry.getKey()));
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

        if (stack.isSectionVisible(i, ItemStack.TooltipSection.UNBREAKABLE)) {
            List<String> parts = new ArrayList<>();
            var upgradable = stack.isIn(PItemTags.UPGRADABLE);
            if (upgradable || (stack.hasNbt() && stack.getNbt().getBoolean("Unbreakable"))) parts.add(Text.translatable("item.unbreakable").getString());
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

    @Environment(EnvType.CLIENT)
    @Inject(method = "getTooltip",
            at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/item/ItemStack;getAttributeModifiers(Lnet/minecraft/entity/EquipmentSlot;)Lcom/google/common/collect/Multimap;", shift = At.Shift.AFTER)
    )
    private void modifyAttributeModifiers(@Nullable PlayerEntity player, TooltipContext context, CallbackInfoReturnable<List<Text>> cir, @Local EquipmentSlot equipmentSlot, @Local Multimap<EntityAttribute, EntityAttributeModifier> multimap) {
        // Create a new multimap after processing the original one
        Multimap<EntityAttribute, EntityAttributeModifier> modifiedMultimap = TooltipUtils.processAndCombineModifiers(multimap);

        // Clear and replace the original multimap with the modified one
        multimap.clear();
        multimap.putAll(modifiedMultimap);
    }

    @Inject(method = "getRepairCost()I", at = @At("HEAD"), cancellable = true)
    private void removeStoredRepairCost(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(0);
    }
}
