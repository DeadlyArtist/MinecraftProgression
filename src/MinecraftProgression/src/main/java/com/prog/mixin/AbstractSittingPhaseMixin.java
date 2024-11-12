package com.prog.mixin;

import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.AbstractPhase;
import net.minecraft.entity.boss.dragon.phase.AbstractSittingPhase;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractSittingPhase.class)
public abstract class AbstractSittingPhaseMixin extends AbstractPhase {

    public AbstractSittingPhaseMixin(EnderDragonEntity dragon) {
        super(dragon);
    }

    @Inject(method = "modifyDamageTaken", at = @At("HEAD"), cancellable = true)
    private void injectModifyDamageTaken(DamageSource damageSource, float damage, CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(super.modifyDamageTaken(damageSource, damage));
    }
}
