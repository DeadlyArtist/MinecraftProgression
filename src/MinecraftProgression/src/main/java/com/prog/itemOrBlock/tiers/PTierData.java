package com.prog.itemOrBlock.tiers;

import com.prog.itemOrBlock.PItemTags;
import com.prog.itemOrBlock.custom.*;
import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.item.*;
import net.minecraft.tag.TagKey;

import java.util.HashMap;
import java.util.Map;

public enum PTierData {
    STEEL(3, MiningLevels.IRON, PArmorMaterials.STEEL, PToolMaterials.STEEL, BowMaterials.STEEL, CrossbowMaterials.STEEL, ShieldMaterials.STEEL, FishingRodMaterials.STEEL, TridentMaterials.STEEL, PItemTags.STEEL, PItemTags.STEEL_OR_HIGHER),
    ULTIMATE_DIAMOND(4, MiningLevels.DIAMOND, PArmorMaterials.ULTIMATE_DIAMOND, PToolMaterials.ULTIMATE_DIAMOND, BowMaterials.ULTIMATE_DIAMOND, CrossbowMaterials.ULTIMATE_DIAMOND, ShieldMaterials.ULTIMATE_DIAMOND, FishingRodMaterials.ULTIMATE_DIAMOND, TridentMaterials.ULTIMATE_DIAMOND, PItemTags.ULTIMATE_DIAMOND, PItemTags.ULTIMATE_DIAMOND_OR_HIGHER),
    REFINED_OBSIDIAN(5, PMiningLevels.REFINED_OBSIDIAN, PArmorMaterials.REFINED_OBSIDIAN, PToolMaterials.REFINED_OBSIDIAN, BowMaterials.REFINED_OBSIDIAN, CrossbowMaterials.REFINED_OBSIDIAN, ShieldMaterials.REFINED_OBSIDIAN, FishingRodMaterials.REFINED_OBSIDIAN, TridentMaterials.REFINED_OBSIDIAN, PItemTags.REFINED_OBSIDIAN, PItemTags.REFINED_OBSIDIAN_OR_HIGHER),
    TITAN(6, PMiningLevels.TITAN, PArmorMaterials.TITAN, PToolMaterials.TITAN, BowMaterials.TITAN, CrossbowMaterials.TITAN, ShieldMaterials.TITAN, FishingRodMaterials.TITAN, TridentMaterials.TITAN, PItemTags.TITAN, PItemTags.TITAN_OR_HIGHER),
    PRIMAL_NETHERITE(7, PMiningLevels.PRIMAL_NETHERITE, PArmorMaterials.PRIMAL_NETHERITE, PToolMaterials.PRIMAL_NETHERITE, BowMaterials.PRIMAL_NETHERITE, CrossbowMaterials.PRIMAL_NETHERITE, ShieldMaterials.PRIMAL_NETHERITE, FishingRodMaterials.PRIMAL_NETHERITE, TridentMaterials.PRIMAL_NETHERITE, PItemTags.PRIMAL_NETHERITE, PItemTags.PRIMAL_NETHERITE_OR_HIGHER),
    END(8, PMiningLevels.END, PArmorMaterials.END, PToolMaterials.END, BowMaterials.END, CrossbowMaterials.END, ShieldMaterials.END, FishingRodMaterials.END, TridentMaterials.END, PItemTags.END, PItemTags.END_OR_HIGHER);

    public final int level; // wood = 0, stone = 1, iron = 2
    public final int miningLevel;
    public final ArmorMaterial armorMaterial;
    public final ToolMaterial toolMaterial;
    public final BowMaterial bowMaterial;
    public final CrossbowMaterial crossbowMaterial;
    public final ShieldMaterial shieldMaterial;
    public final FishingRodMaterial fishingRodMaterial;
    public final TridentMaterial tridentMaterial;
    public final TagKey<Item> tierAttribute;
    public final TagKey<Item> tierOrHigherAttribute;
    PTierData(int level, int miningLevel, ArmorMaterial armorMaterial, ToolMaterial toolMaterial, BowMaterial bowMaterial, CrossbowMaterial crossbowMaterial, ShieldMaterial shieldMaterial, FishingRodMaterial fishingRodMaterial, TridentMaterial tridentMaterial, TagKey<Item> tierAttribute, TagKey<Item> tierOrHigherAttribute) {
        this.level = level;
        this.miningLevel = miningLevel;
        this.armorMaterial = armorMaterial;
        this.toolMaterial = toolMaterial;
        this.bowMaterial = bowMaterial;
        this.crossbowMaterial = crossbowMaterial;
        this.shieldMaterial = shieldMaterial;
        this.fishingRodMaterial = fishingRodMaterial;
        this.tridentMaterial = tridentMaterial;
        this.tierAttribute = tierAttribute;
        this.tierOrHigherAttribute = tierOrHigherAttribute;
    }

    public static final Map<Integer, PTierData> miningLevelMap = new HashMap<>();
    public static final Map<ArmorMaterial, PTierData> armorMaterialMap = new HashMap<>();
    public static final Map<ToolMaterial, PTierData> toolMaterialMap = new HashMap<>();
    public static final Map<BowMaterial, PTierData> bowMaterialMap = new HashMap<>();
    public static final Map<CrossbowMaterial, PTierData> crossbowMaterialMap = new HashMap<>();
    public static final Map<ShieldMaterial, PTierData> shieldMaterialMap = new HashMap<>();
    public static final Map<FishingRodMaterial, PTierData> fishingRodMaterialMap = new HashMap<>();
    public static final Map<TridentMaterial, PTierData> tridentMaterialMap = new HashMap<>();

    static {
        for (PTierData tierData : values()) {
            miningLevelMap.put(tierData.miningLevel, tierData);
            armorMaterialMap.put(tierData.armorMaterial, tierData);
            toolMaterialMap.put(tierData.toolMaterial, tierData);
            bowMaterialMap.put(tierData.bowMaterial, tierData);
            crossbowMaterialMap.put(tierData.crossbowMaterial, tierData);
            shieldMaterialMap.put(tierData.shieldMaterial, tierData);
            fishingRodMaterialMap.put(tierData.fishingRodMaterial, tierData);
            tridentMaterialMap.put(tierData.tridentMaterial, tierData);
        }
    }

    public static PTierData getTier(Item item) {
        if (item instanceof ArmorItem tiered) {
            return armorMaterialMap.get(tiered.getMaterial());
        } else if (item instanceof ToolItem tiered) {
            return toolMaterialMap.get(tiered.getMaterial());
        } else if (item instanceof TieredBowItem tiered) {
            return bowMaterialMap.get(tiered.material);
        } else if (item instanceof TieredCrossbowItem tiered) {
            return crossbowMaterialMap.get(tiered.material);
        } else if (item instanceof TieredShieldItem tiered) {
            return shieldMaterialMap.get(tiered.material);
        } else if (item instanceof TieredFishingRodItem tiered) {
            return fishingRodMaterialMap.get(tiered.material);
        } else if (item instanceof TieredTridentItem tiered) {
            return tridentMaterialMap.get(tiered.material);
        }

        return null;
    }

    public static int getTierLevel(Item item) {
        var tier = getTier(item);
        if (tier != null) return tier.level;

        if (item instanceof ArmorItem armor) {
            var material = armor.getMaterial();
            if (material == ArmorMaterials.LEATHER || material == ArmorMaterials.CHAIN || material == ArmorMaterials.GOLD) return 1;
            else if (material == ArmorMaterials.IRON || material == ArmorMaterials.TURTLE) return 2;
            else if (material == ArmorMaterials.DIAMOND) return 3;
            else if (material == ArmorMaterials.NETHERITE) return 4;
        } else if (item instanceof ToolItem tool) {
            var material = tool.getMaterial();
            // wood = 0
            if (material == ToolMaterials.STONE) return 1;
            else if (material == ToolMaterials.IRON) return 2;
            else if (material == ToolMaterials.DIAMOND) return 3;
            else if (material == ToolMaterials.NETHERITE) return 4;
        }

        return 0;
    }
}
