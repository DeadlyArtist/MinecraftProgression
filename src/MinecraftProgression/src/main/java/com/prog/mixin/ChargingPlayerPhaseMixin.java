package com.prog.mixin;

import com.prog.entity.PComponents;
import com.prog.mixinInterfaces.IChargingPlayerPhaseMixin;
import com.prog.utils.DragonUtils;
import com.prog.utils.LOGGER;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.AbstractPhase;
import net.minecraft.entity.boss.dragon.phase.ChargingPlayerPhase;
import net.minecraft.entity.boss.dragon.phase.Phase;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChargingPlayerPhase.class)
public abstract class ChargingPlayerPhaseMixin extends AbstractPhase implements IChargingPlayerPhaseMixin {

    @Unique
    final ChargingPlayerPhase self = (ChargingPlayerPhase) (Object) this;

    @Unique
    LivingEntity target;

    @Unique
    int numberOfTimesShot = 0;

    @Unique
    int ticksSinceLastShot = 0;

    public ChargingPlayerPhaseMixin(EnderDragonEntity dragon) {
        super(dragon);
        numberOfTimesShot = 0;
    }

    @Inject(method = "beginPhase", at = @At("HEAD"))
    private void injectBeginPhase(CallbackInfo ci) {
        numberOfTimesShot = 0;
    }

    @Inject(method = "serverTick", at = @At("HEAD"))
    private void injectServerTarget(CallbackInfo ci) {
        if (self.getPathTarget() == null) return;
        ticksSinceLastShot++;

        if (ticksSinceLastShot <= 60 || numberOfTimesShot > 2) return;
        var fireball = DragonUtils.tryShootFireball(dragon, target);
        if (fireball != null) {
            PComponents.DRAGON_FIREBALL.get(fireball).sizeMultiplier = 2;
            PComponents.DRAGON_FIREBALL.sync(fireball);
            LOGGER.info("BIG BOY");
            numberOfTimesShot++;
            ticksSinceLastShot = 0;
        }
    }

    @Override
    public void setTarget(LivingEntity entity) {
        target = entity;
    }
}
