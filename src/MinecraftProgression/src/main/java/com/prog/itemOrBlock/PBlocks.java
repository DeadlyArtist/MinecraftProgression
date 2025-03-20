package com.prog.itemOrBlock;

import com.prog.Prog;
import com.prog.data.PBlockLootTableProvider;
import com.prog.itemOrBlock.custom.FlexibleBuddingBlock;
import com.prog.itemOrBlock.custom.FlexibleCookingBlock;
import com.prog.itemOrBlock.custom.FlexibleCraftingBlock;
import com.prog.itemOrBlock.data.FlexibleCookingData;
import com.prog.itemOrBlock.data.FlexibleCraftingData;
import com.prog.utils.LOGGER;
import com.prog.utils.StringUtils;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.TexturedModel;
import net.minecraft.data.server.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;

public class PBlocks {
    public static class BlockData {
        public String name;
        public Item blockItem;
        public Consumer<BlockStateModelGenerator> modelSupplier;
        public Consumer<PBlockLootTableProvider> lootSupplier;
        public List<TagKey<Block>> tags;

        public BlockData(String name, Item blockItem, Consumer<BlockStateModelGenerator> modelSupplier, Consumer<PBlockLootTableProvider> lootSupplier, @Nullable List<TagKey<Block>> tags) {
            this.name = name;
            this.blockItem = blockItem;
            this.modelSupplier = modelSupplier;
            this.lootSupplier = lootSupplier;
            this.tags = tags == null ? List.of() : tags;
        }
    }

    public static final Map<Block, PBlocks.BlockData> data = new HashMap<>();

    // Machines
    public static final Block ASSEMBLY = register("ASSEMBLY", new FlexibleCraftingBlock(FlexibleCraftingData.ASSEMBLY, FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F).requiresTool()), ItemGroup.DECORATIONS, (modelSupplier, self) -> modelSupplier.registerSingleton(self, TexturedModel.ORIENTABLE_WITH_BOTTOM), (lootProvider, self) -> lootProvider.addDrop(self)).tags(List.of(BlockTags.PICKAXE_MINEABLE, BlockTags.NEEDS_DIAMOND_TOOL)).finished();
    public static final Block COSMIC_CONSTRUCTOR = register("COSMIC_CONSTRUCTOR", new FlexibleCraftingBlock(FlexibleCraftingData.COSMIC_CONSTRUCTOR, FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F).requiresTool()), ItemGroup.DECORATIONS, (modelSupplier, self) -> modelSupplier.registerSingleton(self, TexturedModel.CUBE_BOTTOM_TOP), (lootProvider, self) -> lootProvider.addDrop(self)).tags(List.of(BlockTags.PICKAXE_MINEABLE, PBlockTags.NEEDS_PRIMAL_NETHERITE_TOOL, BlockTags.DRAGON_IMMUNE)).finished();
    public static final Block INCINERATOR = register("INCINERATOR", new FlexibleCookingBlock(FlexibleCookingData.INCINERATOR, FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F).requiresTool().luminance(createLightLevelFromLitBlockState(13))), ItemGroup.DECORATIONS, (modelSupplier, self) -> modelSupplier.registerCooker(self, TexturedModel.ORIENTABLE), (lootProvider, self) -> lootProvider.addDrop(self)).tags(List.of(BlockTags.PICKAXE_MINEABLE, BlockTags.NEEDS_DIAMOND_TOOL)).finished();
    public static final Block COSMIC_INCUBATOR = register("COSMIC_INCUBATOR", new FlexibleCookingBlock(FlexibleCookingData.COSMIC_INCUBATOR, FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F).requiresTool().luminance(createLightLevelFromLitBlockState(13))), ItemGroup.DECORATIONS, (modelSupplier, self) -> modelSupplier.registerCooker(self, TexturedModel.ORIENTABLE), (lootProvider, self) -> lootProvider.addDrop(self)).tags(List.of(BlockTags.PICKAXE_MINEABLE, PBlockTags.NEEDS_PRIMAL_NETHERITE_TOOL, BlockTags.DRAGON_IMMUNE)).finished();

    // Materials
    public static final Block STEEL_BLOCK = register("STEEL_BLOCK", new Block(FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F).requiresTool()), ItemGroup.BUILDING_BLOCKS, (modelSupplier, self) -> modelSupplier.registerCubeAllModelTexturePool(self), (lootProvider, self) -> lootProvider.addDrop(self)).tags(List.of(BlockTags.PICKAXE_MINEABLE, BlockTags.NEEDS_IRON_TOOL)).finished();
    public static final Block MACHINE_FRAME = register("MACHINE_FRAME", new Block(FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F).requiresTool()), ItemGroup.BUILDING_BLOCKS, (modelSupplier, self) -> modelSupplier.registerCubeAllModelTexturePool(self), (lootProvider, self) -> lootProvider.addDrop(self)).tags(List.of(BlockTags.PICKAXE_MINEABLE, BlockTags.NEEDS_IRON_TOOL)).finished();
    public static final Block QUARTZ_CATALYST = register("QUARTZ_CATALYST", new Block(FabricBlockSettings.of(Material.AMETHYST).strength(3.0F, 3.0F).requiresTool()), ItemGroup.BUILDING_BLOCKS, (modelSupplier, self) -> modelSupplier.registerCubeAllModelTexturePool(self), (lootProvider, self) -> lootProvider.addDrop(self)).tags(List.of(BlockTags.PICKAXE_MINEABLE, BlockTags.NEEDS_DIAMOND_TOOL)).finished();
    public static final Block EMBERITE_BLOCK = register("EMBERITE_BLOCK", new Block(FabricBlockSettings.of(Material.FIRE).strength(5.0F, 8.0F).requiresTool().luminance(5)), ItemGroup.BUILDING_BLOCKS, (modelSupplier, self) -> modelSupplier.registerCubeAllModelTexturePool(self), (lootProvider, self) -> lootProvider.addDrop(self)).tags(List.of(BlockTags.PICKAXE_MINEABLE, PBlockTags.NEEDS_REFINED_OBSIDIAN_TOOL)).finished();
    public static final Block EMBERITE_ORE = register("EMBERITE_ORE", new Block(FabricBlockSettings.of(Material.FIRE).strength(5.0F, 8.0F).requiresTool().luminance(5)), ItemGroup.BUILDING_BLOCKS, (modelSupplier, self) -> modelSupplier.registerCubeAllModelTexturePool(self), (lootProvider, self) -> lootProvider.addDrop(self, block -> BlockLootTableGenerator.oreDrops(block, PItems.EMBERITE))).tags(List.of(BlockTags.PICKAXE_MINEABLE, PBlockTags.NEEDS_REFINED_OBSIDIAN_TOOL)).finished();
    public static final Block PURE_EVERGLOOM_BLOCK = register("PURE_EVERGLOOM_BLOCK", new Block(FabricBlockSettings.of(Material.METAL).strength(5.0F, 10.0F).requiresTool().luminance(5)), ItemGroup.BUILDING_BLOCKS, (modelSupplier, self) -> modelSupplier.registerCubeAllModelTexturePool(self), (lootProvider, self) -> lootProvider.addDrop(self)).tags(List.of(BlockTags.PICKAXE_MINEABLE, PBlockTags.NEEDS_EVERGLOOM_TOOL, BlockTags.DRAGON_IMMUNE)).finished();
    public static final Block EVERGLOOM = register("EVERGLOOM", new Block(FabricBlockSettings.of(Material.STONE).strength(5.0F, 10.0F).requiresTool().luminance(5)), ItemGroup.BUILDING_BLOCKS, (modelSupplier, self) -> modelSupplier.registerCubeAllModelTexturePool(self), (lootProvider, self) -> lootProvider.addDrop(self)).tags(List.of(BlockTags.PICKAXE_MINEABLE, PBlockTags.NEEDS_PRIMAL_NETHERITE_TOOL, BlockTags.DRAGON_IMMUNE)).finished();
    public static final Block VERUM_BLOCK = register("VERUM_BLOCK", new Block(FabricBlockSettings.of(Material.METAL).strength(5.0F, 12.0F).requiresTool().luminance(5)), ItemGroup.BUILDING_BLOCKS, (modelSupplier, self) -> modelSupplier.registerCubeAllModelTexturePool(self), (lootProvider, self) -> lootProvider.addDrop(self)).tags(List.of(BlockTags.PICKAXE_MINEABLE, PBlockTags.NEEDS_END_TOOL, BlockTags.DRAGON_IMMUNE)).finished();
    public static final Block VERUM_ORE = register("VERUM_ORE", new Block(FabricBlockSettings.of(Material.STONE).strength(5.0F, 12.0F).requiresTool().luminance(5)), ItemGroup.BUILDING_BLOCKS, (modelSupplier, self) -> modelSupplier.registerCubeAllModelTexturePool(self), (lootProvider, self) -> lootProvider.addDrop(self, block -> BlockLootTableGenerator.oreDrops(block, PItems.RAW_VERUM))).tags(List.of(BlockTags.PICKAXE_MINEABLE, PBlockTags.NEEDS_EVERGLOOM_TOOL, BlockTags.DRAGON_IMMUNE)).finished();
    // Not needed lol
//    public static final Block ARTIFICIAL_DRAGON_EGG = register("ARTIFICIAL_DRAGON_EGG", new DragonEggBlock(FabricBlockSettings.of(Material.EGG, MapColor.BLACK).strength(3.0F, 10000.0F).luminance(state -> 1).nonOpaque()), ItemGroup.MISC, (modelSupplier, self) -> modelSupplier.registerSimpleState(self), (lootProvider, self) -> lootProvider.addDrop(self)).itemSettings(s -> s.rarity(Rarity.RARE)).finished();

    // Misc
    public static final Block NETHER_STAR_BLOCK = register("NETHER_STAR_BLOCK", new Block(FabricBlockSettings.of(Material.METAL).strength(5.0F, 10.0F).requiresTool().luminance(5)), ItemGroup.BUILDING_BLOCKS, (modelSupplier, self) -> modelSupplier.registerCubeAllModelTexturePool(self), (lootProvider, self) -> lootProvider.addDrop(self)).tags(List.of(BlockTags.PICKAXE_MINEABLE, PBlockTags.NEEDS_REFINED_OBSIDIAN_TOOL, BlockTags.WITHER_IMMUNE)).finished();

    // Stellar
    public static final Block STELLAR_BLOCK = register("STELLAR_BLOCK", new AmethystBlock(AbstractBlock.Settings.of(Material.AMETHYST, MapColor.BLUE).strength(1.5F).sounds(BlockSoundGroup.AMETHYST_BLOCK).requiresTool()), ItemGroup.BUILDING_BLOCKS, (modelSupplier, self) -> modelSupplier.registerCubeAllModelTexturePool(self), (lootProvider, self) -> lootProvider.addDrop(self)).tags(List.of(BlockTags.PICKAXE_MINEABLE, PBlockTags.NEEDS_END_TOOL, BlockTags.CRYSTAL_SOUND_BLOCKS)).finished();
    public static final Block BUDDING_STELLAR = register("BUDDING_STELLAR", new FlexibleBuddingBlock(__pblocks_helper.__SMALL_STELLAR_BUD, __pblocks_helper.__MEDIUM_STELLAR_BUD, __pblocks_helper.__LARGE_STELLAR_BUD, __pblocks_helper.__STELLAR_CLUSTER, AbstractBlock.Settings.of(Material.AMETHYST).ticksRandomly().strength(1.5F).sounds(BlockSoundGroup.AMETHYST_BLOCK).requiresTool()), ItemGroup.BUILDING_BLOCKS, (modelSupplier, self) -> modelSupplier.registerCubeAllModelTexturePool(self), (lootProvider, self) -> {}).tags(List.of(BlockTags.PICKAXE_MINEABLE, PBlockTags.NEEDS_END_TOOL, BlockTags.CRYSTAL_SOUND_BLOCKS)).finished();
    public static final Block STELLAR_CLUSTER = register("STELLAR_CLUSTER", new AmethystClusterBlock(7, 3, AbstractBlock.Settings.of(Material.AMETHYST).nonOpaque().ticksRandomly().sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(1.5F).luminance(state -> 5)), ItemGroup.DECORATIONS, (modelSupplier, self) -> modelSupplier.registerAmethyst(self), (lootProvider, self) -> lootProvider.addDrop(
            self,
            blockx -> BlockLootTableGenerator.dropsWithSilkTouch(
                    blockx,
                    ItemEntry.builder(PItems.STELLAR_SHARD)
                            .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(4.0F)))
                            .apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))
                            .conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(ItemTags.CLUSTER_MAX_HARVESTABLES)))
                            .alternatively(
                                    (LootPoolEntry.Builder<?>) BlockLootTableGenerator.applyExplosionDecay(
                                            blockx, ItemEntry.builder(PItems.STELLAR_SHARD).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0F)))
                                    )
                            )
            )
    )).tags(List.of(BlockTags.PICKAXE_MINEABLE, PBlockTags.NEEDS_END_TOOL)).finished();
    public static final Block LARGE_STELLAR_BUD = register("LARGE_STELLAR_BUD", new AmethystClusterBlock(5, 3, AbstractBlock.Settings.copy(STELLAR_CLUSTER).sounds(BlockSoundGroup.MEDIUM_AMETHYST_BUD).luminance(state -> 4)), ItemGroup.DECORATIONS, (modelSupplier, self) -> modelSupplier.registerAmethyst(self), (lootProvider, self) -> lootProvider.addDropWithSilkTouch(self)).tags(List.of(BlockTags.PICKAXE_MINEABLE, PBlockTags.NEEDS_END_TOOL)).finished();
    public static final Block MEDIUM_STELLAR_BUD = register("MEDIUM_STELLAR_BUD",
            new AmethystClusterBlock(4, 3, AbstractBlock.Settings.copy(STELLAR_CLUSTER).sounds(BlockSoundGroup.LARGE_AMETHYST_BUD).luminance(state -> 2)), ItemGroup.DECORATIONS, (modelSupplier, self) -> modelSupplier.registerAmethyst(self), (lootProvider, self) -> lootProvider.addDropWithSilkTouch(self)).tags(List.of(BlockTags.PICKAXE_MINEABLE, PBlockTags.NEEDS_END_TOOL)).finished();
    public static final Block SMALL_STELLAR_BUD = register("SMALL_STELLAR_BUD", new AmethystClusterBlock(3, 4, AbstractBlock.Settings.copy(STELLAR_CLUSTER).sounds(BlockSoundGroup.SMALL_AMETHYST_BUD).luminance(state -> 1)), ItemGroup.DECORATIONS, (modelSupplier, self) -> modelSupplier.registerAmethyst(self), (lootProvider, self) -> lootProvider.addDropWithSilkTouch(self)).tags(List.of(BlockTags.PICKAXE_MINEABLE, PBlockTags.NEEDS_END_TOOL)).finished();



    private static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
        return state -> state.get(Properties.LIT) ? litLevel : 0;
    }

    public static class BlockBuilder {
        protected final String id;
        protected final Block block;
        protected final ItemGroup group;
        protected final BiConsumer<BlockStateModelGenerator, Block> modelSupplier;
        protected final BiConsumer<PBlockLootTableProvider, Block> lootSupplier;
        protected List<TagKey<Block>> tags = List.of();
        protected Consumer<FabricItemSettings> itemSettings = s -> {};

        public BlockBuilder(String id, Block block, ItemGroup group, BiConsumer<BlockStateModelGenerator, Block> modelSupplier, BiConsumer<PBlockLootTableProvider, Block> lootSupplier) {
            this.id = id;
            this.block = block;
            this.group = group;
            this.modelSupplier = modelSupplier;
            this.lootSupplier = lootSupplier;
        }

        public BlockBuilder tags(List<TagKey<Block>> tags) {
            this.tags = tags;
            return this;
        }

        public BlockBuilder itemSettings(Consumer<FabricItemSettings> itemSettings) {
            this.itemSettings = itemSettings;
            return this;
        }

        public Block finished() {
            return registerBlock(id, block, group, modelSupplier, lootSupplier, tags, itemSettings);
        }
    }

    private static BlockBuilder register(String id, Block block, ItemGroup group, BiConsumer<BlockStateModelGenerator, Block> modelSupplier, BiConsumer<PBlockLootTableProvider, Block> lootSupplier) {
        return new BlockBuilder(id, block, group, modelSupplier, lootSupplier);
    }

    private static Block registerBlock(String id, Block block, ItemGroup group, BiConsumer<BlockStateModelGenerator, Block> modelSupplier, BiConsumer<PBlockLootTableProvider, Block> lootSupplier, List<TagKey<Block>> tags, Consumer<FabricItemSettings> itemSettings) {
        id = id.toLowerCase();
        String name = StringUtils.toNormalCase(id);
        Block registeredBlock = Registry.register(Registry.BLOCK, new Identifier(Prog.MOD_ID, id), block);
        data.put(registeredBlock, new PBlocks.BlockData(name, registerBlockItem(id, block, group, itemSettings), ms -> modelSupplier.accept(ms, registeredBlock), ls -> lootSupplier.accept(ls, registeredBlock), tags));
        return registeredBlock;
    }

    private static Item registerBlockItem(String id, Block block, ItemGroup group, Consumer<FabricItemSettings> itemSettings) {
        var settings = new FabricItemSettings().group(group);
        itemSettings.accept(settings);
        Item registeredItem = Registry.register(Registry.ITEM, new Identifier(Prog.MOD_ID, id), new BlockItem(block, settings));
        return registeredItem;
    }

    public static void init() {
        LOGGER.info("Registering Blocks for: " + Prog.MOD_ID);
    }
}
