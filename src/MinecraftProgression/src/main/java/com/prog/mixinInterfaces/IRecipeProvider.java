package com.prog.mixinInterfaces;

import net.minecraft.data.DataGenerator;

// Not used, only for future reference
public interface IRecipeProvider {
    public DataGenerator.PathResolver getRecipesPathResolver();

    public DataGenerator.PathResolver getAdvancementsPathResolver();
}
