package com.prog.itemOrBlock.tiers;

public enum CrossbowMaterials implements CrossbowMaterial {
    STEEL(2),
    ULTIMATE_DIAMOND(4),
    REFINED_OBSIDIAN(7),
    TITAN(16),
    PRIMAL_NETHERITE(30),
    VERDITE(38),
    END(48);

    public final int projectileDamageBonus;

    CrossbowMaterials(int projectileDamageBonus) {
        this.projectileDamageBonus = projectileDamageBonus;
    }

    @Override
    public double getProjectileDamageBonus() {
        return projectileDamageBonus;
    }
}