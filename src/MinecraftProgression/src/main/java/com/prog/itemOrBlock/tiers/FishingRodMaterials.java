package com.prog.itemOrBlock.tiers;

public enum FishingRodMaterials implements FishingRodMaterial {
    STEEL(1),
    ULTIMATE_DIAMOND(3),
    REFINED_OBSIDIAN(5),
    TITAN(12),
    PRIMAL_NETHERITE(20),
    END(30);

    public final double treasureQualityBonus;
    FishingRodMaterials(double treasureQualityBonus) {
        this.treasureQualityBonus = treasureQualityBonus;
    }

    @Override
    public double getTreasureQualityBonus() {
        return treasureQualityBonus;
    }
}
