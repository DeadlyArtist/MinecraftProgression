package com.prog.utils;

import net.minecraft.item.ItemConvertible;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.data.server.RecipeProvider.getItemPath;

public class UpgradeUtils {
    public static final String UPGRADE_NBT_PREFIX = "prog_upgrade_";

    public static String getUpgradeNbtName(ItemConvertible upgrade){
        return UPGRADE_NBT_PREFIX + getItemPath(upgrade);
    }

    public static String getRecipePath(ItemConvertible upgrade, ItemConvertible target) {
        return getUpgradeNbtName(upgrade) + "_to_" + getItemPath(target);
    }

    public static List<NbtElement> extractUpgradeData(NbtCompound nbt) {
        List<NbtElement> upgradeData = new ArrayList<>();

        // Iterate over the keys of the NbtCompound
        for (String key : nbt.getKeys()) {
            // Assuming the upgrade-related keys start with "prog_upgrade_"
            if (key.startsWith(UPGRADE_NBT_PREFIX)) {
                // Get the corresponding NbtElement for each key
                NbtElement element = nbt.get(key);
                if (element != null) {
                    upgradeData.add(element);
                }
            }
        }

        return upgradeData;
    }
}
