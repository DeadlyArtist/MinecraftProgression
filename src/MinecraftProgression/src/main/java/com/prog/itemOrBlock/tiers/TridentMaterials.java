package com.prog.itemOrBlock.tiers;

public enum TridentMaterials implements TridentMaterial {
    STEEL(1),
    ULTIMATE_DIAMOND(4),
    REFINED_OBSIDIAN(7),
    TITAN(15),
    PRIMAL_NETHERITE(25),
    EVERGLOOM(30),
    END(38);

    public final double damageBonus;

    TridentMaterials(double damageBonus) {
        this.damageBonus = damageBonus;
    }

    @Override
    public double getMeleeDamageBonus() {
        return damageBonus;
    }

    @Override
    public double getRangedDamageBonus() {
        return Math.ceil(damageBonus * 0.8);
    }
}
