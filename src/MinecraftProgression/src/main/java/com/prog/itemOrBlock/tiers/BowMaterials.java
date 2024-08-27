package com.prog.itemOrBlock.tiers;

public enum BowMaterials implements BowMaterial {
    STEEL(1),
    ULTIMATE_DIAMOND(2),
    REFINED_OBSIDIAN(4),
    TITAN(7),
    PRIMAL_NETHERITE(11),
    VERUM(16);

    public final int projectileDamageBonus;

    BowMaterials(int projectileDamageBonus) {
        this.projectileDamageBonus = projectileDamageBonus;
    }

    @Override
    public double getProjectileDamageBonus() {
        return projectileDamageBonus;
    }
}
