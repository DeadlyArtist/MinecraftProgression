package com.prog.mixin;

import com.prog.itemOrBlock.PFoodComponents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Item.class)
public class ItemMixin {
//    // Doesn't work since the item has not yet been assigned.
//    @ModifyVariable(
//            method = "<init>(Lnet/minecraft/item/Item$Settings;)V",
//            at = @At("HEAD"),
//            ordinal = 0
//    )
//    private static Item.Settings modifyItemSettings(Item.Settings settings) {
//        return settings.group(ItemGroup.FOOD).food(PFoodComponents.GLISTERING_MELON_SLICE);
//    }
}
