package com.prog.utils;

import com.prog.Prog;
import com.prog.itemOrBlock.UEffect;
import com.prog.text.PTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.Text;
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

    public static Map<String, List<UEffect>> extractUpgradeData(NbtCompound nbt) {
        Map<String, List<UEffect>> upgradeData = new HashMap<>();

        // Iterate over the keys of the NbtCompound
        for (String key : nbt.getKeys()) {
            // Assuming the upgrade-related keys start with "prog_upgrade_"
            if (key.startsWith(UPGRADE_NBT_PREFIX)) {
                // Get the corresponding NbtElement for each key
                var element = nbt.getList(key, NbtElement.COMPOUND_TYPE);
                if (element != null && !element.isEmpty()) {
                    List<UEffect> effects = element.stream().map(e -> UEffect.fromNbt(((NbtCompound) e))).toList();
                    upgradeData.put(key, effects);
                }
            }
        }

        return upgradeData;
    }

    public static Item getItemFromUpgradeNbt(NbtElement nbt) {
        return Registry.ITEM.get(new Identifier(nbt.asString()));
    }

    public static void addUpgradeTooltip(List<Text> tooltip, Map<String, NbtElement> upgrades) {
        if (upgrades.isEmpty()) return;

        tooltip.add(Text.of("\n" + PTexts.UPGRADEABLE_UPGRADE_TOOLTIP.get().getString() + ": " + upgrades.size()));
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.of("    " + String.join(", ", upgrades.values().stream().map(nbt -> UpgradeUtils.getItemFromUpgradeNbt(nbt).getName().getString()).toList())));
        }
    }
}
