package com.prog.item;

import com.prog.Prog;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class PItemGroups {
    public static final Map<RegistryKey<ItemGroup>, String> names = new HashMap<>();



    public static final RegistryKey<ItemGroup> UPGRADES_TAB = registerItemGroup("upgrades", "Upgrades", FabricItemGroup.builder().icon(() -> new ItemStack(PItems.STEEL)));



    private static RegistryKey<ItemGroup> registerItemGroup(String id, String name, ItemGroup.Builder itemGroupBuilder) {
        ItemGroup itemGroup = Registry.register(Registries.ITEM_GROUP, new Identifier(Prog.MOD_ID, id), itemGroupBuilder.displayName(Text.translatable("itemGroup." + Prog.MOD_ID + "." + id)).build());
        RegistryKey<ItemGroup> key = RegistryKey.of(Registries.ITEM_GROUP.getKey(), new Identifier(Prog.MOD_ID, id));
        names.put(key, name);
        return key;
    }

    public static void init() {
        Prog.LOGGER.info("Registering Mod Item Groups for: " + Prog.MOD_ID);
    }
}
