package com.prog.itemOrBlock.tiers;

public enum TridentMaterials implements TridentMaterial {
    STEEL(1),
    ULTIMATE_DIAMOND(4),
    REFINED_OBSIDIAN(7),
    TITAN(15),
    PRIMAL_NETHERITE(25),
    VERUM(38);

    public final int damageBonus;

    TridentMaterials(int damageBonus) {
        this.damageBonus = damageBonus;
    }

    @Override
    public double getDamageBonus() {
        return damageBonus;
    }
}
