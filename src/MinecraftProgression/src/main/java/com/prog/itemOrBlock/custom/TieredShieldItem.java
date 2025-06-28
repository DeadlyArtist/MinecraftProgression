package com.prog.itemOrBlock.custom;

import com.prog.itemOrBlock.tiers.ShieldMaterial;
import com.prog.itemOrBlock.tiers.TridentMaterial;
import com.prog.utils.FabricUtils;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.Identifier;

public class TieredShieldItem extends ShieldItem {
    public final ShieldMaterial material;

    public TieredShieldItem(ShieldMaterial material, Item.Settings settings) {
        super(settings);
        this.material = material;
        this.maxCount = 1;

        if (FabricUtils.isClient()) {
            ModelPredicateProviderRegistry.register(
                    this,
                    new Identifier("blocking"),
                    (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F
            );
        }
    }
}
