package com.prog.itemOrBlock.custom;

import com.prog.itemOrBlock.tiers.BowMaterial;
import com.prog.itemOrBlock.tiers.CrossbowMaterial;
import com.prog.utils.FabricUtils;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class TieredCrossbowItem extends CrossbowItem {
    public final CrossbowMaterial material;

    public TieredCrossbowItem(CrossbowMaterial material, Settings settings) {
        super(settings);
        this.material = material;
        this.maxCount = 1;

        if (FabricUtils.isClient()) {
            ModelPredicateProviderRegistry.register(this, new Identifier("pull"), (stack, world, entity, seed) -> {
                if (entity == null) {
                    return 0.0F;
                } else {
                    return CrossbowItem.isCharged(stack) ? 0.0F : (float) (stack.getMaxUseTime() - entity.getItemUseTimeLeft()) / (float) CrossbowItem.getPullTime(stack);
                }
            });
            ModelPredicateProviderRegistry.register(
                    this,
                    new Identifier("pulling"),
                    (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack && !CrossbowItem.isCharged(stack) ? 1.0F : 0.0F
            );
            ModelPredicateProviderRegistry.register(this, new Identifier("charged"), (stack, world, entity, seed) -> entity != null && CrossbowItem.isCharged(stack) ? 1.0F : 0.0F);
            ModelPredicateProviderRegistry.register(
                    this,
                    new Identifier("firework"),
                    (stack, world, entity, seed) -> entity != null && CrossbowItem.isCharged(stack) && CrossbowItem.hasProjectile(stack, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F
            );
        }
    }
}