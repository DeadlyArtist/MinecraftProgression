package com.prog.itemOrBlock;

import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Upgrades {
    public static final List<Upgrade> all = new ArrayList<>();

    public static Upgrade register(Item item, Function<Item, List<UEffect>> effects) {
        Upgrade upgrade = Upgrade.of(item, effects);
        all.add(upgrade);
        return upgrade;
    }
}
