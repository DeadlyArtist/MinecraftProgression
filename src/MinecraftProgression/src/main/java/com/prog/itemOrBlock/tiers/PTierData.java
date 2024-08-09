package com.prog.itemOrBlock.tiers;

import com.prog.entity.attribute.PEntityAttributes;
import com.prog.itemOrBlock.PItemTags;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.tag.TagKey;

public enum PTierData {
    STEEL(PArmorMaterials.STEEL, PToolMaterials.STEEL, PItemTags.STEEL, PItemTags.STEEL_OR_HIGHER),
    ULTIMATE_DIAMOND(PArmorMaterials.ULTIMATE_DIAMOND, PToolMaterials.ULTIMATE_DIAMOND, PItemTags.ULTIMATE_DIAMOND, PItemTags.ULTIMATE_DIAMOND_OR_HIGHER),
    REFINED_OBSIDIAN(PArmorMaterials.REFINED_OBSIDIAN, PToolMaterials.REFINED_OBSIDIAN, PItemTags.REFINED_OBSIDIAN, PItemTags.REFINED_OBSIDIAN_OR_HIGHER),
    TITAN(PArmorMaterials.TITAN, PToolMaterials.TITAN, PItemTags.TITAN, PItemTags.TITAN_OR_HIGHER),
    PRIMAL_NETHERITE(PArmorMaterials.PRIMAL_NETHERITE, PToolMaterials.PRIMAL_NETHERITE, PItemTags.PRIMAL_NETHERITE, PItemTags.PRIMAL_NETHERITE_OR_HIGHER);

    public ArmorMaterial armorMaterial;
    public ToolMaterial toolMaterial;
    public TagKey<Item> tierAttribute;
    public TagKey<Item> tierOrHigherAttribute;
    PTierData(ArmorMaterial armorMaterial, ToolMaterial toolMaterial, TagKey<Item> tierAttribute, TagKey<Item> tierOrHigherAttribute) {
        this.armorMaterial = armorMaterial;
        this.toolMaterial = toolMaterial;
        this.tierAttribute = tierAttribute;
        this.tierOrHigherAttribute = tierOrHigherAttribute;
    }
}
