package com.prog.utils;

import com.prog.Prog;
import com.prog.itemOrBlock.UEffect;
import com.prog.itemOrBlock.Upgrades;
import com.prog.text.PTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.*;

import static net.minecraft.data.server.RecipeProvider.getItemPath;

public class UpgradeUtils {
    public static final String UPGRADE_NBT_PREFIX = "upgrade___";

    public static String getUpgradeNbtName(ItemConvertible upgrade){
        return UPGRADE_NBT_PREFIX + getItemPath(upgrade);
    }

    public static String getRecipePath(ItemConvertible upgrade, ItemConvertible target) {
        return getUpgradeNbtName(upgrade) + "_to_" + getItemPath(target);
    }

    public static String getUpgradeModifierNamePrefix(String upgradeId, String upgradableId) {
        return UPGRADE_NBT_PREFIX + upgradeId + "___" + upgradableId + "___";
    }

    public static String getUpgradeModifierName(String upgradeId, String upgradableId, int effectIndex) {
        return getUpgradeModifierNamePrefix(upgradeId, upgradableId) + effectIndex;
    }

    public static Map<String, List<UEffect>> extractUpgradeData(ItemStack stack) {
        Map<String, List<UEffect>> upgradeData = new HashMap<>();
        var nbt = stack.getNbt();
        if (nbt == null) return upgradeData;

        var upgradableItem = stack.getItem();
        var upgradableId = ItemUtils.getId(upgradableItem).toString();
        for (String key : nbt.getKeys()) {
            if (key.startsWith(UPGRADE_NBT_PREFIX)) {
                var upgradeNbt = nbt.getCompound(key);
                var upgradeId = upgradeNbt.getString("id");
                var upgrade = Upgrades.data.get(ItemUtils.byId(upgradeId));
                if (upgrade != null) {
                    var templateEffects = upgrade.effects.apply(upgradableItem);
                    if (templateEffects != null && !templateEffects.isEmpty()) {
                        var effects = new ArrayList<UEffect>();
                        for (int index = 0; index < templateEffects.size(); index++) {
                            effects.add(templateEffects.get(index).copyWithName(UpgradeUtils.getUpgradeModifierName(upgradeId, upgradableId, index)));
                        }
                        upgradeData.put(upgradeId, effects);
                    }
                }
            }
        }

        return new TreeMap<>(upgradeData);
    }

    public static Item getItemFromUpgradeNbt(NbtElement nbt) {
        return Registry.ITEM.get(new Identifier(nbt.asString()));
    }

    public static void addUpgradeTooltip(List<Text> tooltip, ItemStack stack) {
        var upgrades = extractUpgradeData(stack);
        if (upgrades.isEmpty()) return;

        tooltip.add(Text.literal("\n" + PTexts.UPGRADEABLE_UPGRADE_TOOLTIP.get().getString() + ": " + upgrades.size()).formatted(Formatting.DARK_GRAY).formatted(Formatting.ITALIC));
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.literal("    " + String.join(", ", upgrades.keySet().stream().map((id) -> ItemUtils.byId(id).getName().getString()).toList())).formatted(Formatting.DARK_GRAY).formatted(Formatting.ITALIC));
        }
    }
}
