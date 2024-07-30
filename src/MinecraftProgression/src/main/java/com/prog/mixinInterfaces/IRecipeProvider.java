package com.prog.mixinInterfaces;

import net.minecraft.data.DataGenerator;

public interface IRecipeProvider {
    public DataGenerator.PathResolver getRecipesPathResolver();

    public DataGenerator.PathResolver getAdvancementsPathResolver();
}
