package com.prog.mixin.compat.ironjetpacks;

import com.blakebr0.ironjetpacks.crafting.JetpackDynamicRecipeManager;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;

@Mixin(value = JetpackDynamicRecipeManager.class, remap = false)
public abstract class JetpackDynamicRecipeManagerMixin {

    @Inject(method = "appendRecipes", at = @At("HEAD"), cancellable = true)
    private static void injectAppendRecipes(BiConsumer<Identifier, Recipe<?>> appender, CallbackInfo ci) {
        ci.cancel(); // Don't add any default recipes
    }
}
