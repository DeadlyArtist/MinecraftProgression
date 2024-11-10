package com.prog.itemOrBlock.tiers;

public enum CrossbowMaterials implements CrossbowMaterial {
    STEEL(2),
    ULTIMATE_DIAMOND(4),
    REFINED_OBSIDIAN(7),
    TITAN(11),
    PRIMAL_NETHERITE(15),
    VERUM(21);

    public final int projectileDamageBonus;

    CrossbowMaterials(int projectileDamageBonus) {
        this.projectileDamageBonus = projectileDamageBonus;
    }

    @Override
    public double getProjectileDamageBonus() {
        return projectileDamageBonus;
    }
}