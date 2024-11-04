package com.prog.mixin.compat.ironjetpacks;

import com.blakebr0.ironjetpacks.handler.ColorHandler;
import com.blakebr0.ironjetpacks.item.Colored;
import com.blakebr0.ironjetpacks.registry.Jetpack;
import com.blakebr0.ironjetpacks.registry.JetpackRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.item.ItemConvertible;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;

@Mixin(value = ColorHandler.class, remap = false)
public class ColorHandlerMixin {

    @Inject(method = "onClientSetup", at = @At("HEAD"), cancellable = true)
    private static void injectOnClientSetup(CallbackInfo ci) {
        JetpackRegistry registry = JetpackRegistry.getInstance();
        if (!registry.isErrored()) {
            Iterator<Jetpack> var1 = registry.getAllJetpacks().iterator();

            while (var1.hasNext()) {
                Jetpack jetpack = var1.next();
                ColorHandlerAccessor.getColoredItems().add((ItemConvertible) jetpack.item.get());
            }

            ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
                Colored item = (Colored) stack.getItem();
                return item.getColorTint(tintIndex);
            }, (ItemConvertible[]) ColorHandlerAccessor.getColoredItems().toArray(new ItemConvertible[0]));
        }

        ci.cancel();
    }
}