package com.prog.mixin.compat.ironjetpacks;

import com.blakebr0.ironjetpacks.config.ModJetpacks;
import com.blakebr0.ironjetpacks.registry.Jetpack;
import com.blakebr0.ironjetpacks.registry.JetpackRegistry;
import com.prog.itemOrBlock.PJetpacks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ModJetpacks.class, remap = false)
public class ModJetpacksMixin {
    @Inject(method = "loadJsons", at = @At("HEAD"), cancellable = true)
    private static void cancelLoadJsons(CallbackInfo ci) {
        PJetpacks.registerJetpacks();

        // Don't load any default jetpacks
        ci.cancel();
    }
}