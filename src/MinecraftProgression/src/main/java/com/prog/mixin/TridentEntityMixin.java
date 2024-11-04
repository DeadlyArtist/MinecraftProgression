package com.prog.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import com.prog.utils.EnchantmentUtils;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TridentEntity.class)
public abstract class TridentEntityMixin {

    @Redirect(
            method = "onEntityHit(Lnet/minecraft/util/hit/EntityHitResult;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/enchantment/EnchantmentHelper;getAttackDamage(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityGroup;)F"
            )
    )
    private float redirectGetAttackDamage(ItemStack stack, EntityGroup group, @Local float f) {
        return (float) EnchantmentUtils.getAttackDamageIncrease(group, stack, f);
    }
}