package com.prog.itemOrBlock.tiers;

public enum FishingRodMaterials implements FishingRodMaterial {
    STEEL(2),
    ULTIMATE_DIAMOND(5),
    REFINED_OBSIDIAN(8),
    TITAN(18),
    PRIMAL_NETHERITE(30),
    EVERGLOOM(37),
    END(45);

    public final double treasureQualityBonus;
    FishingRodMaterials(double treasureQualityBonus) {
        this.treasureQualityBonus = treasureQualityBonus;
    }

    @Override
    public double getTreasureQualityBonus() {
        return treasureQualityBonus;
    }
}
