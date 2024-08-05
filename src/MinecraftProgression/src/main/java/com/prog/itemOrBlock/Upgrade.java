package com.prog.itemOrBlock;


import net.minecraft.item.Item;

import java.util.List;
import java.util.function.Function;

public class Upgrade {
    public final Item item;
    public final Function<Item, List<UEffect>> effects;

    public Upgrade(Item item, Function<Item, List<UEffect>> effects) {
        this.item = item;
        this.effects = effects;
    }

    public static Upgrade of(Item item, Function<Item, List<UEffect>> effects) {
        return new Upgrade(item, effects);
    }
}