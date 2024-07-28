package com.prog.block;

import com.prog.Prog;
import com.prog.item.PItemGroups;
import com.prog.item.PItems;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class PBlocks {
    public static class BlockData {
        public String name;
        public Consumer<BlockStateModelGenerator> modelSupplier;
        public Item blockItem;

        public BlockData(String name, Consumer<BlockStateModelGenerator> modelSupplier, Item blockItem) {
            this.name = name;
            this.modelSupplier = modelSupplier;
            this.blockItem = blockItem;
        }
    }

    public static final Map<Block, PBlocks.BlockData> data = new HashMap<>();


    public static final Block STEEL_BLOCK = registerBlock("steel_block", "Steel Block", new Block(FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F).requiresTool()), ItemGroup.BUILDING_BLOCKS, (modelSupplier, self) -> modelSupplier.registerCubeAllModelTexturePool(self));


    private static Block registerBlock(String id, String name, Block block, ItemGroup group, BiConsumer<BlockStateModelGenerator, Block> modelSupplier) {
        Block registeredBlock = Registry.register(Registry.BLOCK, new Identifier(Prog.MOD_ID, id), block);
        data.put(registeredBlock, new PBlocks.BlockData(name, ms -> modelSupplier.accept(ms, registeredBlock), registerBlockItem(id, block, group)));
        return registeredBlock;
    }

    private static Item registerBlockItem(String id, Block block, ItemGroup group) {
        Item registeredItem = Registry.register(Registry.ITEM, new Identifier(Prog.MOD_ID, id), new BlockItem(block, new FabricItemSettings().group(group)));
        return registeredItem;
    }

    public static void init() {
        Prog.LOGGER.info("Registering Mod Blocks for: " + Prog.MOD_ID);
    }
}
