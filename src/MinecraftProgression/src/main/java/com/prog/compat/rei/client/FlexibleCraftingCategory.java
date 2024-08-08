package com.prog.compat.rei.client;

import com.google.common.collect.Lists;
import com.prog.Prog;
import com.prog.compat.rei.FlexibleCraftingDisplay;
import com.prog.itemOrBlock.data.FlexibleCraftingData;
import com.prog.utils.ScreenUtils;
import com.prog.utils.SlotUtils;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Slot;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.InputIngredient;
import me.shedaniel.rei.api.common.util.CollectionUtils;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class FlexibleCraftingCategory implements DisplayCategory<FlexibleCraftingDisplay> {
    public final FlexibleCraftingData data;
    public final CategoryIdentifier<? extends FlexibleCraftingDisplay> categoryIdentifier;

    public FlexibleCraftingCategory(FlexibleCraftingData data) {
        this.data = data;
        this.categoryIdentifier = CategoryIdentifier.of(ScreenUtils.getId(data.screenHandlerType.get()));
    }

    @Override
    public CategoryIdentifier<? extends FlexibleCraftingDisplay> getCategoryIdentifier() {
        return data.categoryIdentifier.get();
    }

    @Override
    public Text getTitle() {
        return data.title;
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(data.block.get());
    }

    @Override
    public List<Widget> setupDisplay(FlexibleCraftingDisplay display, Rectangle bounds) {
        int width = data.width;
        int height = data.height;
        int recipeWidth = display.recipeWidth;
        var centerX = bounds.getCenterX() + 1;
        var centerY = bounds.getCenterY();
        Point startPoint = new Point(centerX - 57, centerY - height * SlotUtils.SIZE / 2 + 1);
        var x0 = startPoint.x;
        var y0 = startPoint.y;


        // Input
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));
        List<InputIngredient<EntryStack<?>>> input = CollectionUtils.mapIndexed(display.getInputEntries(), InputIngredient::of);
        List<Slot> slots = Lists.newArrayList();
        for (int heightIndex = 0; heightIndex < height; heightIndex++) {
            for (int widthIndex = 0; widthIndex < width; widthIndex++) {
                slots.add(Widgets.createSlot(new Point(centerX - width * SlotUtils.HALF_SIZE - SlotUtils.SIZE - 8 + widthIndex * SlotUtils.SIZE, y0 + heightIndex * SlotUtils.SIZE)).markInput());
            }
        }
        for (InputIngredient<EntryStack<?>> ingredient : input) {
            slots.get(ingredient.getIndex()).entries(ingredient.get());
        }
        widgets.addAll(slots);

        // Arrow
        widgets.add(Widgets.createArrow(new Point(centerX + width * SlotUtils.HALF_SIZE - SlotUtils.SIZE - 8, y0 - SlotUtils.HALF_SIZE + height * SlotUtils.HALF_SIZE)));

        // Output
        widgets.add(Widgets.createResultSlotBackground(new Point(centerX + width * SlotUtils.HALF_SIZE + 42 - SlotUtils.SIZE * 2 - 2, y0 + height * SlotUtils.HALF_SIZE - SlotUtils.HALF_SIZE)));
        widgets.add(Widgets.createSlot(new Point(centerX + width * SlotUtils.HALF_SIZE + 42 - SlotUtils.SIZE * 2 - 2, y0 + height * SlotUtils.HALF_SIZE - SlotUtils.HALF_SIZE)).entries(display.getOutputEntries().get(0)).disableBackground().markOutput());

        if (display.recipe instanceof ShapelessRecipe) {
            widgets.add(Widgets.createShapelessIcon(bounds));
        }

        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 16 + data.height * SlotUtils.SIZE;
    }
}
