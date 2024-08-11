package com.prog.itemOrBlock;

import com.prog.Prog;
import com.prog.data.PLootTableProvider;
import com.prog.itemOrBlock.custom.FlexibleCookingBlock;
import com.prog.itemOrBlock.custom.FlexibleCraftingBlock;
import com.prog.itemOrBlock.data.FlexibleCookingData;
import com.prog.itemOrBlock.data.FlexibleCraftingData;
import com.prog.utils.StringUtils;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.TexturedModel;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
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
        public Consumer<PLootTableProvider> lootSupplier;
        public @Nullable List<TagKey<Block>> tags;

        public BlockData(String name, Item blockItem, Consumer<BlockStateModelGenerator> modelSupplier, Consumer<PLootTableProvider> lootSupplier, @Nullable List<TagKey<Block>> tags) {
            this.name = name;
            this.blockItem = blockItem;
            this.modelSupplier = modelSupplier;
            this.lootSupplier = lootSupplier;
            this.tags = tags;
        }
    }

    public static final Map<Block, PBlocks.BlockData> data = new HashMap<>();

    // Machines
    public static final Block ASSEMBLY = registerBlock("ASSEMBLY", new FlexibleCraftingBlock(FlexibleCraftingData.ASSEMBLY, FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F).requiresTool()), ItemGroup.DECORATIONS, (modelSupplier, self) -> modelSupplier.registerSingleton(self, TexturedModel.ORIENTABLE_WITH_BOTTOM), (lootProvider, self) -> lootProvider.addDrop(self), List.of(BlockTags.PICKAXE_MINEABLE, BlockTags.NEEDS_DIAMOND_TOOL));
    public static final Block COSMIC_CONSTRUCTOR = registerBlock("COSMIC_CONSTRUCTOR", new FlexibleCraftingBlock(FlexibleCraftingData.COSMIC_CONSTRUCTOR, FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F).requiresTool()), ItemGroup.DECORATIONS, (modelSupplier, self) -> modelSupplier.registerSingleton(self, TexturedModel.CUBE_BOTTOM_TOP), (lootProvider, self) -> lootProvider.addDrop(self), List.of(BlockTags.PICKAXE_MINEABLE, PBlockTags.NEEDS_PRIMAL_NETHERITE_TOOL));
    public static final Block INCINERATOR = registerBlock("INCINERATOR", new FlexibleCookingBlock(FlexibleCookingData.INCINERATOR, FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F).requiresTool().luminance(createLightLevelFromLitBlockState(13))), ItemGroup.DECORATIONS, (modelSupplier, self) -> modelSupplier.registerCooker(self, TexturedModel.ORIENTABLE), (lootProvider, self) -> lootProvider.addDrop(self), List.of(BlockTags.PICKAXE_MINEABLE, BlockTags.NEEDS_DIAMOND_TOOL));
    public static final Block COSMIC_INCUBATOR = registerBlock("COSMIC_INCUBATOR", new FlexibleCookingBlock(FlexibleCookingData.COSMIC_INCUBATOR, FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F).requiresTool().luminance(createLightLevelFromLitBlockState(13))), ItemGroup.DECORATIONS, (modelSupplier, self) -> modelSupplier.registerCooker(self, TexturedModel.ORIENTABLE), (lootProvider, self) -> lootProvider.addDrop(self), List.of(BlockTags.PICKAXE_MINEABLE, PBlockTags.NEEDS_PRIMAL_NETHERITE_TOOL));

    // Materials
    public static final Block STEEL_BLOCK = registerBlock("STEEL_BLOCK", new Block(FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F).requiresTool()), ItemGroup.BUILDING_BLOCKS, (modelSupplier, self) -> modelSupplier.registerCubeAllModelTexturePool(self), (lootProvider, self) -> lootProvider.addDrop(self), List.of(BlockTags.PICKAXE_MINEABLE, BlockTags.NEEDS_IRON_TOOL));
    public static final Block MACHINE_FRAME = registerBlock("MACHINE_FRAME", new Block(FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F).requiresTool()), ItemGroup.BUILDING_BLOCKS, (modelSupplier, self) -> modelSupplier.registerCubeAllModelTexturePool(self), (lootProvider, self) -> lootProvider.addDrop(self), List.of(BlockTags.PICKAXE_MINEABLE, BlockTags.NEEDS_IRON_TOOL));
    public static final Block QUARTZ_CATALYST = registerBlock("QUARTZ_CATALYST", new Block(FabricBlockSettings.of(Material.AMETHYST).strength(3.0F, 3.0F).requiresTool()), ItemGroup.BUILDING_BLOCKS, (modelSupplier, self) -> modelSupplier.registerCubeAllModelTexturePool(self), (lootProvider, self) -> lootProvider.addDrop(self), List.of(BlockTags.PICKAXE_MINEABLE, BlockTags.NEEDS_DIAMOND_TOOL));
    public static final Block EMBERITE_BLOCK = registerBlock("EMBERITE_BLOCK", new Block(FabricBlockSettings.of(Material.FIRE).strength(5.0F, 8.0F).requiresTool().luminance(5)), ItemGroup.BUILDING_BLOCKS, (modelSupplier, self) -> modelSupplier.registerCubeAllModelTexturePool(self), (lootProvider, self) -> lootProvider.addDrop(self), List.of(BlockTags.PICKAXE_MINEABLE, PBlockTags.NEEDS_REFINED_OBSIDIAN_TOOL));
    public static final Block EMBERITE_ORE = registerBlock("EMBERITE_ORE", new Block(FabricBlockSettings.of(Material.FIRE).strength(5.0F, 8.0F).requiresTool().luminance(5)), ItemGroup.BUILDING_BLOCKS, (modelSupplier, self) -> modelSupplier.registerCubeAllModelTexturePool(self), (lootProvider, self) -> lootProvider.addDrop(self, PItems.EMBERITE), List.of(BlockTags.PICKAXE_MINEABLE, PBlockTags.NEEDS_REFINED_OBSIDIAN_TOOL));
    public static final Block VERUM_BLOCK = registerBlock("VERUM_BLOCK", new Block(FabricBlockSettings.of(Material.METAL).strength(5.0F, 12.0F).requiresTool().luminance(5)), ItemGroup.BUILDING_BLOCKS, (modelSupplier, self) -> modelSupplier.registerCubeAllModelTexturePool(self), (lootProvider, self) -> lootProvider.addDrop(self), List.of(BlockTags.PICKAXE_MINEABLE, PBlockTags.NEEDS_VERUM_TOOL));
    public static final Block VERUM_ORE = registerBlock("VERUM_ORE", new Block(FabricBlockSettings.of(Material.METAL).strength(5.0F, 12.0F).requiresTool().luminance(5)), ItemGroup.BUILDING_BLOCKS, (modelSupplier, self) -> modelSupplier.registerCubeAllModelTexturePool(self), (lootProvider, self) -> lootProvider.addDrop(self, PItems.RAW_VERUM), List.of(BlockTags.PICKAXE_MINEABLE, PBlockTags.NEEDS_PRIMAL_NETHERITE_TOOL));

    // Misc
    public static final Block NETHER_STAR_BLOCK = registerBlock("NETHER_STAR_BLOCK", new Block(FabricBlockSettings.of(Material.METAL).strength(5.0F, 10.0F).requiresTool().luminance(5)), ItemGroup.BUILDING_BLOCKS, (modelSupplier, self) -> modelSupplier.registerCubeAllModelTexturePool(self), (lootProvider, self) -> lootProvider.addDrop(self), List.of(BlockTags.PICKAXE_MINEABLE, PBlockTags.NEEDS_REFINED_OBSIDIAN_TOOL));


    private static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
        return state -> state.get(Properties.LIT) ? litLevel : 0;
    }

    private static Block registerBlock(String id, Block block, ItemGroup group, BiConsumer<BlockStateModelGenerator, Block> modelSupplier, BiConsumer<PLootTableProvider, Block> lootSupplier, List<TagKey<Block>> tags) {
        id = id.toLowerCase();
        String name = StringUtils.toNormalCase(id);
        Block registeredBlock = Registry.register(Registry.BLOCK, new Identifier(Prog.MOD_ID, id), block);
        data.put(registeredBlock, new PBlocks.BlockData(name, registerBlockItem(id, block, group), ms -> modelSupplier.accept(ms, registeredBlock), ls -> lootSupplier.accept(ls, registeredBlock), tags));
        return registeredBlock;
    }

    private static Block registerBlock(String id, Block block, ItemGroup group, BiConsumer<BlockStateModelGenerator, Block> modelSupplier, BiConsumer<PLootTableProvider, Block> lootSupplier) {
        return registerBlock(id, block, group, modelSupplier, lootSupplier, null);
    }

    private static Item registerBlockItem(String id, Block block, ItemGroup group) {
        Item registeredItem = Registry.register(Registry.ITEM, new Identifier(Prog.MOD_ID, id), new BlockItem(block, new FabricItemSettings().group(group)));
        return registeredItem;
    }

    public static void init() {
        Prog.LOGGER.info("Registering Blocks for: " + Prog.MOD_ID);
    }
}
