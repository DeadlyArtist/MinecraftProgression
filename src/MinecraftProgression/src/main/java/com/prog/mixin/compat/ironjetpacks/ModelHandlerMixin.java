package com.prog.mixin.compat.ironjetpacks;

import com.blakebr0.ironjetpacks.client.ModelHandler;
import com.blakebr0.ironjetpacks.registry.JetpackRegistry;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@Mixin(value = ModelHandler.class, remap = false)
public class ModelHandlerMixin {

    @Inject(method = "onClientSetup", at = @At("HEAD"), cancellable = true)
    private static void onClientSetup(CallbackInfo ci) {
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
            out.accept(new ModelIdentifier(new Identifier("iron-jetpacks", "jetpack"), "inventory"));
        });
        Identifier jetpack = new Identifier("iron-jetpacks", "item/jetpack");
        Map<ModelIdentifier, UnbakedModel> modelMap = Maps.newHashMap();
        JetpackRegistry.getInstance().getAllJetpacks().forEach((pack) -> {
            Identifier jetpackLocation = Registry.ITEM.getId((Item) pack.item.get());
            if (jetpackLocation != null) {
                ModelIdentifier locationxxx = new ModelIdentifier(jetpackLocation, "inventory");
                provideModel(modelMap, locationxxx, jetpack);
            }

        });
        ModelLoadingRegistry.INSTANCE.registerVariantProvider((resourceManager) -> {
            return (modelIdentifier, modelProviderContext) -> {
                return (UnbakedModel) modelMap.get(modelIdentifier);
            };
        });

        ci.cancel(); // Overwrite
    }

    @Unique
    private static void provideModel(Map<ModelIdentifier, UnbakedModel> modelMap, ModelIdentifier modelIdentifier, final Identifier redirectedId) {
        modelMap.put(modelIdentifier, new UnbakedModel() {
            public Collection<Identifier> getModelDependencies() {
                return Collections.emptyList();
            }

            public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences) {
                return Collections.emptyList();
            }

            public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
                return loader.bake(redirectedId, rotationContainer);
            }
        });
    }
}
