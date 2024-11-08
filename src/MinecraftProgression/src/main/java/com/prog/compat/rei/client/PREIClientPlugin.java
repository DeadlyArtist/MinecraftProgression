package com.prog.compat.rei.client;

import com.prog.client.gui.screen.ingame.FlexibleCookingScreen;
import com.prog.client.gui.screen.ingame.FlexibleCraftingScreen;
import com.prog.compat.rei.FlexibleCookingDisplay;
import com.prog.compat.rei.FlexibleCraftingDisplay;
import com.prog.compat.rei.NbtSmithingDisplay;
import com.prog.compat.rei.PREICategories;
import com.prog.itemOrBlock.data.FlexibleCookingData;
import com.prog.itemOrBlock.data.FlexibleCraftingData;
import com.prog.recipe.FlexibleCookingRecipe;
import com.prog.recipe.FlexibleCraftingRecipe;
import com.prog.recipe.PRecipeTypes;
import com.prog.recipe.NbtSmithingRecipe;
import com.prog.screen.FlexibleCookingScreenHandler;
import com.prog.screen.FlexibleCraftingScreenHandler;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.client.registry.transfer.TransferHandlerRegistry;
import me.shedaniel.rei.api.client.registry.transfer.simple.SimpleTransferHandler;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.transfer.info.stack.SlotAccessor;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.common.BuiltinPlugin;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.recipe.*;
import net.minecraft.screen.*;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Environment(EnvType.CLIENT)
public class PREIClientPlugin implements REIClientPlugin {
    static <C extends ScreenHandler, D extends Display> SimpleTransferHandler createCraftingCompat(CategoryIdentifier<D> categoryIdentifier, int recipeWidth, int recipeHeight) {
        var containerClass = FlexibleCraftingScreenHandler.class;
        return new SimpleTransferHandler() {
            @Override
            public ApplicabilityResult checkApplicable(Context context) {
                if (!containerClass.isInstance(context.getMenu())
                        || !categoryIdentifier.equals(context.getDisplay().getCategoryIdentifier())
                        || context.getContainerScreen() == null
                        || (context.getMenu() instanceof FlexibleCraftingScreenHandler flex && (flex.width < recipeWidth || flex.height < recipeHeight))
                ) {
                    return ApplicabilityResult.createNotApplicable();
                } else {
                    return ApplicabilityResult.createApplicable();
                }
            }

            @Override
            public Iterable<SlotAccessor> getInputSlots(Context context) {
                var handler = (FlexibleCraftingScreenHandler) context.getMenu();
                var width = handler.width;

                return IntStream.range(1, recipeWidth * recipeHeight + 1)
                        .mapToObj(id -> SlotAccessor.fromSlot(handler.getSlot(1 + (id - 1) % recipeWidth + width * (int)((id - 1) / recipeWidth))))
                        .toList();
            }

            @Override
            public Iterable<SlotAccessor> getInventorySlots(Context context) {
                ClientPlayerEntity player = context.getMinecraft().player;
                PlayerInventory inventory = player.getInventory();
                return IntStream.range(0, inventory.main.size())
                        .mapToObj(index -> SlotAccessor.fromPlayerInventory(player, index))
                        .collect(Collectors.toList());
            }
        };
    }

    static <C extends ScreenHandler, D extends Display> SimpleTransferHandler createCraftingCompat(CategoryIdentifier<D> categoryIdentifier, FlexibleCraftingData data) {
        return createCraftingCompat(categoryIdentifier, data.width, data.height);
    }

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new NbtSmithingCategory());
        registry.addWorkstations(PREICategories.NBT_SMITHING, EntryStacks.of(Blocks.SMITHING_TABLE));

        registerCategory(registry, FlexibleCraftingData.ASSEMBLY);
        registerCategory(registry, FlexibleCraftingData.COSMIC_CONSTRUCTOR);
        registerCategory(registry, FlexibleCookingData.INCINERATOR);
        registerCategory(registry, FlexibleCookingData.COSMIC_INCUBATOR);
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(NbtSmithingRecipe.class, PRecipeTypes.NBT_SMITHING, recipe -> new NbtSmithingDisplay(recipe));

        registry.registerRecipeFiller(CraftingRecipe.class, RecipeType.CRAFTING, recipe -> new FlexibleCraftingDisplay(FlexibleCraftingData.ASSEMBLY, recipe));
        registry.registerRecipeFiller(FlexibleCraftingRecipe.class, PRecipeTypes.ASSEMBLY, recipe -> new FlexibleCraftingDisplay(FlexibleCraftingData.ASSEMBLY, recipe));

        registry.registerRecipeFiller(CraftingRecipe.class, RecipeType.CRAFTING, recipe -> new FlexibleCraftingDisplay(FlexibleCraftingData.COSMIC_CONSTRUCTOR, recipe));
        registry.registerRecipeFiller(FlexibleCraftingRecipe.class, PRecipeTypes.ASSEMBLY, recipe -> new FlexibleCraftingDisplay(FlexibleCraftingData.COSMIC_CONSTRUCTOR, recipe));
        registry.registerRecipeFiller(FlexibleCraftingRecipe.class, PRecipeTypes.COSMIC_CONSTRUCTOR, recipe -> new FlexibleCraftingDisplay(FlexibleCraftingData.COSMIC_CONSTRUCTOR, recipe));

        registry.registerRecipeFiller(BlastingRecipe.class, RecipeType.BLASTING, recipe -> new FlexibleCookingDisplay(FlexibleCookingData.INCINERATOR, recipe));
        registry.registerRecipeFiller(FlexibleCookingRecipe.class, PRecipeTypes.INCINERATOR, recipe -> new FlexibleCookingDisplay(FlexibleCookingData.INCINERATOR, recipe));

        registry.registerRecipeFiller(BlastingRecipe.class, RecipeType.BLASTING, recipe -> new FlexibleCookingDisplay(FlexibleCookingData.COSMIC_INCUBATOR, recipe));
        registry.registerRecipeFiller(FlexibleCookingRecipe.class, PRecipeTypes.INCINERATOR, recipe -> new FlexibleCookingDisplay(FlexibleCookingData.COSMIC_INCUBATOR, recipe));
        registry.registerRecipeFiller(FlexibleCookingRecipe.class, PRecipeTypes.COSMIC_INCUBATOR, recipe -> new FlexibleCookingDisplay(FlexibleCookingData.COSMIC_INCUBATOR, recipe));
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerContainerClickArea(new Rectangle(88 + 18, 32, 28, 23), FlexibleCraftingScreen.class, PREICategories.ASSEMBLY);
        registry.registerContainerClickArea(new Rectangle(88 + 18, 32 + 18, 28, 23), FlexibleCraftingScreen.class, PREICategories.COSMIC_CONSTRUCTOR);
        registry.registerContainerClickArea(new Rectangle(78, 32, 28, 23), FlexibleCookingScreen.class, PREICategories.INCINERATOR);
        registry.registerContainerClickArea(new Rectangle(78, 32, 28, 23), FlexibleCookingScreen.class, PREICategories.COSMIC_INCUBATOR);
    }

    @Override
    public void registerTransferHandlers(TransferHandlerRegistry registry) {
        registry.register(createCraftingCompat(BuiltinPlugin.CRAFTING, 3, 3));

        registry.register(createCraftingCompat(PREICategories.ASSEMBLY, FlexibleCraftingData.ASSEMBLY));
        registry.register(createCraftingCompat(PREICategories.COSMIC_CONSTRUCTOR, FlexibleCraftingData.COSMIC_CONSTRUCTOR));

        registry.register(SimpleTransferHandler.create(FlexibleCookingScreenHandler.class, BuiltinPlugin.BLASTING, new SimpleTransferHandler.IntRange(0, 1)));
        registry.register(SimpleTransferHandler.create(FlexibleCookingScreenHandler.class, PREICategories.INCINERATOR, new SimpleTransferHandler.IntRange(0, 1)));
        registry.register(SimpleTransferHandler.create(FlexibleCookingScreenHandler.class, PREICategories.COSMIC_INCUBATOR, new SimpleTransferHandler.IntRange(0, 1)));
    }

    public static void registerCategory(CategoryRegistry registry, FlexibleCraftingData data) {
        registry.add(new FlexibleCraftingCategory(data));
        registry.addWorkstations(data.categoryIdentifier.get(), EntryStacks.of(data.block.get()));
    }

    public static void registerCategory(CategoryRegistry registry, FlexibleCookingData data) {
        registry.add(new FlexibleCookingCategory(data));
        registry.addWorkstations(data.categoryIdentifier.get(), EntryStacks.of(data.block.get()));
    }

    @Override
    public double getPriority() {
        return -10;
    }
}
