package com.prog.mixin;

import com.prog.recipe.PRecipeTypes;
import com.prog.recipe.NbtSmithingRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.SmithingScreenHandler;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(SmithingScreenHandler.class)
public abstract class SmithinScreenHandlerMixin extends ForgingScreenHandler {
    @Unique
    private static DefaultedList<ItemStack> remainders = null;

    public SmithinScreenHandlerMixin(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }

    @Inject(
            method = "updateResult",
            at = @At("HEAD"),
            cancellable = true
    )
    public void onUpdateResult(CallbackInfo callbackInfo) {
        Optional<NbtSmithingRecipe> match = player.world.getRecipeManager().getFirstMatch(PRecipeTypes.NBT_SMITHING, input, player.world);

        if (match.isPresent()) {
            output.setStack(0, match.get().craft(input));
            callbackInfo.cancel();
        }
    }

    @Inject(
            method = "onTakeOutput",
            at = @At("HEAD")
    )
    protected void onTakeOutput(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        Optional<NbtSmithingRecipe> match = player.world.getRecipeManager().getFirstMatch(PRecipeTypes.NBT_SMITHING, input, player.world);
        remainders = match.map(inventoryIngredientRecipe -> inventoryIngredientRecipe.getRemainder(input)).orElse(null);
    }
}
