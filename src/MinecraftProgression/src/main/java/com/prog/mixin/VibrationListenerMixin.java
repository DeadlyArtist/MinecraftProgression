package com.prog.mixin;

import com.prog.utils.LOGGER;
import com.prog.utils.SilentUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.listener.VibrationListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VibrationListener.class)
public class VibrationListenerMixin {

    @Inject(method = "listen(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/world/event/GameEvent$Message;)Z", at = @At("HEAD"), cancellable = true)
    public void listen(ServerWorld world, GameEvent.Message event, CallbackInfoReturnable<Boolean> cir) {
        GameEvent.Emitter emitter = event.getEmitter();
        var entity = emitter.sourceEntity();
        if (entity instanceof LivingEntity living && SilentUtils.isSilent(living)) {
            cir.setReturnValue(false);
        }
    }
}
