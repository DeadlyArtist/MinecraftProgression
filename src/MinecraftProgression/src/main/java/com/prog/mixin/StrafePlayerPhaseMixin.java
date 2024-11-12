package com.prog.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.prog.utils.DragonUtils;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.*;
import net.minecraft.entity.projectile.DragonFireballEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StrafePlayerPhase.class)
public abstract class StrafePlayerPhaseMixin extends AbstractPhase {

    @Unique
    final StrafePlayerPhase self = (StrafePlayerPhase) (Object) this;

    @Unique
    int numberOfTimesShot = 0;

    @Unique
    int ticksSinceLastShot = 0;

    public StrafePlayerPhaseMixin(EnderDragonEntity enderDragonEntity) {
        super(enderDragonEntity);
        numberOfTimesShot = 0;
    }

    @Inject(method = "beginPhase", at = @At("HEAD"))
    private void injectBeginPhase(CallbackInfo ci) {
        numberOfTimesShot = 0;
    }

    @Inject(method = "serverTick", at = @At("HEAD"))
    private void injectServerTick(CallbackInfo ci) {
        ticksSinceLastShot++;
    }

    @ModifyConstant(method = "serverTick", constant = @Constant(doubleValue = 4096.0, ordinal = 0))
    private double changeDetectionRadius(double constant) {
        return 16384;
    }

    @ModifyConstant(method = "serverTick", constant = @Constant(floatValue = 10, ordinal = 0))
    private float changeAngle(float constant) {
        return ticksSinceLastShot > (35 - DragonUtils.getPhase(dragon) * 5) ? DragonUtils.MAXIMUM_FIREBALL_ANGLE : -1;
    }

    @Redirect(method = "serverTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/pathing/Path;isFinished()Z", ordinal = 1))
    private boolean redirectIsFinished(Path instance) {
        return true;
    }

    @Redirect(method = "serverTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/boss/dragon/phase/PhaseManager;setPhase(Lnet/minecraft/entity/boss/dragon/phase/PhaseType;)V", ordinal = 1))
    private void redirectSetPhase(PhaseManager instance, PhaseType<?> type) {
        numberOfTimesShot++;
        ticksSinceLastShot = 0;
        if (numberOfTimesShot > 20 + 5 * DragonUtils.getPhase(dragon)) {
            numberOfTimesShot = 0;

            if (self.path != null) {
                while (!self.path.isFinished()) {
                    self.path.next();
                }
            }

            dragon.getPhaseManager().setPhase(PhaseType.HOLDING_PATTERN);
        }
    }
}
