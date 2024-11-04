package com.prog.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.Identifier;
import com.google.gson.JsonElement;

import java.util.Map;

public class RecipeEvents {

    // An event fired when recipes are loaded
    public static final Event<IRecipesLoaded> RECIPES_LOADED = EventFactory.createArrayBacked(IRecipesLoaded.class, callbacks -> (recipes) -> {
        for (IRecipesLoaded callback : callbacks) {
            callback.onRecipesLoaded(recipes);
        }
    });

    @FunctionalInterface
    public interface IRecipesLoaded {
        void onRecipesLoaded(Map<Identifier, JsonElement> recipes);
    }
}
