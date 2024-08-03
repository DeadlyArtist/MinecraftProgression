package com.prog.itemOrBlock;

import com.prog.Prog;
import com.prog.utils.StringUtils;
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



    public static final ItemGroup UPGRADES = registerItemGroup("UPGRADES", () -> new ItemStack(PItems.STEEL_INGOT));



    private static ItemGroup registerItemGroup(String id, java.util.function.Supplier<net.minecraft.item.ItemStack> iconSupplier) {
        id = id.toLowerCase();
        String name = StringUtils.toNormalCase(id);
        ItemGroup itemGroup = FabricItemGroupBuilder.create(new Identifier(Prog.MOD_ID, id)).icon(iconSupplier).build();
        data.put(itemGroup, new ItemGroupData(name));
        return itemGroup;
    }

    public static void init() {
        Prog.LOGGER.info("Registering Item Groups for: " + Prog.MOD_ID);
    }
}
