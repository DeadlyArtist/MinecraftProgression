package com.prog.mixin;

import com.prog.mixinInterfaces.IRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.server.RecipeProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

// Not used, only for future reference
@Mixin(RecipeProvider.class)
public abstract class RecipeProviderMixin implements IRecipeProvider {
    @Accessor("recipesPathResolver")
    public abstract DataGenerator.PathResolver getRecipesPathResolver();

    @Accessor("advancementsPathResolver")
    public abstract DataGenerator.PathResolver getAdvancementsPathResolver();
}
