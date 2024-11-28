package com.prog.itemOrBlock.tiers;

public enum BowMaterials implements BowMaterial {
    STEEL(1),
    ULTIMATE_DIAMOND(2),
    REFINED_OBSIDIAN(4),
    TITAN(11),
    PRIMAL_NETHERITE(22),
    EVERGLOOM(28),
    END(36);

    public final int projectileDamageBonus;

    BowMaterials(int projectileDamageBonus) {
        this.projectileDamageBonus = projectileDamageBonus;
    }

    @Override
    public double getProjectileDamageBonus() {
        return projectileDamageBonus;
    }
}
