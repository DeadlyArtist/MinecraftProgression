package com.prog.mixin;

import com.google.common.collect.ImmutableSet;
import com.prog.event.TagEvents;
import com.prog.utils.FabricUtils;
import net.minecraft.item.Item;
import net.minecraft.resource.ResourceManager;
import net.minecraft.tag.TagGroupLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Mixin(TagGroupLoader.class)
public abstract class TagGroupLoaderMixin<T> {

    @Inject(method = "load", at = @At("RETURN"))
    private void customTags(ResourceManager manager, CallbackInfoReturnable<Map<Identifier, Collection<T>>> cir) {
        Map<Identifier, Collection<T>> resolvedTags = cir.getReturnValue();
        for (Map.Entry<Identifier, Collection<T>> entry : resolvedTags.entrySet()) {
            Identifier tagId = entry.getKey();
            Collection<T> mutableTagEntries = new ArrayList<>(entry.getValue());
            TagEvents.TAG_LOADED.invoker().onTagLoaded(tagId, mutableTagEntries);
            resolvedTags.put(tagId, ImmutableSet.copyOf(mutableTagEntries));
        }
    }
}
