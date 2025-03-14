package com.prog.itemOrBlock.tiers;

public enum TridentMaterials implements TridentMaterial {
    STEEL(11),
    ULTIMATE_DIAMOND(14),
    REFINED_OBSIDIAN(17),
    TITAN(25),
    PRIMAL_NETHERITE(35),
    EVERGLOOM(52),
    END(72);

    public final double damage;

    TridentMaterials(double damage) {
        this.damage = damage;
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
