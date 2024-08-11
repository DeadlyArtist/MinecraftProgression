package com.prog.itemOrBlock.tiers;

public enum BowMaterials implements BowMaterial {
    STEEL(1),
    ULTIMATE_DIAMOND(4),
    REFINED_OBSIDIAN(7),
    TITAN(15),
    PRIMAL_NETHERITE(26),
    VERUM(39);

    public final int projectileDamageBonus;

    BowMaterials(int projectileDamageBonus) {
        this.projectileDamageBonus = projectileDamageBonus;
    }

    @Override
    public double getProjectileDamageBonus() {
        return projectileDamageBonus;
    }
}
