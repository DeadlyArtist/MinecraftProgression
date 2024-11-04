package com.prog.mixin.compat.armordamagescaling;

import com.google.gson.JsonObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.armordamagescale.config.CommonConfiguration;

@Mixin(value = CommonConfiguration.class, remap = false)
public abstract class CommonConfigurationMixin {

    @Inject(method = "serialize", at = @At("HEAD"))
    public void serialize(CallbackInfoReturnable<JsonObject> cir) {
        CommonConfiguration self = (CommonConfiguration) (Object) this;
        self.playerdamageFormula = "damage";
    }
}
