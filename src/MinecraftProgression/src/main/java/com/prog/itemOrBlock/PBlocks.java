package com.prog.itemOrBlock;

import com.prog.Prog;
import com.prog.data.PBlockTagProvider;
import com.prog.data.PLootTableProvider;
import com.prog.utils.StringUtils;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
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


    public static final Block STEEL_BLOCK = registerBlock("STEEL_BLOCK", new Block(FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F).requiresTool()), ItemGroup.BUILDING_BLOCKS, (modelSupplier, self) -> modelSupplier.registerCubeAllModelTexturePool(self), (lootProvider, self) -> lootProvider.addDrop(self), List.of(BlockTags.PICKAXE_MINEABLE, BlockTags.NEEDS_IRON_TOOL));
    public static final Block STEEL_FRAME = registerBlock("STEEL_FRAME", new Block(FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F).requiresTool()), ItemGroup.BUILDING_BLOCKS, (modelSupplier, self) -> modelSupplier.registerCubeAllModelTexturePool(self), (lootProvider, self) -> lootProvider.addDrop(self), List.of(BlockTags.PICKAXE_MINEABLE, BlockTags.NEEDS_IRON_TOOL));


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
        Prog.LOGGER.info("Registering Mod Blocks for: " + Prog.MOD_ID);
    }
}
