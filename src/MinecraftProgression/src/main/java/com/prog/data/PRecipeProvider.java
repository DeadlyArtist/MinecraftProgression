package com.prog.data;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import com.prog.Prog;
import com.prog.itemOrBlock.PBlocks;
import com.prog.itemOrBlock.PItems;
import com.prog.mixinInterfaces.IRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ConditionJsonProvider;
import net.fabricmc.fabric.impl.datagen.FabricDataGenHelper;
import net.minecraft.data.DataWriter;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.SmithingRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.CookingRecipeSerializer;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class PRecipeProvider extends FabricRecipeProvider {
    public static class ShapedRecipeUtils {
        public static char findFirstNonSpaceChar(List<String> strings) {
            for (String string : strings) {
                for (char ch : string.toCharArray()) {
                    if (ch != ' ') {
                        return ch;
                    }
                }
            }
            throw new RuntimeException("No non-space character found in the list");
        }

        public static List<Character> getUniqueChars(List<String> strings) {
            // Use a LinkedHashSet to maintain order and uniqueness
            Set<Character> uniqueCharsSet = new LinkedHashSet<>();

            // Iterate over each string and add each character to the set
            for (String str : strings) {
                for (char ch : str.toCharArray()) {
                    if (ch != ' ') {
                        uniqueCharsSet.add(ch);
                    }
                }
            }

            // Convert the set to a list to return
            return new ArrayList<>(uniqueCharsSet);
        }
    }

    public static class Input {
        public final char identifier;
        public final ItemConvertible itemProvider;
        public Input(char identifier, ItemConvertible itemProvider) {
            this.identifier = identifier;
            this.itemProvider = itemProvider;
        }

        public static Input of(char identifier, ItemConvertible itemProvider) {
            return new Input(identifier, itemProvider);
        }
    }

    public static class ShapedRecipeBuilderWrapper {
        private final ShapedRecipeJsonBuilder internalBuilder;
        private String path;
        private String namespace = null;

        public ShapedRecipeBuilderWrapper(ShapedRecipeJsonBuilder internalBuilder, String path) {
            this.internalBuilder = internalBuilder;
            this.path = path;
        }

        public ShapedRecipeBuilderWrapper pattern(String pattern) {
            internalBuilder.pattern(pattern);
            return this;
        }

        public ShapedRecipeBuilderWrapper input(Character identifier, ItemConvertible itemProvider) {
            internalBuilder.input(identifier, itemProvider);
            return this;
        }

        public ShapedRecipeBuilderWrapper criterion(String criterionName, net.minecraft.advancement.criterion.CriterionConditions conditions) {
            internalBuilder.criterion(criterionName, conditions);
            return this;
        }

        public ShapedRecipeBuilderWrapper setPath(String path) {
            this.path = path;
            return this;
        }

        public ShapedRecipeBuilderWrapper setNamespace(String namespace) {
            this.namespace = namespace;
            return this;
        }

        public void offer(Consumer<RecipeJsonProvider> exporter) {
            Identifier identifier = new Identifier(namespace, path);
            if (namespace != null) recipesWithCustomNamespace.add(String.valueOf(identifier));
            internalBuilder.offerTo(exporter, identifier);
        }
    }

    public static class CookingRecipeBuilderWrapper {
        private final CookingRecipeJsonBuilder internalBuilder;
        private String path;
        private String namespace = Prog.MOD_ID;

        public CookingRecipeBuilderWrapper(CookingRecipeJsonBuilder internalBuilder, String path) {
            this.internalBuilder = internalBuilder;
            this.path = path;
        }

        public CookingRecipeBuilderWrapper criterion(String criterionName, net.minecraft.advancement.criterion.CriterionConditions conditions) {
            internalBuilder.criterion(criterionName, conditions);
            return this;
        }

        public CookingRecipeBuilderWrapper setPath(String path) {
            this.path = path;
            return this;
        }

        public CookingRecipeBuilderWrapper setNamespace(String namespace) {
            this.namespace = namespace;
            return this;
        }

        public void offer(Consumer<RecipeJsonProvider> exporter) {
            internalBuilder.offerTo(exporter, new Identifier(namespace, path));
        }
    }

    public static class SmithingRecipeBuilderWrapper {
        private final SmithingRecipeJsonBuilder internalBuilder;
        private String path;
        private String namespace = Prog.MOD_ID;

        public SmithingRecipeBuilderWrapper(SmithingRecipeJsonBuilder internalBuilder, String path) {
            this.internalBuilder = internalBuilder;
            this.path = path;
        }

        public SmithingRecipeBuilderWrapper criterion(String criterionName, net.minecraft.advancement.criterion.CriterionConditions conditions) {
            internalBuilder.criterion(criterionName, conditions);
            return this;
        }

        public SmithingRecipeBuilderWrapper setPath(String path) {
            this.path = path;
            return this;
        }

        public SmithingRecipeBuilderWrapper setNamespace(String namespace) {
            this.namespace = namespace;
            return this;
        }

        public void offer(Consumer<RecipeJsonProvider> exporter) {
            internalBuilder.offerTo(exporter, new Identifier(namespace, path));
        }
    }


    public PRecipeProvider(FabricDataGenerator generator) {
        super(generator);
    }

    public static ShapedRecipeBuilderWrapper createShapedRecipeSpecific(List<String> pattern, List<Input> inputs, ItemConvertible output) {
        ShapedRecipeJsonBuilder internalBuilder = ShapedRecipeJsonBuilder.create(output);
        ShapedRecipeBuilderWrapper builder = new ShapedRecipeBuilderWrapper(internalBuilder, getItemPath(output));

        pattern.forEach(builder::pattern);
        inputs.forEach(input -> builder.input(input.identifier, input.itemProvider));
        inputs.forEach(input -> builder.criterion(hasItem(input.itemProvider), conditionsFromItem(input.itemProvider)));

        return builder;
    }

    public static ShapedRecipeBuilderWrapper createShapedRecipe(List<String> pattern, List<ItemConvertible> inputs, ItemConvertible output) {
        List<Character> uniqueChars = ShapedRecipeUtils.getUniqueChars(pattern);
        List<Input> inputsMapped = IntStream.range(0, Math.min(uniqueChars.size(), inputs.size()))
                .mapToObj(i -> Input.of(uniqueChars.get(i), inputs.get(i)))
                .toList();
        return createShapedRecipeSpecific(pattern, inputsMapped, output);
    }

    public static ShapedRecipeBuilderWrapper createShapedRecipe(List<String> pattern, ItemConvertible input, ItemConvertible output) {
        return createShapedRecipe(pattern, List.of(input), output);
    }

    public static CookingRecipeBuilderWrapper createCookingRecipe(CookingRecipeSerializer<?> serializer, ItemConvertible input, ItemConvertible output, int cookingTime, float experience) {
        CookingRecipeJsonBuilder internalBuilder = CookingRecipeJsonBuilder.create(Ingredient.ofItems(input), output, experience, cookingTime, serializer);
        String defaultPath = getItemPath(output) + "_from_" + Registry.RECIPE_SERIALIZER.getId(serializer).getPath();
        CookingRecipeBuilderWrapper builder = new CookingRecipeBuilderWrapper(internalBuilder, defaultPath);

        builder.criterion(hasItem(input), conditionsFromItem(input));

        return builder;
    }

    public static SmithingRecipeBuilderWrapper createSmithingRecipe(Item baseInput, Item consumeInput, Item output) {
        SmithingRecipeJsonBuilder internalBuilder = SmithingRecipeJsonBuilder.create(Ingredient.ofItems(baseInput), Ingredient.ofItems(consumeInput), output);
        String defaultPath = getItemPath(output) + "_from_smithing";
        SmithingRecipeBuilderWrapper builder = new SmithingRecipeBuilderWrapper(internalBuilder, defaultPath);

        builder.criterion(hasItem(baseInput), conditionsFromItem(baseInput));
        builder.criterion(hasItem(consumeInput), conditionsFromItem(consumeInput));

        return builder;
    }


    private static final Set<String> recipesWithCustomNamespace = new HashSet<>();

    @Override
    public void run(DataWriter writer) {
        Set<Identifier> generatedRecipes = Sets.newHashSet();
        generateRecipes(provider -> {
            Identifier recipeIdentifier = provider.getRecipeId();
            String namespace = recipeIdentifier.getNamespace();
            if (!recipesWithCustomNamespace.contains(recipeIdentifier.toString())) {
                namespace = dataGenerator.getModId();
                recipeIdentifier = new Identifier(namespace, recipeIdentifier.getPath());
            }

            Identifier advancementIdentifier = new Identifier(namespace, provider.getAdvancementId().getPath());

            if (!generatedRecipes.add(recipeIdentifier)) {
                throw new IllegalStateException("Duplicate recipe " + recipeIdentifier);
            }

            JsonObject recipeJson = provider.toJson();
            ConditionJsonProvider[] conditions = FabricDataGenHelper.consumeConditions(provider);
            ConditionJsonProvider.write(recipeJson, conditions);

            saveRecipe(writer, recipeJson, ((IRecipeProvider)this).getRecipesPathResolver().resolveJson(recipeIdentifier));
            JsonObject advancementJson = provider.toAdvancementJson();

            if (advancementJson != null) {
                ConditionJsonProvider.write(advancementJson, conditions);
                saveRecipeAdvancement(writer, advancementJson, ((IRecipeProvider) this).getAdvancementsPathResolver().resolveJson(advancementIdentifier));
            }
        });
    }

    @Override
    public void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
        // Vanilla Overrides
        createShapedRecipe(List.of("# #", "# #"),Items.NETHERITE_INGOT, Items.NETHERITE_BOOTS).setNamespace(Prog.VANILLA_ID).setPath("netherite_boots_smithing").offer(exporter);
        createShapedRecipe(List.of("# #", "###", "###"), Items.NETHERITE_INGOT, Items.NETHERITE_CHESTPLATE).setNamespace(Prog.VANILLA_ID).setPath("netherite_chestplate_smithing").offer(exporter);
        createShapedRecipe(List.of("###", "# #"), Items.NETHERITE_INGOT, Items.NETHERITE_HELMET).setNamespace(Prog.VANILLA_ID).setPath("netherite_helmet_smithing").offer(exporter);
        createShapedRecipe(List.of("###", "# #", "# #"), Items.NETHERITE_INGOT, Items.NETHERITE_LEGGINGS).setNamespace(Prog.VANILLA_ID).setPath("netherite_leggings_smithing").offer(exporter);

        // Misc
        createShapedRecipe(List.of("# #", " # ", "# #"), PItems.STEEL_INGOT, PItems.STEEL_BINDING).offer(exporter);
        createCookingRecipe(RecipeSerializer.SMELTING, Items.IRON_INGOT, PItems.STEEL_INGOT, 300,0.45F).offer(exporter);
        offerReversibleCompactingRecipes(exporter, PItems.STEEL_INGOT, PBlocks.STEEL_BLOCK);

        // Armor
        createShapedRecipe(List.of("# #", "# #"), PItems.STEEL_INGOT, PItems.STEEL_BOOTS).offer(exporter);
        createShapedRecipe(List.of("# #", "###", "###"), PItems.STEEL_INGOT, PItems.STEEL_CHESTPLATE).offer(exporter);
        createShapedRecipe(List.of("###", "# #"), PItems.STEEL_INGOT, PItems.STEEL_HELMET).offer(exporter);
        createShapedRecipe(List.of("###", "# #", "# #"), PItems.STEEL_INGOT, PItems.STEEL_LEGGINGS).offer(exporter);

        // Tools
        createShapedRecipe(List.of("##", "#I", " I"), List.of(PItems.STEEL_INGOT, Items.STICK), PItems.STEEL_AXE).offer(exporter);
        createShapedRecipe(List.of("##", " I", " I"), List.of(PItems.STEEL_INGOT, Items.STICK), PItems.STEEL_HOE).offer(exporter);
        createShapedRecipe(List.of("###", " I ", " I "), List.of(PItems.STEEL_INGOT, Items.STICK), PItems.STEEL_PICKAXE).offer(exporter);
        createShapedRecipe(List.of("#", "I", "I"), List.of(PItems.STEEL_INGOT, Items.STICK), PItems.STEEL_SHOVEL).offer(exporter);

        // Weapons
        createShapedRecipe(List.of("#", "#", "I"), List.of(PItems.STEEL_INGOT, Items.STICK), PItems.STEEL_SWORD).offer(exporter);

        // Tier Upgrades
        createSmithingRecipe(Items.IRON_AXE, PItems.STEEL_INGOT, PItems.STEEL_AXE).offer(exporter);

    }
}
