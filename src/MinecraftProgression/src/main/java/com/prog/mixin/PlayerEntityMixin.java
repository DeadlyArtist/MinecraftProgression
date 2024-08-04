package com.prog.mixin;

import com.prog.event.EntityTickEvents;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(at = @At(value = "HEAD"), method = "tick")
    private void tick(CallbackInfo ci) {
        PlayerEntity entity = (PlayerEntity) (Object) this;
        EntityTickEvents.PLAYER_ENTITY_TICK.invoker().tick(entity);
    }
}