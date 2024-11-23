package com.prog.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import com.prog.utils.EnchantmentUtils;
import com.prog.utils.LOGGER;
import com.prog.utils.RangedUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TridentEntity.class)
public abstract class TridentEntityMixin {

    @Inject(
            method = "<init>(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;)V",
            at = @At("TAIL")
    )
    private void redirectGetAttackDamage(World world, LivingEntity owner, ItemStack stack, CallbackInfo ci) {
        var self = (TridentEntity) (Object) this;
        self.setDamage(RangedUtils.getBaseProjectileDamage(owner, stack));
    }


    @Redirect(
            method = "onEntityHit(Lnet/minecraft/util/hit/EntityHitResult;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/enchantment/EnchantmentHelper;getAttackDamage(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityGroup;)F"
            )
    )
    private float redirectGetAttackDamage(ItemStack stack, EntityGroup group, @Local float f, @Local Entity entity) {
        var self = (TridentEntity) (Object) this;
        var damage = self.getDamage();
        var base = (float) EnchantmentUtils.getAttackDamageIncrease(group, stack, damage, true);
        base *= (float) entity.random.nextTriangular(1, 0.1);
        return (float) (base + damage - f);
    }

    @Inject(method = "tick()V", at = @At("HEAD"))
    private void returnFromVoid(CallbackInfo ci) {
        var self = (TridentEntity) (Object) this;
        var accessor = (TridentEntityAccessor) (Object) this;
        if (self.getDataTracker().get(accessor.getLoyalty()) == 0 || accessor.getDealtDamage()) return;

        if (self.getY() <= self.getWorld().getBottomY()) {
            accessor.setDealtDamage(true);
            self.setVelocity(0, 0, 0);
        }
    }
}