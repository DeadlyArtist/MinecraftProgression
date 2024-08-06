package com.prog.mixin;

import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ConfiguredFeatures.class)
public class ConfiguredFeaturesMixin {
    @Inject(method = "register*", at = @At("HEAD"))
    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(String id, F feature, FC config, CallbackInfoReturnable<RegistryEntry<ConfiguredFeature<FC, ?>>> cir) {
        if (config instanceof OreFeatureConfig) {
            if ("ore_ancient_debris_large".equals(id) || "ore_ancient_debris_small".equals(id)) {
                ((OreFeatureConfig) config).size = 4;
            }
        }
    }
}
