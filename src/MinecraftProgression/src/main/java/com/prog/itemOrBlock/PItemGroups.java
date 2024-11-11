package com.prog.itemOrBlock;

import com.prog.Prog;
import com.prog.utils.LOGGER;
import com.prog.utils.StringUtils;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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


    public static final ItemGroup MORE_PROGRESSION = registerItemGroup("MORE_PROGRESSION", () -> new ItemStack(PBlocks.COSMIC_CONSTRUCTOR));
    public static final ItemGroup UPGRADABLES = registerItemGroup("UPGRADABLES", () -> new ItemStack(PItems.ULTIMATE_DIAMOND_SWORD));
    public static final ItemGroup TIER_CORES = registerItemGroup("TIER_CORES", () -> new ItemStack(PItems.REFINED_OBSIDIAN_MODULE));
    public static final ItemGroup UPGRADES = registerItemGroup("UPGRADES", () -> new ItemStack(PItems.MECHANICAL_BOOTS));
    public static final ItemGroup GOURMET_FOOD = registerItemGroup("GOURMET_FOOD", () -> new ItemStack(Items.GOLDEN_APPLE));



    private static ItemGroup registerItemGroup(String id, java.util.function.Supplier<net.minecraft.item.ItemStack> iconSupplier) {
        id = id.toLowerCase();
        String name = StringUtils.toNormalCase(id);
        ItemGroup itemGroup = FabricItemGroupBuilder.create(new Identifier(Prog.MOD_ID, id)).icon(iconSupplier).build();
        data.put(itemGroup, new ItemGroupData(name));
        return itemGroup;
    }

    public static void init() {
        LOGGER.info("Registering Item Groups for: " + Prog.MOD_ID);
    }
}
