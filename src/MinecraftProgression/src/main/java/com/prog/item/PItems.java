package com.prog.item;

import com.prog.Prog;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class PItems {
    private static final Map<RegistryKey<ItemGroup>, Consumer<FabricItemGroupEntries>> itemGroupRegisterMap = new HashMap<>();
    public static final Map<Item, String> names = new HashMap<>();



    public static final Item STEEL = registerItem("steel", "Steel", new Item(new FabricItemSettings()),
            ItemGroups.INGREDIENTS, (entries, self) -> entries.add(self));
    public static final Item STEEL_BINDING = registerItem("steel_binding", "Steel Binding", new Item(new FabricItemSettings()),
            ItemGroups.INGREDIENTS, (entries, self) -> entries.add(self));


    private static Item registerItem(String id, String name, Item item, RegistryKey<ItemGroup> itemGroup, BiConsumer<FabricItemGroupEntries, Item> itemGroupModifier) {
        Item registeredItem = Registry.register(Registries.ITEM, new Identifier(Prog.MOD_ID, id), item);
        names.put(registeredItem, name);
        itemGroupRegisterMap.merge(itemGroup, entries -> itemGroupModifier.accept(entries, registeredItem), Consumer::andThen);
        return registeredItem;
    }

    public static void init(){
        Prog.LOGGER.info("Registering Mod Items for: " + Prog.MOD_ID);

        itemGroupRegisterMap.forEach((itemGroup, modifier) ->
                ItemGroupEvents.modifyEntriesEvent(itemGroup).register(modifier::accept));
    }
}
