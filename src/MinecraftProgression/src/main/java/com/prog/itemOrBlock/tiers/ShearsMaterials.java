package com.prog.itemOrBlock.tiers;

public enum ShearsMaterials implements ShearsMaterial {
    STEEL(1, 1),
    ULTIMATE_DIAMOND(3, 1),
    REFINED_OBSIDIAN(4, 1),
    TITAN(6, 1),
    PRIMAL_NETHERITE(8, 1),
    EVERGLOOM(12, 1),
    END(15, 2);

    public final float miningSpeedBonus;
    public final int woolBonus;

    ShearsMaterials(float miningSpeedBonus, int woolBonus) {
        this.miningSpeedBonus = miningSpeedBonus;
        this.woolBonus = woolBonus;
    }

    @Override
    public float getMiningSpeedBonus() {
        return miningSpeedBonus;
    }

    @Override
    public int getWoolBonus() {
        return woolBonus;
    }
}
