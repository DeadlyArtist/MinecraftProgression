package com.prog.itemOrBlock.tiers;

public enum ShieldMaterials implements ShieldMaterial {
    STEEL(2),
    ULTIMATE_DIAMOND(5),
    REFINED_OBSIDIAN(9),
    TITAN(18),
    PRIMAL_NETHERITE(30),
    EVERGLOOM(37),
    END(45);

    public final double shieldBonus;

    ShieldMaterials(double shieldBonus) {
        this.shieldBonus = shieldBonus;
    }

    @Override
    public double getShieldBonus() {
        return shieldBonus;
    }
}
