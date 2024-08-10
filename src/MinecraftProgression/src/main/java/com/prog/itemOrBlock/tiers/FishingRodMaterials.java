package com.prog.itemOrBlock.tiers;

public enum FishingRodMaterials implements FishingRodMaterial {
    STEEL(1, 1),
    ULTIMATE_DIAMOND(2, 2),
    REFINED_OBSIDIAN(3, 2),
    TITAN(5, 3),
    PRIMAL_NETHERITE(8, 3);

    public final double luckIncrease;
    public final double lureIncrease;
    FishingRodMaterials(double luckIncrease, double lureIncrease) {
        this.luckIncrease = luckIncrease;
        this.lureIncrease = lureIncrease;
    }

    @Override
    public double getLuckIncrease() {
        return 0;
    }

    @Override
    public double getLureIncrease() {
        return 0;
    }
}
