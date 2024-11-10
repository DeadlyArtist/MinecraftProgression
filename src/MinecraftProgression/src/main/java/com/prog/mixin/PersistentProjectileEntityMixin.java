package com.prog.mixin;

import com.prog.mixinInterfaces.IPersistentProjectileEntityMixin;
import com.prog.utils.RangedUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin implements IPersistentProjectileEntityMixin {

    @Unique
    public float chargeModifier = 1;

    @Redirect(method = "onEntityHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    protected boolean redirectOnEntityHit(Entity instance, DamageSource source, float amount) {
        var self = (PersistentProjectileEntity) (Object) this;
        var damage = RangedUtils.getChargeDamageIncrease(self.random, self.getDamage(), chargeModifier);
        return instance.damage(source, (float) damage);
    }

    @Redirect(method = "onEntityHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;isCritical()Z"))
    protected boolean redirectOnEntityHit2(PersistentProjectileEntity instance) {
        return false;
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        this.chargeModifier = nbt.getFloat("chargeModifier");
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putFloat("chargeModifier", this.chargeModifier);
    }

    @Override
    public float getChargeModifier() {
        return chargeModifier;
    }

    @Override
    public void setChargeModifier(float chargeModifier) {
        this.chargeModifier = chargeModifier;
    }
}
