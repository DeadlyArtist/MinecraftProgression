package com.prog.mixin;

import com.prog.mixinInterfaces.IPersistentProjectileEntityMixin;
import com.prog.utils.LOGGER;
import com.prog.utils.RangedUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.predicate.entity.DistancePredicate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin implements IPersistentProjectileEntityMixin {

    @Unique
    private final PersistentProjectileEntity self = (PersistentProjectileEntity) (Object) this;
    @Unique
    public float chargeModifier = 1;
    @Unique
    public ItemStack sourceStack = ItemStack.EMPTY;
    @Unique
    public BlockPos sourcePosition = BlockPos.ORIGIN;

    @Inject(method = "<init>(Lnet/minecraft/entity/EntityType;DDDLnet/minecraft/world/World;)V", at = @At("TAIL"))
    public void injectGetSourcePosition(EntityType type, double x, double y, double z, World world, CallbackInfo ci) {
        this.sourcePosition = self.getBlockPos();
    }

    @Redirect(method = "onEntityHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    protected boolean redirectOnEntityHit(Entity instance, DamageSource source, float amount) {
        var damage = RangedUtils.getChargeDamageIncrease(self.random, self.getDamage(), chargeModifier);
        if (!sourceStack.isEmpty() && sourceStack.getItem() instanceof BowItem && sourcePosition != BlockPos.ORIGIN) {
            var currentPosition = instance.getBlockPos();
            damage = RangedUtils.getDistanceDamageIncrease(sourcePosition, currentPosition, damage);
        }
        return instance.damage(source, (float) damage);
    }

    @Redirect(method = "onEntityHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;isCritical()Z"))
    protected boolean redirectOnEntityHit2(PersistentProjectileEntity instance) {
        return false;
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        this.chargeModifier = nbt.getFloat("chargeModifier");
        this.sourceStack = ItemStack.fromNbt(nbt.getCompound("sourceStack"));
        this.sourcePosition = NbtHelper.toBlockPos(nbt.getCompound("sourcePosition"));
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putFloat("chargeModifier", this.chargeModifier);
        nbt.put("sourceStack", this.sourceStack.writeNbt(new NbtCompound()));
        nbt.put("sourcePosition", NbtHelper.fromBlockPos(this.sourcePosition));
    }

    @Override
    public float getChargeModifier() {
        return chargeModifier;
    }

    @Override
    public void setChargeModifier(float chargeModifier) {
        this.chargeModifier = chargeModifier;
    }

    @Override
    public ItemStack getSourceStack() {
        return this.sourceStack;
    }

    @Override
    public void setSourceStack(ItemStack stack) {
        this.sourceStack = stack;
    }

    @Override
    public BlockPos getSourcePosition() {
        return this.sourcePosition;
    }

    @Override
    public void setSourcePosition(BlockPos blockPos) {
        this.sourcePosition = blockPos;
    }
}
