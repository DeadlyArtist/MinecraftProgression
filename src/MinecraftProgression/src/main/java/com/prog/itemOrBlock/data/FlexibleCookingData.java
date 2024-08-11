package com.prog.itemOrBlock.data;

import com.prog.compat.rei.FlexibleCookingDisplay;
import com.prog.compat.rei.PREICategories;
import com.prog.itemOrBlock.PBlockEntityTypes;
import com.prog.itemOrBlock.PBlocks;
import com.prog.itemOrBlock.entity.FlexibleAbstractFurnaceBlockEntity;
import com.prog.itemOrBlock.entity.FlexibleCookingBlockEntity;
import com.prog.recipe.PRecipeSerializers;
import com.prog.recipe.PRecipeTypes;
import com.prog.screen.PScreenHandlerTypes;
import com.prog.text.PTexts;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.impl.content.registry.FuelRegistryImpl;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public enum FlexibleCookingData {
    INCINERATOR(() -> PBlocks.INCINERATOR, () -> PBlockEntityTypes.INCINERATOR, () -> PRecipeSerializers.INCINERATOR, PRecipeTypes.INCINERATOR, List.of(RecipeType.BLASTING), 200, 5, defaultFuel(), PTexts.INCINERATOR_UI_TITLE.get(), () -> PScreenHandlerTypes.INCINERATOR, () -> PREICategories.INCINERATOR),
    COSMIC_INCUBATOR(() -> PBlocks.COSMIC_INCUBATOR, () -> PBlockEntityTypes.COSMIC_INCUBATOR, () -> PRecipeSerializers.COSMIC_INCUBATOR, PRecipeTypes.COSMIC_INCUBATOR, List.of(RecipeType.BLASTING, PRecipeTypes.INCINERATOR), 200,25, defaultFuelPlus(List.of(FlexibleAbstractFurnaceBlockEntity.Fuel.of(Items.DRAGON_EGG, 1000000))), PTexts.COSMIC_INCUBATOR_UI_TITLE.get(), () -> PScreenHandlerTypes.COSMIC_INCUBATOR, () -> PREICategories.COSMIC_INCUBATOR);

    public final Supplier<Block> block;
    public final Supplier<BlockEntityType<FlexibleCookingBlockEntity>> blockEntityType;
    public final Supplier<RecipeSerializer<?>> recipeSerializerSupplier;
    public final RecipeType<? extends AbstractCookingRecipe> recipeType;
    public final List<RecipeType<? extends AbstractCookingRecipe>> supportedRecipeTypes;
    public final int defaultCookingTime;
    public final float cookingTimeDivisor;
    public final Supplier<Map<Item, Integer>> fuelMap;
    public final Text title;
    public final Supplier<ScreenHandlerType<?>> screenHandlerType;
    public final Supplier<CategoryIdentifier<? extends FlexibleCookingDisplay>> categoryIdentifier;

    FlexibleCookingData(Supplier<Block> block, Supplier<BlockEntityType<FlexibleCookingBlockEntity>> blockEntityType, Supplier<RecipeSerializer<?>> recipeSerializerSupplier, RecipeType<? extends AbstractCookingRecipe> recipeType, List<RecipeType<? extends AbstractCookingRecipe>> additionalSupportedRecipeTypes, int defaultCookingTime, float cookingTimeDivisor, Supplier<Map<Item, Integer>> fuelMap, Text title, Supplier<ScreenHandlerType<?>> screenHandlerType, Supplier<CategoryIdentifier<? extends FlexibleCookingDisplay>> categoryIdentifier) {
        this.block = block;
        this.blockEntityType = blockEntityType;
        this.recipeSerializerSupplier = recipeSerializerSupplier;
        this.recipeType = recipeType;
        this.defaultCookingTime = defaultCookingTime;
        this.cookingTimeDivisor = cookingTimeDivisor;
        this.fuelMap = fuelMap;
        this.title = title;
        this.screenHandlerType = screenHandlerType;
        this.categoryIdentifier = categoryIdentifier;

        additionalSupportedRecipeTypes = new ArrayList<>(additionalSupportedRecipeTypes);
        additionalSupportedRecipeTypes.add(recipeType);
        this.supportedRecipeTypes = additionalSupportedRecipeTypes;
    }

    FlexibleCookingData(Supplier<Block> block, Supplier<BlockEntityType<FlexibleCookingBlockEntity>> blockEntityType, Supplier<RecipeSerializer<?>> recipeSerializerSupplier, RecipeType<? extends AbstractCookingRecipe> recipeType, int defaultCookingTime, float cookingTimeDivisor, Supplier<Map<Item, Integer>> fuelMap, Text title, Supplier<ScreenHandlerType<?>> screenHandlerType, Supplier<CategoryIdentifier<? extends FlexibleCookingDisplay>> categoryIdentifier) {
        this(block, blockEntityType, recipeSerializerSupplier, recipeType, List.of(), defaultCookingTime, cookingTimeDivisor, fuelMap, title, screenHandlerType, categoryIdentifier);
    }

    public static Supplier<Map<Item, Integer>> defaultFuel() {
        return defaultFuelPlus(List.of());
    }

    public static Supplier<Map<Item, Integer>> defaultFuelPlus(List<FlexibleAbstractFurnaceBlockEntity.Fuel> additionalFuels) {
        var map = additionalFuels.stream().collect(Collectors.toMap(FlexibleAbstractFurnaceBlockEntity.Fuel::item, FlexibleAbstractFurnaceBlockEntity.Fuel::fuelTime));
        return () -> {
            Map<Item, Integer> mergedMap = new HashMap<>(((FuelRegistryImpl) FuelRegistry.INSTANCE).getFuelTimes());
            map.forEach((key, value) -> mergedMap.merge(key.asItem(), value, Integer::sum));
            return mergedMap;
        };
    }
}
