package com.prog.itemOrBlock.custom;

import com.prog.itemOrBlock.tiers.BowMaterial;
import com.prog.itemOrBlock.tiers.TridentMaterial;
import com.prog.utils.FabricUtils;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Items;
import net.minecraft.item.TridentItem;
import net.minecraft.util.Identifier;

public class TieredTridentItem extends TridentItem {
    public final TridentMaterial material;

    public TieredTridentItem(TridentMaterial material, Settings settings) {
        super(settings);
        this.material = material;

        if (FabricUtils.isClient()) {
            ModelPredicateProviderRegistry.register(
                    Items.TRIDENT,
                    new Identifier("throwing"),
                    (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F
            );
        }
    }
}