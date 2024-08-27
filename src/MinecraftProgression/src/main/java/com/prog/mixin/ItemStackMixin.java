package com.prog.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalDoubleRef;
import com.prog.event.ItemStackEvents;
import com.prog.itemOrBlock.PItemTags;
import com.prog.utils.EnchantmentUtils;
import com.prog.utils.UpgradeUtils;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtByte;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.projectile_damage.internal.Constants;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

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
        return (float) (EnchantmentUtils.getAttackDamage(EntityGroup.DEFAULT, self, d.get()) - d.get());
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
}
