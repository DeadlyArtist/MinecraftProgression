package com.prog.mixin.compat.universalenchants;

import fuzs.universalenchants.config.ServerConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ServerConfig.class, remap = false)
public class ServerConfigMixin {

    @Inject(method = "<init>", at = @At("RETURN"))
    private void modifyNoProjectileImmunity(CallbackInfo ci) {
        ServerConfig self = (ServerConfig) (Object) this;
        self.noProjectileImmunity = false;
    }
}
