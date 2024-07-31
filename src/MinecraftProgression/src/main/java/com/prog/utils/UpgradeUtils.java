package com.prog.utils;

import com.prog.Prog;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

import java.util.*;

import static net.minecraft.data.server.RecipeProvider.getItemPath;

public class UpgradeUtils {
    public static final String UPGRADE_NBT_PREFIX = "prog_upgrade_";

    public static String getUpgradeNbtName(ItemConvertible upgrade){
        return UPGRADE_NBT_PREFIX + getItemPath(upgrade);
    }

    public static String getRecipePath(ItemConvertible upgrade, ItemConvertible target) {
        return getUpgradeNbtName(upgrade) + "_to_" + getItemPath(target);
    }

    public static Map<String, NbtElement> extractUpgradeData(NbtCompound nbt) {
        Map<String, NbtElement> upgradeData = new HashMap<>();

        // Iterate over the keys of the NbtCompound
        for (String key : nbt.getKeys()) {
            // Assuming the upgrade-related keys start with "prog_upgrade_"
            if (key.startsWith(UPGRADE_NBT_PREFIX)) {
                // Get the corresponding NbtElement for each key
                NbtElement element = nbt.get(key);
                if (element != null) {
                    upgradeData.put(key, element);
                }
            }
        }

        return upgradeData;
    }

    public static Item getItemFromUpgradeNbt(NbtElement nbt) {
        return Registry.ITEM.get(new Identifier(nbt.asString()));
    }
}
