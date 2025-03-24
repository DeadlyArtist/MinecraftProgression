package com.prog.itemOrBlock.tiers;

public enum TridentMaterials implements TridentMaterial {
    STEEL(9),
    ULTIMATE_DIAMOND(12),
    REFINED_OBSIDIAN(18),
    TITAN(32),
    PRIMAL_NETHERITE(40),
    EVERGLOOM(80),
    END(110);

    public final double damage;

    TridentMaterials(double damage) {
        this.damage = damage - 1;
    }

    @Override
    public double getMeleeDamage() {
        return damage;
    }

    @Override
    public double getRangedDamage() {
        return Math.ceil(damage * 0.8);
    }
}
