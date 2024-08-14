package com.prog.compat.rei;

import com.prog.itemOrBlock.data.FlexibleCookingData;
import com.prog.itemOrBlock.data.FlexibleCraftingData;
import com.prog.recipe.PRecipeTypes;
import com.prog.utils.ScreenUtils;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import net.minecraft.util.registry.Registry;

public class PREICategories {
    public static CategoryIdentifier<? extends FlexibleCraftingDisplay> ASSEMBLY = register(FlexibleCraftingData.ASSEMBLY);
    public static CategoryIdentifier<? extends FlexibleCraftingDisplay> COSMIC_CONSTRUCTOR = register(FlexibleCraftingData.COSMIC_CONSTRUCTOR);
    public static CategoryIdentifier<? extends FlexibleCookingDisplay> INCINERATOR = register(FlexibleCookingData.INCINERATOR);
    public static CategoryIdentifier<? extends FlexibleCookingDisplay> COSMIC_INCUBATOR = register(FlexibleCookingData.COSMIC_INCUBATOR);

    public static CategoryIdentifier<? extends NbtSmithingDisplay> NBT_SMITHING = CategoryIdentifier.of(Registry.RECIPE_TYPE.getId(PRecipeTypes.NBT_SMITHING));

    public static CategoryIdentifier<? extends FlexibleCraftingDisplay> register(FlexibleCraftingData data) {
        return CategoryIdentifier.of(ScreenUtils.getId(data.screenHandlerType.get()));
    }

    public static CategoryIdentifier<? extends FlexibleCookingDisplay> register(FlexibleCookingData data) {
        return CategoryIdentifier.of(ScreenUtils.getId(data.screenHandlerType.get()));
    }
}
