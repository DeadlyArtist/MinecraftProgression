package com.prog.world;

import com.prog.Prog;
import com.prog.itemOrBlock.PBlocks;
import com.prog.utils.LOGGER;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.Blocks;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;
import java.util.function.Predicate;

public class GeodeGeneration {
    public static final RegistryEntry<ConfiguredFeature<?, ?>> STELLAR_GEODE = register(
            "STELLAR_GEODE",
            new GeodeFeatureConfig(
                    new GeodeLayerConfig(
                            BlockStateProvider.of(Blocks.AIR),
                            BlockStateProvider.of(PBlocks.STELLAR_BLOCK),
                            BlockStateProvider.of(PBlocks.BUDDING_STELLAR),
                            BlockStateProvider.of(Blocks.OBSIDIAN),
                            BlockStateProvider.of(Blocks.END_STONE),
                            List.of(
                                    PBlocks.SMALL_STELLAR_BUD.getDefaultState(),
                                    PBlocks.MEDIUM_STELLAR_BUD.getDefaultState(),
                                    PBlocks.LARGE_STELLAR_BUD.getDefaultState(),
                                    PBlocks.STELLAR_CLUSTER.getDefaultState()
                            ),
                            BlockTags.FEATURES_CANNOT_REPLACE,
                            BlockTags.GEODE_INVALID_BLOCKS
                    ),
                    new GeodeLayerThicknessConfig(1.7, 2.2, 3.2, 4.2),
                    new GeodeCrackConfig(0.95, 2.0, 2),
                    0.35,
                    0.083,
                    true,
                    UniformIntProvider.create(4, 6),
                    UniformIntProvider.create(3, 4),
                    UniformIntProvider.create(1, 2),
                    -16,
                    16,
                    0.05,
                    1
            ),
            BiomeSelectors.foundInTheEnd(),
            List.of(RarityFilterPlacementModifier.of(24),
                    SquarePlacementModifier.of(),
                    HeightRangePlacementModifier.uniform(YOffset.aboveBottom(10), YOffset.fixed(30)),
                    BiomePlacementModifier.of())

    );


    public static RegistryEntry<ConfiguredFeature<?, ?>> register(String name, GeodeFeatureConfig featureConfig, Predicate<BiomeSelectionContext> biomeSelector, List<PlacementModifier> placementModifiers) {
        var id = name.toLowerCase();
        var configured = BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(Prog.MOD_ID, id), new ConfiguredFeature<>(Feature.GEODE, featureConfig));
        var placed = new PlacedFeature(
                configured,
                placementModifiers);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(Prog.MOD_ID, id),
                placed);
        BiomeModifications.addFeature(biomeSelector, GenerationStep.Feature.UNDERGROUND_ORES,
                RegistryKey.of(Registry.PLACED_FEATURE_KEY,
                        new Identifier(Prog.MOD_ID, id)));
        return configured;
    }

    public static void init() {
        LOGGER.info("Registering Geode Generation for: " + Prog.MOD_ID);
    }
}
