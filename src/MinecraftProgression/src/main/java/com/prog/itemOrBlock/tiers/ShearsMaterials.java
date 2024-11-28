package com.prog.itemOrBlock.tiers;

public enum ShearsMaterials implements ShearsMaterial {
    STEEL(1),
    ULTIMATE_DIAMOND(3),
    REFINED_OBSIDIAN(4),
    TITAN(6),
    PRIMAL_NETHERITE(8),
    VERDITE(9),
    END(12);

    public final float miningSpeedBonus;

    ShearsMaterials(float miningSpeedBonus) {
        this.miningSpeedBonus = miningSpeedBonus;
    }

    @Override
    public float getMiningSpeedBonus() {
        return miningSpeedBonus;
    }
}
