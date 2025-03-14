package com.prog.itemOrBlock.tiers;

public enum FishingRodMaterials implements FishingRodMaterial {
    STEEL(7),
    ULTIMATE_DIAMOND(10),
    REFINED_OBSIDIAN(13),
    TITAN(23),
    PRIMAL_NETHERITE(35),
    EVERGLOOM(45),
    END(60);

    public final double treasureQuality;
    FishingRodMaterials(double treasureQuality) {
        this.treasureQuality = treasureQuality;
    }

    @Override
    public double getTreasureQuality() {
        return treasureQuality;
    }
}
