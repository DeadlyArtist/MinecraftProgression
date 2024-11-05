package com.prog.mixin;

import com.blakebr0.ironjetpacks.item.JetpackItem;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalDoubleRef;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.prog.Prog;
import com.prog.entity.attribute.PEntityAttributes;
import com.prog.utils.EnchantmentUtils;
import com.prog.utils.LOGGER;
import com.prog.utils.RangedUtils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BowItem.class)
public class BowItemMixin {
    @Redirect(
            method = "onStoppedUsing",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/BowItem;getPullProgress(I)F"
            )
    )
    private float redirectGetPullProgress(int useTicks, @Local LivingEntity user) {
        return RangedUtils.getPullProgress(user, useTicks);
    }

    @Redirect(
            method = "onStoppedUsing",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;setDamage(D)V"
            )
    )
    private void redirectSetDamage(PersistentProjectileEntity projectile, double originalDamage, ItemStack stack) {
        int powerLevel = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
        double modifiedDamage = projectile.getDamage() * EnchantmentUtils.getCommonDamageMultiplier(powerLevel);
        projectile.setDamage(modifiedDamage);
    }


    @Redirect(
            method = "onStoppedUsing",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;setVelocity(Lnet/minecraft/entity/Entity;FFFFF)V"
            )
    )
    private void redirectSetVelocity(PersistentProjectileEntity persistentProjectileEntity, Entity entity, float pitch, float yaw, float roll, float speed, float divergence, @Local float f) {
        var playerEntity = (PlayerEntity) entity;
        persistentProjectileEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, f * 3.0F * (float) playerEntity.getAttributeValue(PEntityAttributes.PROJECTILE_SPEED) / 4, 1.0F);
    }
}
