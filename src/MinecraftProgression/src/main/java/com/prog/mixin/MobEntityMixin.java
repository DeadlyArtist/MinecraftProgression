package com.prog.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.prog.entity.PComponents;
import com.prog.utils.EnchantmentUtils;
import com.prog.utils.LOGGER;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin {

    @Shadow public int experiencePoints;

    @Redirect(
            method = "tryAttack(Lnet/minecraft/entity/Entity;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/enchantment/EnchantmentHelper;getAttackDamage(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityGroup;)F"
            )
    )
    private float redirectGetAttackDamage(ItemStack stack, EntityGroup group, @Local LocalRef<Entity> entity, @Local(ordinal = 0) LocalFloatRef f) {
        return (float) EnchantmentUtils.getAttackDamage(group, stack, f.get()) - f.get();
    }

    @Inject(at = @At("HEAD"), method = "getXpToDrop")
    protected void multiplyXpDrop(CallbackInfoReturnable<Integer> cir) {
        MobEntity self = (MobEntity) (Object) this;
        var squad = PComponents.SQUAD.get(self);
        self.experiencePoints *= (int) Math.pow(2, squad.rank);
    }
}