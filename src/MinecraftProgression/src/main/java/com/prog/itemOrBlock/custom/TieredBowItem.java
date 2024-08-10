package com.prog.itemOrBlock.custom;

import com.prog.itemOrBlock.tiers.BowMaterial;
import com.prog.utils.FabricUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.BowItem;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.projectile_damage.ProjectileDamageMod;
import net.projectile_damage.api.IProjectileWeapon;

public class TieredBowItem extends BowItem {
    public final BowMaterial material;

    public TieredBowItem(BowMaterial material, Settings settings) {
        super(settings);
        this.material = material;

        if (this instanceof IProjectileWeapon) {
            ((IProjectileWeapon) this).setProjectileDamage(ProjectileDamageMod.configManager.value.default_bow_damage() + material.getProjectileDamageBonus());
        }

        if (FabricUtils.isClient()) {
            ModelPredicateProviderRegistry.register(this, new Identifier("pull"), (stack, world, entity, seed) -> {
                if (entity == null) {
                    return 0.0F;
                } else {
                    return entity.getActiveItem() != stack ? 0.0F : (float) (stack.getMaxUseTime() - entity.getItemUseTimeLeft()) / 20.0F;
                }
            });
            ModelPredicateProviderRegistry.register(
                    this,
                    new Identifier("pulling"),
                    (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F
            );
        }
    }
}
