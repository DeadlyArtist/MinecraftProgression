package com.prog.mixin;

import net.minecraft.client.recipebook.ClientRecipeBook;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(ClientRecipeBook.class)
public class ClientRecipeBookMixin {
    @Redirect(
            method = "getGroupForRecipe",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V"
            )
    )
    private static void removeUnknownRecipeWarning(Logger instance, String s, Object o1, Object o2) {
        // Suppress the warning by doing nothing
    }
}
