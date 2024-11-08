package com.prog.compat.rei.client;

import com.google.common.collect.Lists;
import com.prog.compat.rei.FlexibleCookingDisplay;
import com.prog.itemOrBlock.data.FlexibleCookingData;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.DisplayRenderer;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.SimpleDisplayRenderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

@Environment(EnvType.CLIENT)
public class FlexibleCookingCategory implements DisplayCategory<FlexibleCookingDisplay> {

    private CategoryIdentifier<? extends FlexibleCookingDisplay> identifier;
    private EntryStack<?> logo;
    private String categoryName;

    public FlexibleCookingCategory(FlexibleCookingData data) {
        this.identifier = data.categoryIdentifier.get();
        this.logo = EntryStacks.of(data.block.get());
        this.categoryName = data.title.getString();
    }

    @Override
    public List<Widget> setupDisplay(FlexibleCookingDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 41, bounds.y + 10);
        double cookingTime = display.getCookingTime();
        DecimalFormat df = new DecimalFormat("###.##");
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 61, startPoint.y + 9)));
        widgets.add(Widgets.createBurningFire(new Point(startPoint.x + 1, startPoint.y + 20))
                .animationDurationMS(10000));
        widgets.add(Widgets.createLabel(new Point(bounds.x + bounds.width - 5, bounds.y + 5),
                Text.translatable("category.rei.cooking.time&xp", df.format(display.getXp()), df.format(cookingTime / 20d))).noShadow().rightAligned().color(0xFF404040, 0xFFBBBBBB));
        widgets.add(Widgets.createArrow(new Point(startPoint.x + 24, startPoint.y + 8))
                .animationDurationTicks(cookingTime));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 61, startPoint.y + 9))
                .entries(display.getOutputEntries().get(0))
                .disableBackground()
                .markOutput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 1, startPoint.y + 1))
                .entries(display.getInputEntries().get(0))
                .markInput());
        return widgets;
    }

    @Override
    public DisplayRenderer getDisplayRenderer(FlexibleCookingDisplay display) {
        return SimpleDisplayRenderer.from(Collections.singletonList(display.getInputEntries().get(0)), display.getOutputEntries());
    }

    @Override
    public int getDisplayHeight() {
        return 49;
    }

    @Override
    public CategoryIdentifier<? extends FlexibleCookingDisplay> getCategoryIdentifier() {
        return identifier;
    }

    @Override
    public Renderer getIcon() {
        return logo;
    }

    @Override
    public Text getTitle() {
        return Text.translatable(categoryName);
    }
}
