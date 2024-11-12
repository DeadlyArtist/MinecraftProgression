package com.prog.mixin;

import com.prog.mixinInterfaces.IChargingPlayerPhaseMixin;
import com.prog.utils.DragonUtils;
import com.prog.utils.LOGGER;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.AbstractPhase;
import net.minecraft.entity.boss.dragon.phase.HoldingPatternPhase;
import net.minecraft.entity.boss.dragon.phase.PhaseManager;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HoldingPatternPhase.class)
public abstract class HoldingPatternPhaseMixin extends AbstractPhase {

    public HoldingPatternPhaseMixin(EnderDragonEntity dragon) {
        super(dragon);
    }

    @Redirect(method = "tickInRange", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/pathing/Path;isFinished()Z", ordinal = 0))
    private boolean redirectIsFinished(Path instance) {
        var phase = DragonUtils.getPhase(dragon);
        return phase == DragonUtils.HIGHEST_PHASE || dragon.random.nextInt(DragonUtils.HIGHEST_PHASE - phase) == 0 || instance.isFinished();
    }

    @Redirect(method = "tickInRange", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/random/Random;nextInt(I)I", ordinal = 0))
    private int redirectNextInt(Random instance, int i) {
        return 0;
    }

    @Redirect(method = "tickInRange", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/boss/dragon/phase/PhaseManager;setPhase(Lnet/minecraft/entity/boss/dragon/phase/PhaseType;)V"))
    private void redirectSetPhase(PhaseManager instance, PhaseType<?> type) {
        PlayerEntity player = this.dragon.world.getClosestPlayer(dragon, 128);
        if (player == null) return;

        var random = dragon.getRandom().nextInt(9);
        if (random == 0) {
            dragon.getPhaseManager().setPhase(PhaseType.LANDING_APPROACH);
        } else if (random < 6) {
            dragon.getPhaseManager().setPhase(PhaseType.CHARGING_PLAYER);
            var phase = dragon.getPhaseManager().create(PhaseType.CHARGING_PLAYER);
            phase.setPathTarget(new Vec3d(player.getX(), player.getY(), player.getZ()));
            ((IChargingPlayerPhaseMixin)(Object)phase).setTarget(player);
        } else {
            dragon.getPhaseManager().setPhase(PhaseType.STRAFE_PLAYER);
            dragon.getPhaseManager().create(PhaseType.STRAFE_PLAYER).setTargetEntity(player);
        }
    }
}
