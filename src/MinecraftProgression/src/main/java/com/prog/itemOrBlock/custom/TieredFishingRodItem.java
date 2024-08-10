package com.prog.itemOrBlock.custom;

import com.prog.itemOrBlock.tiers.FishingRodMaterial;
import com.prog.itemOrBlock.tiers.TridentMaterial;
import com.prog.utils.FabricUtils;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class TieredFishingRodItem extends FishingRodItem {
    public final FishingRodMaterial material;

    public TieredFishingRodItem(FishingRodMaterial material, Settings settings) {
        super(settings);
        this.material = material;

        if (FabricUtils.isClient()) {
            ModelPredicateProviderRegistry.register(this, new Identifier("cast"), (stack, world, entity, seed) -> {
                if (entity == null) {
                    return 0.0F;
                } else {
                    boolean bl = entity.getMainHandStack() == stack;
                    boolean bl2 = entity.getOffHandStack() == stack;
                    if (entity.getMainHandStack().getItem() instanceof FishingRodItem) {
                        bl2 = false;
                    }

                    return (bl || bl2) && entity instanceof PlayerEntity && ((PlayerEntity) entity).fishHook != null ? 1.0F : 0.0F;
                }
            });
        }
    }
}
