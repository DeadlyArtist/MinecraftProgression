package com.prog.mixin.compat.rottencreatures;

import com.github.teamfusion.platform.CoreRegistry;
import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.common.registries.RCItems;
import com.prog.itemOrBlock.PFoodComponents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Supplier;

@Mixin(value = RCItems.class, remap = false)
public class RCItemsMixin {

    @Redirect(method = "create", at = @At(value = "INVOKE", target = "Lcom/github/teamfusion/platform/CoreRegistry;register(Ljava/lang/String;Ljava/util/function/Supplier;)Ljava/util/function/Supplier;"))
    private static <T extends Item, E extends T> Supplier<E> injectCreate(CoreRegistry instance, String s, Supplier<E> eSupplier) {
        if ("corrupted_wart".equals(s)) {
            return RCItems.ITEMS.register(s, () -> (E) new Item((new FabricItemSettings()).food(PFoodComponents.CORRUPTED_WART).group(RottenCreatures.TAB)));
        }
        return RCItems.ITEMS.register(s, eSupplier);
    }
}
