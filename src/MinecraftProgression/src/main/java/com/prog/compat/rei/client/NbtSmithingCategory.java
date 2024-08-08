package com.prog.compat.rei.client;

import com.google.common.collect.Lists;
import com.prog.compat.rei.PREICategories;
import com.prog.text.PTexts;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.common.BuiltinPlugin;
import me.shedaniel.rei.plugin.common.displays.DefaultSmithingDisplay;
import net.minecraft.block.Blocks;
import net.minecraft.text.Text;

import java.util.List;

public class NbtSmithingCategory implements DisplayCategory<NbtSmithingDisplay> {
    @Override
    public CategoryIdentifier<? extends NbtSmithingDisplay> getCategoryIdentifier() {
        return PREICategories.NBT_SMITHING;
    }

    @Override
    public Text getTitle() {
        return Text.literal(Text.translatable("category.rei.smithing").getString() + " (" + PTexts.UPGRADEABLE_UPGRADE_TOOLTIP.get().getString() + ")");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(Blocks.SMITHING_TABLE);
    }

    @Override
    public List<Widget> setupDisplay(NbtSmithingDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 31, bounds.getCenterY() - 13);
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createArrow(new Point(startPoint.x + 27, startPoint.y + 4)));
        widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 61, startPoint.y + 5)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 4 - 22, startPoint.y + 5)).entries(display.getInputEntries().get(0)).markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 4, startPoint.y + 5)).entries(display.getInputEntries().get(1)).markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 61, startPoint.y + 5)).entries(display.getOutputEntries().get(0)).disableBackground().markOutput());
        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 36;
    }
}
