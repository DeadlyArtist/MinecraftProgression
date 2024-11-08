package com.prog.mixin;

import com.prog.itemOrBlock.PFoodComponents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Items.class)
public class ItemsMixin {
    @Inject(at = @At("HEAD"), method = "register(Lnet/minecraft/util/Identifier;Lnet/minecraft/item/Item;)Lnet/minecraft/item/Item;")
    private static void register(Identifier id, Item item, CallbackInfoReturnable<Item> cir) {
        if ("glistering_melon_slice".equals(id.getPath())) {
            item.group = ItemGroup.FOOD;
            item.foodComponent = PFoodComponents.GLISTERING_MELON_SLICE;
        } else if ("nether_wart".equals(id.getPath())) {
            item.group = ItemGroup.FOOD;
            item.foodComponent = PFoodComponents.NETHER_WART;
        } else if ("turtle_egg".equals(id.getPath())) {
            item.group = ItemGroup.FOOD;
            item.foodComponent = PFoodComponents.TURTLE_EGG;
        } else if ("slime_ball".equals(id.getPath())) {
            item.group = ItemGroup.FOOD;
            item.foodComponent = PFoodComponents.SLIME_BALL;
            // Doesn't work due to glitches.
//        } else if ("sea_pickle".equals(id.getPath())) {
//            item.group = ItemGroup.FOOD;
//            item.foodComponent = PFoodComponents.SEA_PICKLE;
        } else if ("magma_cream".equals(id.getPath())) {
            item.group = ItemGroup.FOOD;
            item.foodComponent = PFoodComponents.MAGMA_CREAM;
        }
    }
}
