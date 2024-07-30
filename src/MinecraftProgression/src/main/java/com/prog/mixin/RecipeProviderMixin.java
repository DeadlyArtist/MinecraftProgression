package com.prog.mixin;

import com.prog.mixinInterfaces.IRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.server.RecipeProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RecipeProvider.class)
public abstract class RecipeProviderMixin implements IRecipeProvider {
    // Shadow the private final field
    @Final
    @Shadow private DataGenerator.PathResolver recipesPathResolver;
    @Final
    @Shadow
    private DataGenerator.PathResolver advancementsPathResolver;

    @Accessor("recipesPathResolver")
    public abstract DataGenerator.PathResolver getRecipesPathResolver();

    @Accessor("advancementsPathResolver")
    public abstract DataGenerator.PathResolver getAdvancementsPathResolver();
}
