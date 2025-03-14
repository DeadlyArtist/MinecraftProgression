package com.prog.itemOrBlock.tiers;

public enum ShieldMaterials implements ShieldMaterial {
    STEEL(10),
    ULTIMATE_DIAMOND(14),
    REFINED_OBSIDIAN(18),
    TITAN(28),
    PRIMAL_NETHERITE(40),
    EVERGLOOM(58),
    END(85);

    public final double shield;

    ShieldMaterials(double shield) {
        this.shield = shield;
    }

    @Override
    public double getShield() {
        return shield;
    }
}
