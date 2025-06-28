package com.prog.itemOrBlock.custom;

import com.prog.itemOrBlock.tiers.BowMaterial;
import com.prog.itemOrBlock.tiers.TridentMaterial;
import com.prog.utils.FabricUtils;
import com.prog.utils.LOGGER;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class TieredTridentItem extends TridentItem {
    public final TridentMaterial material;

    public TieredTridentItem(TridentMaterial material, Settings settings) {
        super(settings);
        this.material = material;
        this.maxCount = 1;

        if (FabricUtils.isClient()) {
            ModelPredicateProviderRegistry.register(
                    this,
                    new Identifier("throwing"),
                    (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F
            );
        }
    }
}