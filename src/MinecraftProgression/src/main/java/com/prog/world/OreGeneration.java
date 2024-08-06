package com.prog.world;

import com.prog.Prog;
import com.prog.itemOrBlock.PBlocks;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

import java.util.Arrays;

public class OreGeneration {

    static {
        register(PBlocks.EMBERITE_ORE, OreConfiguredFeatures.NETHERRACK, 10, 5);
    }

    public static ConfiguredFeature<?, ?> register(Block ore, RuleTest oreRuleTest, int count, int size) {
        String id = ore.asItem().toString() + "_ore_generation";
        var configured = new ConfiguredFeature
                (Feature.ORE, new OreFeatureConfig(
                        oreRuleTest,
                        ore.getDefaultState(),
                        size));
        var placed = new PlacedFeature(
                RegistryEntry.of(configured),
                Arrays.asList(
                        CountPlacementModifier.of(count),
                        SquarePlacementModifier.of(),
                        HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(64))));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,
                new Identifier(Prog.MOD_ID, id), configured);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(Prog.MOD_ID, id),
                placed);
        BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Feature.UNDERGROUND_ORES,
                RegistryKey.of(Registry.PLACED_FEATURE_KEY,
                        new Identifier(Prog.MOD_ID, id)));
        return configured;
    }

    public static void init() {
        Prog.LOGGER.info("Registering Ore Generation for: " + Prog.MOD_ID);
    }
}
