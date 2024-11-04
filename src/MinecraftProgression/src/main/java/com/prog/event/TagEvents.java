package com.prog.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.Identifier;

import java.util.Collection;

public class TagEvents {

    // An event that is fired for each tag (Identifier) with its collection of entries
    public static final Event<ITagLoaded> TAG_LOADED = EventFactory.createArrayBacked(ITagLoaded.class, callbacks -> (tagId, entries) -> {
        for (ITagLoaded callback : callbacks) {
            callback.onTagLoaded(tagId, entries);
        }
    });

    @FunctionalInterface
    public interface ITagLoaded {
        void onTagLoaded(Identifier tagId, Collection<?> entries);
    }
}
