package com.prog.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.prog.utils.RangedUtils;
import net.minecraft.entity.ai.goal.BowAttackGoal;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BowItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BowAttackGoal.class)
public class BowAttackGoalMixin<T extends LivingEntity> {
    @Redirect(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/BowItem;getPullProgress(I)F"
            )
    )
    private float redirectGetPullProgress(int useTicks, @Local LivingEntity user) {
        return RangedUtils.getPullProgress(user, useTicks);
    }
}
