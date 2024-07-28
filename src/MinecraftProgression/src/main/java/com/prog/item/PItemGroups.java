package com.prog.item;

import com.prog.Prog;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class PItemGroups {
    public static class ItemGroupData {
        public String name;

        public ItemGroupData(String name) {
            this.name = name;
        }
    }

    public static final Map<ItemGroup, ItemGroupData> data = new HashMap<>();



    public static final ItemGroup UPGRADES_TAB = registerItemGroup("upgrades", "Upgrades", () -> new ItemStack(PItems.STEEL_INGOT));



    private static ItemGroup registerItemGroup(String id, String name, java.util.function.Supplier<net.minecraft.item.ItemStack> iconSupplier) {
        ItemGroup itemGroup = FabricItemGroupBuilder.create(new Identifier(Prog.MOD_ID, id)).build();
        data.put(itemGroup, new ItemGroupData(name));
        return itemGroup;
    }

    public static void init() {
        Prog.LOGGER.info("Registering Mod Item Groups for: " + Prog.MOD_ID);
    }
}
