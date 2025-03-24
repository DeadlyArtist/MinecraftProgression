package com.prog.mixin.compat.universalenchants;

import fuzs.universalenchants.world.item.enchantment.serialize.entry.DataEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(DataEntry.Builder.class)
public interface DataEntryBuilderAccessor {
    @Accessor("entries")
    List<DataEntry<?>> getEntries();
}
