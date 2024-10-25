package com.prog.mixin;

import com.prog.entity.attribute.PEntityAttributes;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndermanEntity.class)
public abstract class EndermanEntityMixin {
    @Inject(method = "isPlayerStaring", at = @At("HEAD"), cancellable = true)
    private void alwaysStaring(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        if (player.getAttributeValue(PEntityAttributes.ENDERMAN_DISGUISE) == 1) cir.setReturnValue(true);
    }
}