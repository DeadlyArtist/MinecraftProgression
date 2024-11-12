package com.prog.mixin;

import com.prog.entity.PComponents;
import com.prog.utils.DragonUtils;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.AbstractPhase;
import net.minecraft.entity.boss.dragon.phase.SittingFlamingPhase;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SittingFlamingPhase.class)
public abstract class SittingFlamingPhaseMixin extends AbstractPhase {

    public SittingFlamingPhaseMixin(EnderDragonEntity dragon) {
        super(dragon);
    }

    @Unique
    public int getRank() {
        return PComponents.SQUAD.get(dragon).rank;
    }

    @ModifyConstant(method = "serverTick", constant = @Constant(intValue = 4, ordinal = 0))
    private int changeRepetitionAmount(int constant) {
        return 2;
    }

    @Redirect(method = "serverTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/AreaEffectCloudEntity;addEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;)V"))
    private void redirectAddEffect(AreaEffectCloudEntity instance, StatusEffectInstance effect) {
        instance.addEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 1, 2 + getRank()));
    }
}
