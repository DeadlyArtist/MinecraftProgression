package com.prog.mixin.compat.rei;

import com.prog.compat.rei.client.PREIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.plugin.client.DefaultClientPlugin;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(DefaultClientPlugin.class)
public abstract class DefaultClientPluginMixin {
    @Shadow public abstract void registerCategories(CategoryRegistry registry);

    @Inject(method = "registerCategories", at = @At(value = "INVOKE", target = "Lme/shedaniel/rei/api/client/registry/category/CategoryRegistry;addWorkstations(Lme/shedaniel/rei/api/common/category/CategoryIdentifier;[Lme/shedaniel/rei/api/common/entry/EntryStack;)V"),
            remap = false)
    private void afterAddWorkstations(CategoryRegistry registry, CallbackInfo info) {
        PREIClientPlugin.registerCategoriesExtern(registry);
    }
}
