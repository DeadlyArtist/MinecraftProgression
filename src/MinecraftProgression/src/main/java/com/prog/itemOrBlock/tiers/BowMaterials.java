package com.prog.itemOrBlock.tiers;

public enum BowMaterials implements BowMaterial {
    STEEL(7),
    ULTIMATE_DIAMOND(8),
    REFINED_OBSIDIAN(10),
    TITAN(17),
    PRIMAL_NETHERITE(28),
    EVERGLOOM(42),
    END(60);

    public final int projectileDamage;

    BowMaterials(int projectileDamage) {
        this.projectileDamage = projectileDamage;
    }

    @Override
    public double getProjectileDamage() {
        return projectileDamage;
    }
}
