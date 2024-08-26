package com.prog.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.OrePlacedFeatures;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(PlacedFeatures.class)
public abstract class PlacedFeaturesMixin {

    // Intercept the register method in OrePlacedFeatures
    @Inject(method = "register(Ljava/lang/String;Lnet/minecraft/util/registry/RegistryEntry;Ljava/util/List;)Lnet/minecraft/util/registry/RegistryEntry;", at = @At("HEAD"))
    private static void modifyOreDebrisSmall(String id, RegistryEntry<? extends ConfiguredFeature<?, ?>> registryEntry, List<PlacementModifier> modifiers, CallbackInfoReturnable<RegistryEntry<PlacedFeature>> cir, @Local(argsOnly = true) LocalRef<List<PlacementModifier>> wrappedModifiers) {
        if (true) return;
        if ("ore_debris_small".equals(id)) {
            wrappedModifiers.set(List.of(
                    CountPlacementModifier.of(4),
                    SquarePlacementModifier.of(),
                    HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(128))
            ));
        }
    }
}