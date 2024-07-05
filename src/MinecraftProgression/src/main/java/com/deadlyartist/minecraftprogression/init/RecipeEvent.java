package com.deadlyartist.minecraftprogression.init;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.event.server.ServerStartingEvent;

public class RecipeEvent {

    public static void replaceRecipes(ServerStartingEvent event) {
        MinecraftServer server = event.getServer();
        RecipeManager recipeManager = server.getRecipeManager();

        removeRecipe(recipeManager, "minecraft", "diamond_hoe");
    }
    
    public static void removeRecipe(RecipeManager recipeManager, String modId, String name) {
    	ResourceLocation recipeId = new ResourceLocation(modId, name);
    	List<Recipe<?>> recipesToReplace = new ArrayList<>(recipeManager.getRecipes());
    	if (recipeManager.byKey(recipeId).isPresent()) {
            recipeManager.replaceRecipes(recipesToReplace);
        }
	}
}