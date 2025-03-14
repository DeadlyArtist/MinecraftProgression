package com.prog.itemOrBlock.tiers;

public enum CrossbowMaterials implements CrossbowMaterial {
    STEEL(12),
    ULTIMATE_DIAMOND(14),
    REFINED_OBSIDIAN(17),
    TITAN(26),
    PRIMAL_NETHERITE(40),
    EVERGLOOM(58),
    END(82);

    public final int projectileDamage;

    CrossbowMaterials(int projectileDamage) {
        this.projectileDamage = projectileDamage;
    }

    @Override
    public double getProjectileDamage() {
        return projectileDamage;
    }
}