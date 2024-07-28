package com.prog.item;

import com.prog.Prog;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class PItems {
    public static class ItemData {
        public String name;
        public Consumer<ItemModelGenerator> modelSupplier;

        public ItemData(String name, Consumer<ItemModelGenerator> modelSupplier) {
            this.name = name;
            this.modelSupplier = modelSupplier;
        }
    }

    public static final Map<Item, ItemData> data = new HashMap<>();



    public static final Item STEEL_INGOT = registerItem("steel_ingot", "Steel Ingot", ItemGroup.MISC, (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));
    public static final Item STEEL_BINDING = registerItem("steel_binding", "Steel Binding", ItemGroup.MISC, (modelSupplier, self) -> modelSupplier.register(self, Models.GENERATED));


    private static Item registerItem(String id, String name, ItemGroup group, BiConsumer<ItemModelGenerator, Item> modelSupplier) {
        Item registeredItem = Registry.register(Registry.ITEM, new Identifier(Prog.MOD_ID, id), new Item(new FabricItemSettings().group(group)));
        data.put(registeredItem, new ItemData(name, ms -> modelSupplier.accept(ms, registeredItem)));
        return registeredItem;
    }

    public static void init(){
        Prog.LOGGER.info("Registering Mod Items for: " + Prog.MOD_ID);
    }
}
