package com.prog.data;

import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.prog.XIDs;
import com.prog.Prog;
import com.prog.data.custom.FlexibleShapedRecipeJsonBuilder;
import com.prog.data.custom.FlexibleShapelessRecipeJsonBuilder;
import com.prog.data.custom.NbtSmithingRecipeJsonBuilder;
import com.prog.itemOrBlock.*;
import com.prog.itemOrBlock.data.FlexibleCraftingData;
import com.prog.recipe.PRecipeSerializers;
import com.prog.utils.ItemUtils;
import com.prog.utils.UpgradeUtils;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ConditionJsonProvider;
import net.fabricmc.fabric.impl.datagen.FabricDataGenHelper;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataWriter;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.CookingRecipeSerializer;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.TagKey;
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
        public final Ingredient ingredient;
        public final ItemConvertible item;
        public final TagKey<Item> tag;
        public final int size;

        public Input(int size, char identifier, Ingredient ingredient, ItemConvertible item, TagKey<Item> tag) {
            this.identifier = identifier;
            this.size = size;
            this.ingredient = ingredient;
            this.item = item;
            this.tag = tag;
        }

        public Input(char identifier, ItemConvertible item) {
            this(1, identifier, Ingredient.ofItems(item), item, null);
        }

        public Input(char identifier, TagKey<Item> tag) {
            this(1, identifier, Ingredient.fromTag(tag), null, tag);
        }

        public Input(int size, ItemConvertible item) {
            this(size, Character.MIN_VALUE, Ingredient.ofItems(item), item, null);
        }

        public Input(int size, TagKey<Item> tag) {
            this(size, Character.MIN_VALUE, Ingredient.fromTag(tag), null, tag);
        }

        public static Input of(char identifier, ItemConvertible item) {
            return new Input(identifier, item);
        }

        public static Input of(char identifier, TagKey<Item> tag) {
            return new Input(identifier, tag);
        }

        public static Input of(ItemConvertible item) {
            return new Input(Character.MIN_VALUE, item);
        }

        public static Input of(TagKey<Item> tag) {
            return new Input(Character.MIN_VALUE, tag);
        }

        public static Input of(ItemConvertible item, int size) {
            return new Input(size, item);
        }

        public static Input of(TagKey<Item> tag, int size) {
            return new Input(size, tag);
        }
    }

    public static class ShapedRecipeBuilderWrapper {
        private final FlexibleShapedRecipeJsonBuilder internalBuilder;
        private String path;
        private String namespace = null;

        public ShapedRecipeBuilderWrapper(FlexibleShapedRecipeJsonBuilder internalBuilder, String path) {
            this.internalBuilder = internalBuilder;
            this.path = path;
        }

        public ShapedRecipeBuilderWrapper require(RecipeSerializer<?> recipeSerializer) {
            internalBuilder.setSerializer(recipeSerializer);
            return this;
        }

        public ShapedRecipeBuilderWrapper requireAssembly() {
            internalBuilder.requireAssembly();
            return this;
        }

        public ShapedRecipeBuilderWrapper requireCosmicConstructor() {
            internalBuilder.requireCosmicConstructor();
            return this;
        }

        public ShapedRecipeBuilderWrapper pattern(String pattern) {
            internalBuilder.pattern(pattern);
            return this;
        }

        public ShapedRecipeBuilderWrapper input(Character identifier, Ingredient ingredient) {
            internalBuilder.input(identifier, ingredient);
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

    public static class ShapelessRecipeBuilderWrapper {
        private final FlexibleShapelessRecipeJsonBuilder internalBuilder;
        private String path;
        private String namespace = null;

        public ShapelessRecipeBuilderWrapper(FlexibleShapelessRecipeJsonBuilder internalBuilder, String path) {
            this.internalBuilder = internalBuilder;
            this.path = path;
        }

        public ShapelessRecipeBuilderWrapper require(RecipeSerializer<?> recipeSerializer) {
            internalBuilder.setSerializer(recipeSerializer);
            return this;
        }

        public ShapelessRecipeBuilderWrapper requireAssembly() {
            internalBuilder.requireAssembly();
            return this;
        }

        public ShapelessRecipeBuilderWrapper requireCosmicConstructor() {
            internalBuilder.requireCosmicConstructor();
            return this;
        }

        public ShapelessRecipeBuilderWrapper input(Ingredient ingredient, int size) {
            internalBuilder.input(ingredient, size);
            return this;
        }

        public ShapelessRecipeBuilderWrapper input(Ingredient ingredient) {
            internalBuilder.input(ingredient, 1);
            return this;
        }

        public ShapelessRecipeBuilderWrapper criterion(String criterionName, net.minecraft.advancement.criterion.CriterionConditions conditions) {
            internalBuilder.criterion(criterionName, conditions);
            return this;
        }

        public ShapelessRecipeBuilderWrapper setPath(String path) {
            this.path = path;
            return this;
        }

        public ShapelessRecipeBuilderWrapper setNamespace(String namespace) {
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
        private final NbtSmithingRecipeJsonBuilder internalBuilder;
        private String path;
        private String namespace = Prog.MOD_ID;

        public SmithingRecipeBuilderWrapper(NbtSmithingRecipeJsonBuilder internalBuilder, String path) {
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

        public SmithingRecipeBuilderWrapper addBaseNbt(String property, JsonElement value, boolean require) {
            internalBuilder.addBaseNbt(property, value, require);
            return this;
        }

        public SmithingRecipeBuilderWrapper addAdditionNbt(String property, JsonElement value, boolean require) {
            internalBuilder.addAdditionNbt(property, value, require);
            return this;
        }

        public SmithingRecipeBuilderWrapper addAdditionNbt(String property, JsonElement value) {
            internalBuilder.addAdditionNbt(property, value);
            return this;
        }

        public SmithingRecipeBuilderWrapper addResultNbt(String property, JsonElement value, boolean merge) {
            internalBuilder.addResultNbt(property, value, merge);
            return this;
        }

        public SmithingRecipeBuilderWrapper addResultNbt(String property, JsonElement value) {
            internalBuilder.addResultNbt(property, value);
            return this;
        }

        public Identifier getId() {
            return new Identifier(namespace, path);
        }

        public void offer(Consumer<RecipeJsonProvider> exporter) {
            internalBuilder.offerTo(exporter, getId());
        }
    }


    public PRecipeProvider(FabricDataGenerator generator) {
        super(generator);
    }

    public static ShapedRecipeBuilderWrapper createShapedRecipeSpecific(List<String> pattern, List<Input> inputs, ItemConvertible output, int outputCount) {
        FlexibleShapedRecipeJsonBuilder internalBuilder = FlexibleShapedRecipeJsonBuilder.create(output, outputCount);
        ShapedRecipeBuilderWrapper builder = new ShapedRecipeBuilderWrapper(internalBuilder, getItemPath(output));

        pattern.forEach(builder::pattern);
        inputs.forEach(input -> builder.input(input.identifier, input.ingredient));
        inputs.forEach(input -> builder.criterion(hasInput(input), conditionsFromInput(input)));

        return builder;
    }

    public static ShapedRecipeBuilderWrapper createShapedRecipeSpecific(List<String> pattern, List<Input> inputs, ItemConvertible output) {
        return createShapedRecipeSpecific(pattern, inputs, output, 1);
    }

    public static ShapedRecipeBuilderWrapper createShapedRecipe(List<String> pattern, List<Input> inputs, ItemConvertible output, int outputCount) {
        List<Character> uniqueChars = ShapedRecipeUtils.getUniqueChars(pattern);
        List<Input> inputsMapped = IntStream.range(0, Math.min(uniqueChars.size(), inputs.size()))
                .mapToObj(i -> new Input(1, uniqueChars.get(i), inputs.get(i).ingredient, inputs.get(i).item, inputs.get(i).tag))
                .toList();
        return createShapedRecipeSpecific(pattern, inputsMapped, output, outputCount);
    }

    public static ShapedRecipeBuilderWrapper createShapedRecipe(List<String> pattern, List<Input> inputs, ItemConvertible output) {
        return createShapedRecipe(pattern, inputs, output, 1);
    }

    public static ShapedRecipeBuilderWrapper createShapedRecipe(List<String> pattern, Input input, ItemConvertible output, int outputCount) {
        return createShapedRecipe(pattern, List.of(input), output, outputCount);
    }

    public static ShapedRecipeBuilderWrapper createShapedRecipe(List<String> pattern, Input input, ItemConvertible output) {
        return createShapedRecipe(pattern, input, output, 1);
    }

    public static void offerReversibleCompactingRecipes(Consumer<RecipeJsonProvider> exporter, ItemConvertible item, ItemConvertible compacted, FlexibleCraftingData recipeType) {
        var compact = createShapedRecipe(List.of("###", "###", "###"), Input.of(item), compacted);
        var uncompact = createShapelessRecipe(Input.of(compacted), item, 9);
        if (recipeType != null) {
            compact.require(recipeType.shapedSerializer.get());
            uncompact.require(recipeType.shapelessSerializer.get());
        }
        compact.offer(exporter);
        uncompact.offer(exporter);
    }

    public static void offerReversibleCompactingRecipes(Consumer<RecipeJsonProvider> exporter, ItemConvertible item, ItemConvertible compacted) {
        offerReversibleCompactingRecipes(exporter, item, compacted, null);
    }

    public static ShapelessRecipeBuilderWrapper createShapelessRecipe(List<Input> inputs, ItemConvertible output, int outputCount) {
        FlexibleShapelessRecipeJsonBuilder internalBuilder = FlexibleShapelessRecipeJsonBuilder.create(output, outputCount);
        ShapelessRecipeBuilderWrapper builder = new ShapelessRecipeBuilderWrapper(internalBuilder, getItemPath(output));

        inputs.forEach(input -> builder.input(input.ingredient, input.size));
        inputs.forEach(input -> builder.criterion(hasInput(input), conditionsFromInput(input)));

        return builder;
    }

    public static ShapelessRecipeBuilderWrapper createShapelessRecipe(List<Input> inputs, ItemConvertible output) {
        return createShapelessRecipe(inputs, output, 1);
    }

    public static ShapelessRecipeBuilderWrapper createShapelessRecipe(Input input, ItemConvertible output, int ouputCount) {
        return createShapelessRecipe(List.of(input), output, ouputCount);
    }

    public static ShapelessRecipeBuilderWrapper createShapelessRecipe(Input input, ItemConvertible output) {
        return createShapelessRecipe(input, output, 1);
    }

    public static CookingRecipeBuilderWrapper createCookingRecipe(CookingRecipeSerializer<?> serializer, Input input, ItemConvertible output, int cookingTime, float experience) {
        CookingRecipeJsonBuilder internalBuilder = CookingRecipeJsonBuilder.create(ingredientFromInput(input), output, experience, cookingTime, serializer);
        String defaultPath = getItemPath(output) + "_from_" + Registry.RECIPE_SERIALIZER.getId(serializer).getPath();
        CookingRecipeBuilderWrapper builder = new CookingRecipeBuilderWrapper(internalBuilder, defaultPath);

        builder.criterion(hasInput(input), conditionsFromInput(input));

        return builder;
    }

    public static SmithingRecipeBuilderWrapper createSmithingRecipe(Input baseInput, Input consumeInput, Item output) {
        NbtSmithingRecipeJsonBuilder internalBuilder = NbtSmithingRecipeJsonBuilder.create(ingredientFromInput(baseInput), ingredientFromInput(consumeInput), output);
        String defaultPath = getItemPath(output) + "_from_smithing";
        SmithingRecipeBuilderWrapper builder = new SmithingRecipeBuilderWrapper(internalBuilder, defaultPath);

        builder.criterion(hasInput(baseInput), conditionsFromInput(baseInput));
        builder.criterion(hasInput(consumeInput), conditionsFromInput(consumeInput));

        return builder;
    }


    public static String hasTag(TagKey<?> tag) {
        String[] parts = tag.id().getPath().split("/");
        return "has_" + String.join("_", parts);
    }

    public static String hasInput(Input input){
        return input.item != null ? hasItem(input.item) : hasTag(input.tag);
    }

    public static CriterionConditions conditionsFromInput(Input input){
        return input.item != null ? conditionsFromItem(input.item) : conditionsFromTag(input.tag);
    }

    public static Ingredient ingredientFromInput(Input input) {
        return input.item != null ? Ingredient.ofItems(input.item) : Ingredient.fromTag(input.tag);
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

            saveRecipe(writer, recipeJson, this.recipesPathResolver.resolveJson(recipeIdentifier));
            JsonObject advancementJson = provider.toAdvancementJson();

            if (advancementJson != null) {
                ConditionJsonProvider.write(advancementJson, conditions);
                saveRecipeAdvancement(writer, advancementJson, this.advancementsPathResolver.resolveJson(advancementIdentifier));
            }
        });
    }

    @Override
    public void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
        // Vanilla Overrides
        createShapedRecipe(List.of("# #", "# #"),Input.of(Items.NETHERITE_INGOT), Items.NETHERITE_BOOTS).requireAssembly().setNamespace(XIDs.VANILLA).setPath("netherite_boots_smithing").offer(exporter);
        createShapedRecipe(List.of("# #", "###", "###"), Input.of(Items.NETHERITE_INGOT), Items.NETHERITE_CHESTPLATE).requireAssembly().setNamespace(XIDs.VANILLA).setPath("netherite_chestplate_smithing").offer(exporter);
        createShapedRecipe(List.of("###", "# #"), Input.of(Items.NETHERITE_INGOT), Items.NETHERITE_HELMET).requireAssembly().setNamespace(XIDs.VANILLA).setPath("netherite_helmet_smithing").offer(exporter);
        createShapedRecipe(List.of("###", "# #", "# #"), Input.of(Items.NETHERITE_INGOT), Items.NETHERITE_LEGGINGS).requireAssembly().setNamespace(XIDs.VANILLA).setPath("netherite_leggings_smithing").offer(exporter);
        createShapedRecipe(List.of("##", "#I", " I"), List.of(Input.of(Items.NETHERITE_INGOT), Input.of(Items.STICK)), Items.NETHERITE_AXE).requireAssembly().setNamespace(XIDs.VANILLA).setPath("netherite_axe_smithing").offer(exporter);
        createShapedRecipe(List.of("##", " I", " I"), List.of(Input.of(Items.NETHERITE_INGOT), Input.of(Items.STICK)), Items.NETHERITE_HOE).requireAssembly().setNamespace(XIDs.VANILLA).setPath("netherite_hoe_smithing").offer(exporter);
        createShapedRecipe(List.of("###", " I ", " I "), List.of(Input.of(Items.NETHERITE_INGOT), Input.of(Items.STICK)), Items.NETHERITE_PICKAXE).requireAssembly().setNamespace(XIDs.VANILLA).setPath("netherite_pickaxe_smithing").offer(exporter);
        createShapedRecipe(List.of("#", "I", "I"), List.of(Input.of(Items.NETHERITE_INGOT), Input.of(Items.STICK)), Items.NETHERITE_SHOVEL).requireAssembly().setNamespace(XIDs.VANILLA).setPath("netherite_shovel_smithing").offer(exporter);
        createShapedRecipe(List.of("#", "#", "I"), List.of(Input.of(Items.NETHERITE_INGOT), Input.of(Items.STICK)), Items.NETHERITE_SWORD).requireAssembly().setNamespace(XIDs.VANILLA).setPath("netherite_sword_smithing").offer(exporter);
        createShapedRecipe(List.of("  sss", " sGs ", "sss  "), List.of(Input.of(Items.NETHERITE_SCRAP), Input.of(Blocks.GOLD_BLOCK)), Items.NETHERITE_INGOT, 2).requireAssembly().setNamespace(XIDs.VANILLA).setPath("netherite_ingot").offer(exporter);
        createShapedRecipe(List.of("###", "psp", "psp"), List.of(Input.of(PItems.STEEL_INGOT), Input.of(ItemTags.PLANKS), Input.of(Items.STONE)), Items.SMITHING_TABLE).setNamespace(XIDs.VANILLA).setPath("smithing_table").offer(exporter);

        // Misc
        createCookingRecipe(RecipeSerializer.BLASTING, Input.of(Items.IRON_INGOT), PItems.STEEL_INGOT, 300, 0.45F).offer(exporter);
        createShapedRecipe(List.of("sc ", "coc", " cs"), List.of(Input.of(PItems.STEEL_INGOT), Input.of(Items.COPPER_INGOT), Input.of(Items.OBSIDIAN)), PBlocks.MACHINE_FRAME).offer(exporter);
        createShapedRecipe(List.of("crc", "rgr", "crc"), List.of(Input.of(Items.COPPER_INGOT), Input.of(Items.REDSTONE), Input.of(Items.GOLD_INGOT)), PItems.MACHINE_CIRCUIT).offer(exporter);
        offerReversibleCompactingRecipes(exporter, PItems.STEEL_INGOT, PBlocks.STEEL_BLOCK);
        createCookingRecipe(PRecipeSerializers.INCINERATOR, Input.of(Items.OBSIDIAN), PItems.REFINED_OBSIDIAN_INGOT, 1500, 2F).offer(exporter);
        offerReversibleCompactingRecipes(exporter, PItems.EMBERITE, PBlocks.EMBERITE_BLOCK);
        createCookingRecipe(PRecipeSerializers.INCINERATOR, Input.of(PBlocks.EMBERITE_ORE), PItems.EMBERITE, 600, 1F).offer(exporter);
        createShapedRecipe(List.of(" qcq ", "ccecc", " qcq "), List.of(Input.of(Items.QUARTZ), Input.of(PItems.COMPRESSED_QUARTZ), Input.of(PItems.EMBERITE)), PBlocks.QUARTZ_CATALYST).requireAssembly().offer(exporter);
        createShapedRecipe(List.of(" lCl ", "qCMCq", "lrCrl"), List.of(Input.of(Items.LEATHER), Input.of(PItems.MACHINE_CIRCUIT), Input.of(PItems.COMPRESSED_QUARTZ), Input.of(PItems.REFINED_OBSIDIAN_MODULE), Input.of(Items.REDSTONE)), PItems.MECHANICAL_BOOTS).requireAssembly().offer(exporter);
        createCookingRecipe(PRecipeSerializers.INCINERATOR, Input.of(Items.QUARTZ), PItems.COMPRESSED_QUARTZ, 300, 2F).offer(exporter);
        offerReversibleCompactingRecipes(exporter, PItems.VERUM_INGOT, PBlocks.VERUM_BLOCK, FlexibleCraftingData.COSMIC_CONSTRUCTOR);
        createCookingRecipe(PRecipeSerializers.COSMIC_INCUBATOR, Input.of(PBlocks.VERUM_ORE), PItems.RAW_VERUM, 7500, 1F).offer(exporter);
        createCookingRecipe(PRecipeSerializers.COSMIC_INCUBATOR, Input.of(PItems.RAW_VERUM), PItems.VERUM_INGOT, 7500, 5F).offer(exporter);
        createCookingRecipe(PRecipeSerializers.COSMIC_INCUBATOR, Input.of(Items.AMETHYST_BLOCK), Items.BUDDING_AMETHYST, 50000, 5F).offer(exporter);
        createShapedRecipe(List.of(" a ", "aoa", " a "), List.of(Input.of(Items.AMETHYST_SHARD), Input.of(Items.OBSIDIAN)), Items.CRYING_OBSIDIAN).requireCosmicConstructor().offer(exporter);
        createShapedRecipe(List.of("GGG", "GAG", "GGG"), List.of(Input.of(Items.GOLD_BLOCK), Input.of(Items.APPLE)), Items.ENCHANTED_GOLDEN_APPLE).requireAssembly().offer(exporter);
        createShapedRecipe(List.of("nnn", "nAn", "nnn"), List.of(Input.of(Items.NETHER_STAR), Input.of(Items.APPLE)), PItems.STAR_APPLE).requireAssembly().offer(exporter);
        offerReversibleCompactingRecipes(exporter, Items.NETHER_STAR, PBlocks.NETHER_STAR_BLOCK, FlexibleCraftingData.COSMIC_CONSTRUCTOR);
        createShapedRecipe(List.of("NNN", "NAN", "NNN"), List.of(Input.of(PBlocks.NETHER_STAR_BLOCK), Input.of(Items.APPLE)), PItems.ENCHANTED_STAR_APPLE).requireCosmicConstructor().offer(exporter);
        createShapedRecipe(List.of(" c ", "eCe", " c "), List.of(Input.of(PItems.COMPRESSED_QUARTZ), Input.of(Items.ENDER_PEARL), Input.of(PItems.MACHINE_CIRCUIT)), PItems.TELEPORTATION_CORE).requireAssembly().offer(exporter);
        createCookingRecipe(RecipeSerializer.SMOKING, Input.of(Items.ROTTEN_FLESH), Items.LEATHER, 1000, 5F).offer(exporter);
        createShapedRecipe(List.of("A c", "Ic ", "AIA"), List.of(Input.of(Items.ANVIL), Input.of(Items.CHAIN), Input.of(Items.IRON_BLOCK)), PItems.ANCHOR).offer(exporter);
        createShapedRecipe(List.of("sps", "pwp", "sps"), List.of(Input.of(Items.STICK), Input.of(Items.PHANTOM_MEMBRANE), Input.of(Items.COBWEB)), PItems.DREAM_CATCHER).offer(exporter);
        createShapedRecipe(List.of("ele", "lcl", " l "), List.of(Input.of(Items.ECHO_SHARD), Input.of(PItems.LIVING_SOUL_FRAGMENT), Input.of(Items.SCULK_CATALYST)), PItems.SILENT_HEART).offer(exporter);
        createShapedRecipe(List.of("nsn", "bHu", "thf"), List.of(Input.of(Items.NAUTILUS_SHELL), Input.of(Items.SPONGE), Input.of(Items.BRAIN_CORAL), Input.of(Items.HEART_OF_THE_SEA), Input.of(Items.BUBBLE_CORAL), Input.of(Items.TUBE_CORAL), Input.of(Items.HORN_CORAL), Input.of(Items.FIRE_CORAL)), PItems.OCEANS_GRACE).offer(exporter);
        createShapelessRecipe(List.of(Input.of(Items.BOWL), Input.of(Items.DRAGON_EGG), Input.of(Items.CHORUS_FLOWER), Input.of(Items.CRYING_OBSIDIAN), Input.of(Items.AMETHYST_SHARD), Input.of(Items.SHULKER_SHELL)), PItems.COSMIC_SOUP).requireCosmicConstructor().offer(exporter);
        createShapedRecipe(List.of(" fcf ", " mcm ", "oeoeo"), List.of(Input.of(PBlocks.MACHINE_FRAME), Input.of(PItems.MACHINE_CIRCUIT), Input.of(PItems.REFINED_OBSIDIAN_MODULE), Input.of(Items.OBSIDIAN), Input.of(PItems.EMBERITE)), PJetpacks.MECHANICAL.item.get()).requireAssembly().offer(exporter);
        createShapedRecipe(List.of("ffgff", "fg gf", "  g  "), List.of(Input.of(Items.FEATHER), Input.of(Items.GOLD_INGOT)), PItems.ANGEL_RING).requireAssembly().offer(exporter);


        // Tier upgrades
        createShapedRecipe(List.of("# #", " # ", "# #"), Input.of(PItems.STEEL_INGOT), PItems.STEEL_BINDING).offer(exporter);
        createShapedRecipe(List.of("sd ", "ded", " ds"), List.of(Input.of(PItems.STEEL_INGOT), Input.of(Items.DIAMOND), Input.of(Items.EMERALD)), PItems.DIAMOND_HEART).offer(exporter);
        createShapedRecipe(List.of("gn ", "ndn", " ng"), List.of(Input.of(Items.GOLD_INGOT), Input.of(Items.NETHERITE_INGOT), Input.of(Items.DIAMOND)), PItems.NETHERITE_HEART).offer(exporter);
        createShapedRecipe(List.of("cr#rc", "c#g#c", "cr#rc"), List.of(Input.of(Items.COPPER_INGOT), Input.of(Items.REDSTONE), Input.of(PItems.REFINED_OBSIDIAN_INGOT), Input.of(Items.GOLD_INGOT)), PItems.REFINED_OBSIDIAN_MODULE).requireAssembly().offer(exporter);
        createShapedRecipe(List.of("  cce", " eCe ", "ecc  "), List.of(Input.of(PItems.COMPRESSED_QUARTZ), Input.of(PItems.EMBERITE), Input.of(PBlocks.QUARTZ_CATALYST)), PItems.TITAN_INGOT).requireAssembly().offer(exporter);
        createShapedRecipe(List.of(" t ", "tdt", " t "), List.of(Input.of(PItems.TITAN_INGOT), Input.of(Items.DIAMOND)), PItems.TITAN_CORE).requireAssembly().offer(exporter);
        createShapedRecipe(List.of("avava", "cavac", "cvdvc", "ce ec"), List.of(Input.of(Items.AMETHYST_SHARD), Input.of(PItems.VERUM_INGOT), Input.of(Items.CRYING_OBSIDIAN), Input.of(Items.DRAGON_HEAD), Input.of(Items.ENDER_EYE)), PItems.VOID_SOUL_HELMET).requireCosmicConstructor().offer(exporter);
        createShapedRecipe(List.of("av va", "cavac", "ceEec", "cavac", "avcva"), List.of(Input.of(Items.AMETHYST_SHARD), Input.of(PItems.VERUM_INGOT), Input.of(Items.CRYING_OBSIDIAN), Input.of(Items.ENDER_EYE), Input.of(Items.ELYTRA)), PItems.VOID_SOUL_CHESTPLATE).requireCosmicConstructor().offer(exporter);
        createShapedRecipe(List.of("aeaea", "csvsc", "vaaav", "va av", "ca ac"), List.of(Input.of(Items.AMETHYST_SHARD), Input.of(Items.ENDER_EYE), Input.of(Items.CRYING_OBSIDIAN), Input.of(Items.SHULKER_SHELL), Input.of(PItems.VERUM_INGOT)), PItems.VOID_SOUL_LEGGINGS).requireCosmicConstructor().offer(exporter);
        createShapedRecipe(List.of("avdva", "ve ev", "cc cc"), List.of(Input.of(Items.AMETHYST_SHARD), Input.of(PItems.VERUM_INGOT), Input.of(Blocks.DRAGON_EGG), Input.of(Items.ENDER_EYE), Input.of(Items.CRYING_OBSIDIAN)), PItems.VOID_SOUL_BOOTS).requireCosmicConstructor().offer(exporter);
        createShapedRecipe(List.of("  e  ", " vav ", "cadac", " vav ", "  e  "), List.of(Input.of(Items.ENDER_EYE), Input.of(PItems.VERUM_INGOT), Input.of(Items.AMETHYST_SHARD), Input.of(Items.CRYING_OBSIDIAN), Input.of(Items.DRAGON_BREATH)), PItems.VOID_SOUL_TOOL).requireCosmicConstructor().offer(exporter);

        // Machines
        createShapedRecipe(List.of("frf", "CcC", "rRr"), List.of(Input.of(PBlocks.MACHINE_FRAME), Input.of(Items.REDSTONE), Input.of(PItems.MACHINE_CIRCUIT), Input.of(Items.CRAFTING_TABLE), Input.of(Blocks.REDSTONE_BLOCK)), PBlocks.ASSEMBLY).offer(exporter);
        createShapedRecipe(List.of("obBbo", "on no", "ofbfo"), List.of(Input.of(Blocks.OBSIDIAN), Input.of(Items.BLAZE_ROD), Input.of(Items.BLAST_FURNACE), Input.of(PBlocks.MACHINE_FRAME), Input.of(Blocks.NETHERRACK)), PBlocks.INCINERATOR).requireAssembly().offer(exporter);
        createShapedRecipe(List.of("gaaag", "EqeqE", "gcAcg"), List.of(Input.of(Items.GOLD_INGOT), Input.of(Items.AMETHYST_SHARD), Input.of(Items.END_STONE), Input.of(PBlocks.QUARTZ_CATALYST), Input.of(Items.ENDER_EYE), Input.of(Blocks.CRYING_OBSIDIAN), Input.of(PBlocks.ASSEMBLY)), PBlocks.COSMIC_CONSTRUCTOR).requireAssembly().offer(exporter);
        createShapedRecipe(List.of("caRac", "deqed", "RM MR", "cCICc", "EEDEE"), List.of(Input.of(Items.CRYING_OBSIDIAN), Input.of(Items.AMETHYST_SHARD), Input.of(PItems.REFINED_OBSIDIAN_MODULE), Input.of(PItems.DIAMOND_HEART), Input.of(Items.ENDER_EYE), Input.of(PBlocks.QUARTZ_CATALYST), Input.of(PBlocks.MACHINE_FRAME), Input.of(PItems.MACHINE_CIRCUIT), Input.of(PBlocks.INCINERATOR), Input.of(Items.END_STONE), Input.of(Blocks.DRAGON_EGG)), PBlocks.COSMIC_INCUBATOR).requireCosmicConstructor().offer(exporter);

        // Tier Upgrades
        // Steel
        createSmithingRecipe(Input.of(Items.IRON_BOOTS), Input.of(PItems.STEEL_BINDING), PItems.STEEL_BOOTS).offer(exporter);
        createSmithingRecipe(Input.of(Items.IRON_CHESTPLATE), Input.of(PItems.STEEL_BINDING), PItems.STEEL_CHESTPLATE).offer(exporter);
        createSmithingRecipe(Input.of(Items.IRON_HELMET), Input.of(PItems.STEEL_BINDING), PItems.STEEL_HELMET).offer(exporter);
        createSmithingRecipe(Input.of(Items.IRON_LEGGINGS), Input.of(PItems.STEEL_BINDING), PItems.STEEL_LEGGINGS).offer(exporter);

        createSmithingRecipe(Input.of(Items.IRON_AXE), Input.of(PItems.STEEL_BINDING), PItems.STEEL_AXE).offer(exporter);
        createSmithingRecipe(Input.of(Items.IRON_HOE), Input.of(PItems.STEEL_BINDING), PItems.STEEL_HOE).offer(exporter);
        createSmithingRecipe(Input.of(Items.IRON_PICKAXE), Input.of(PItems.STEEL_BINDING), PItems.STEEL_PICKAXE).offer(exporter);
        createSmithingRecipe(Input.of(Items.IRON_SHOVEL), Input.of(PItems.STEEL_BINDING), PItems.STEEL_SHOVEL).offer(exporter);

        createSmithingRecipe(Input.of(Items.BOW), Input.of(PItems.STEEL_BINDING), PItems.STEEL_BOW).offer(exporter);
        createSmithingRecipe(Input.of(Items.IRON_SWORD), Input.of(PItems.STEEL_BINDING), PItems.STEEL_SWORD).offer(exporter);

        // Ultimate diamond
        createSmithingRecipe(Input.of(PItems.STEEL_BOOTS), Input.of(Items.DIAMOND_BOOTS), PItems.ULTIMATE_DIAMOND_BOOTS).offer(exporter);
        createSmithingRecipe(Input.of(PItems.STEEL_CHESTPLATE), Input.of(Items.DIAMOND_CHESTPLATE), PItems.ULTIMATE_DIAMOND_CHESTPLATE).offer(exporter);
        createSmithingRecipe(Input.of(PItems.STEEL_HELMET), Input.of(Items.DIAMOND_HELMET), PItems.ULTIMATE_DIAMOND_HELMET).offer(exporter);
        createSmithingRecipe(Input.of(PItems.STEEL_LEGGINGS), Input.of(Items.DIAMOND_LEGGINGS), PItems.ULTIMATE_DIAMOND_LEGGINGS).offer(exporter);

        createSmithingRecipe(Input.of(PItems.STEEL_AXE), Input.of(Items.DIAMOND_AXE), PItems.ULTIMATE_DIAMOND_AXE).offer(exporter);
        createSmithingRecipe(Input.of(PItems.STEEL_HOE), Input.of(Items.DIAMOND_HOE), PItems.ULTIMATE_DIAMOND_HOE).offer(exporter);
        createSmithingRecipe(Input.of(PItems.STEEL_PICKAXE), Input.of(Items.DIAMOND_PICKAXE), PItems.ULTIMATE_DIAMOND_PICKAXE).offer(exporter);
        createSmithingRecipe(Input.of(PItems.STEEL_SHOVEL), Input.of(Items.DIAMOND_SHOVEL), PItems.ULTIMATE_DIAMOND_SHOVEL).offer(exporter);

        createSmithingRecipe(Input.of(PItems.STEEL_BOW), Input.of(PItems.DIAMOND_HEART), PItems.ULTIMATE_DIAMOND_BOW).offer(exporter);
        createSmithingRecipe(Input.of(PItems.STEEL_SWORD), Input.of(Items.DIAMOND_SWORD), PItems.ULTIMATE_DIAMOND_SWORD).offer(exporter);

        // Refined obsidian
        createSmithingRecipe(Input.of(PItems.ULTIMATE_DIAMOND_BOOTS), Input.of(PItems.REFINED_OBSIDIAN_MODULE), PItems.REFINED_OBSIDIAN_BOOTS).offer(exporter);
        createSmithingRecipe(Input.of(PItems.ULTIMATE_DIAMOND_CHESTPLATE), Input.of(PItems.REFINED_OBSIDIAN_MODULE), PItems.REFINED_OBSIDIAN_CHESTPLATE).offer(exporter);
        createSmithingRecipe(Input.of(PItems.ULTIMATE_DIAMOND_HELMET), Input.of(PItems.REFINED_OBSIDIAN_MODULE), PItems.REFINED_OBSIDIAN_HELMET).offer(exporter);
        createSmithingRecipe(Input.of(PItems.ULTIMATE_DIAMOND_LEGGINGS), Input.of(PItems.REFINED_OBSIDIAN_MODULE), PItems.REFINED_OBSIDIAN_LEGGINGS).offer(exporter);

        createSmithingRecipe(Input.of(PItems.ULTIMATE_DIAMOND_AXE), Input.of(PItems.REFINED_OBSIDIAN_MODULE), PItems.REFINED_OBSIDIAN_AXE).offer(exporter);
        createSmithingRecipe(Input.of(PItems.ULTIMATE_DIAMOND_HOE), Input.of(PItems.REFINED_OBSIDIAN_MODULE), PItems.REFINED_OBSIDIAN_HOE).offer(exporter);
        createSmithingRecipe(Input.of(PItems.ULTIMATE_DIAMOND_PICKAXE), Input.of(PItems.REFINED_OBSIDIAN_MODULE), PItems.REFINED_OBSIDIAN_PICKAXE).offer(exporter);
        createSmithingRecipe(Input.of(PItems.ULTIMATE_DIAMOND_SHOVEL), Input.of(PItems.REFINED_OBSIDIAN_MODULE), PItems.REFINED_OBSIDIAN_SHOVEL).offer(exporter);

        createSmithingRecipe(Input.of(PItems.ULTIMATE_DIAMOND_BOW), Input.of(PItems.REFINED_OBSIDIAN_MODULE), PItems.REFINED_OBSIDIAN_BOW).offer(exporter);
        createSmithingRecipe(Input.of(PItems.ULTIMATE_DIAMOND_SWORD), Input.of(PItems.REFINED_OBSIDIAN_MODULE), PItems.REFINED_OBSIDIAN_SWORD).offer(exporter);

        // Titan
        createSmithingRecipe(Input.of(PItems.REFINED_OBSIDIAN_BOOTS), Input.of(PItems.TITAN_CORE), PItems.TITAN_BOOTS).offer(exporter);
        createSmithingRecipe(Input.of(PItems.REFINED_OBSIDIAN_CHESTPLATE), Input.of(PItems.TITAN_CORE), PItems.TITAN_CHESTPLATE).offer(exporter);
        createSmithingRecipe(Input.of(PItems.REFINED_OBSIDIAN_HELMET), Input.of(PItems.TITAN_CORE), PItems.TITAN_HELMET).offer(exporter);
        createSmithingRecipe(Input.of(PItems.REFINED_OBSIDIAN_LEGGINGS), Input.of(PItems.TITAN_CORE), PItems.TITAN_LEGGINGS).offer(exporter);

        createSmithingRecipe(Input.of(PItems.REFINED_OBSIDIAN_AXE), Input.of(PItems.TITAN_CORE), PItems.TITAN_AXE).offer(exporter);
        createSmithingRecipe(Input.of(PItems.REFINED_OBSIDIAN_HOE), Input.of(PItems.TITAN_CORE), PItems.TITAN_HOE).offer(exporter);
        createSmithingRecipe(Input.of(PItems.REFINED_OBSIDIAN_PICKAXE), Input.of(PItems.TITAN_CORE), PItems.TITAN_PICKAXE).offer(exporter);
        createSmithingRecipe(Input.of(PItems.REFINED_OBSIDIAN_SHOVEL), Input.of(PItems.TITAN_CORE), PItems.TITAN_SHOVEL).offer(exporter);

        createSmithingRecipe(Input.of(PItems.REFINED_OBSIDIAN_BOW), Input.of(PItems.TITAN_CORE), PItems.TITAN_BOW).offer(exporter);
        createSmithingRecipe(Input.of(PItems.REFINED_OBSIDIAN_SWORD), Input.of(PItems.TITAN_CORE), PItems.TITAN_SWORD).offer(exporter);

        // Primal netherite
        createSmithingRecipe(Input.of(PItems.TITAN_BOOTS), Input.of(Items.NETHERITE_BOOTS), PItems.PRIMAL_NETHERITE_BOOTS).offer(exporter);
        createSmithingRecipe(Input.of(PItems.TITAN_CHESTPLATE), Input.of(Items.NETHERITE_CHESTPLATE), PItems.PRIMAL_NETHERITE_CHESTPLATE).offer(exporter);
        createSmithingRecipe(Input.of(PItems.TITAN_HELMET), Input.of(Items.NETHERITE_HELMET), PItems.PRIMAL_NETHERITE_HELMET).offer(exporter);
        createSmithingRecipe(Input.of(PItems.TITAN_LEGGINGS), Input.of(Items.NETHERITE_LEGGINGS), PItems.PRIMAL_NETHERITE_LEGGINGS).offer(exporter);

        createSmithingRecipe(Input.of(PItems.TITAN_AXE), Input.of(Items.NETHERITE_AXE), PItems.PRIMAL_NETHERITE_AXE).offer(exporter);
        createSmithingRecipe(Input.of(PItems.TITAN_HOE), Input.of(Items.NETHERITE_HOE), PItems.PRIMAL_NETHERITE_HOE).offer(exporter);
        createSmithingRecipe(Input.of(PItems.TITAN_PICKAXE), Input.of(Items.NETHERITE_PICKAXE), PItems.PRIMAL_NETHERITE_PICKAXE).offer(exporter);
        createSmithingRecipe(Input.of(PItems.TITAN_SHOVEL), Input.of(Items.NETHERITE_SHOVEL), PItems.PRIMAL_NETHERITE_SHOVEL).offer(exporter);

        createSmithingRecipe(Input.of(PItems.TITAN_BOW), Input.of(PItems.NETHERITE_HEART), PItems.PRIMAL_NETHERITE_BOW).offer(exporter);
        createSmithingRecipe(Input.of(PItems.TITAN_SWORD), Input.of(Items.NETHERITE_SWORD), PItems.PRIMAL_NETHERITE_SWORD).offer(exporter);


        // Verum
        createSmithingRecipe(Input.of(PItems.PRIMAL_NETHERITE_BOOTS), Input.of(PItems.VOID_SOUL_BOOTS), PItems.END_BOOTS).offer(exporter);
        createSmithingRecipe(Input.of(PItems.PRIMAL_NETHERITE_CHESTPLATE), Input.of(PItems.VOID_SOUL_CHESTPLATE), PItems.END_CHESTPLATE).offer(exporter);
        createSmithingRecipe(Input.of(PItems.PRIMAL_NETHERITE_HELMET), Input.of(PItems.VOID_SOUL_HELMET), PItems.END_HELMET).offer(exporter);
        createSmithingRecipe(Input.of(PItems.PRIMAL_NETHERITE_LEGGINGS), Input.of(PItems.VOID_SOUL_LEGGINGS), PItems.END_LEGGINGS).offer(exporter);

        createSmithingRecipe(Input.of(PItems.PRIMAL_NETHERITE_AXE), Input.of(PItems.VOID_SOUL_TOOL), PItems.END_AXE).offer(exporter);
        createSmithingRecipe(Input.of(PItems.PRIMAL_NETHERITE_HOE), Input.of(PItems.VOID_SOUL_TOOL), PItems.END_HOE).offer(exporter);
        createSmithingRecipe(Input.of(PItems.PRIMAL_NETHERITE_PICKAXE), Input.of(PItems.VOID_SOUL_TOOL), PItems.END_PICKAXE).offer(exporter);
        createSmithingRecipe(Input.of(PItems.PRIMAL_NETHERITE_SHOVEL), Input.of(PItems.VOID_SOUL_TOOL), PItems.END_SHOVEL).offer(exporter);

        createSmithingRecipe(Input.of(PItems.PRIMAL_NETHERITE_BOW), Input.of(PItems.VOID_SOUL_TOOL), PItems.END_BOW).offer(exporter);
        createSmithingRecipe(Input.of(PItems.PRIMAL_NETHERITE_SWORD), Input.of(PItems.VOID_SOUL_TOOL), PItems.END_SWORD).offer(exporter);

        // Upgrades
        List<Item> upgradeTargets = PItemTagProvider.tags.get(PItemTags.UPGRADABLE);
        var upgrades = Upgrades.data;
        upgrades.forEach((item, upgrade) -> {
            getUpgradeRecipes(upgrade).forEach(wrapper -> wrapper.offer(exporter));
        });
    }

    public static List<SmithingRecipeBuilderWrapper> getUpgradeRecipes(Upgrade upgrade) {
        var item = upgrade.item;
        List<SmithingRecipeBuilderWrapper> recipes = new ArrayList<>();
        List<Item> upgradeTargets = PItemTagProvider.tags.get(PItemTags.UPGRADABLE);
        upgradeTargets.forEach(target -> {
            var effects = upgrade.effects.apply(target);
            if (effects == null || effects.isEmpty()) return;

            String upgradeNbtName = UpgradeUtils.getUpgradeNbtName(item);
            String recipePath = UpgradeUtils.getRecipePath(item, target);
            var json = new JsonObject();
            json.add("id", new JsonPrimitive(ItemUtils.getId(item).toString()));
            recipes.add(createSmithingRecipe(Input.of(target), Input.of(item), target).addBaseNbt(upgradeNbtName, new JsonPrimitive(""), false).addResultNbt(upgradeNbtName, json).setPath(recipePath));
        });
        return recipes;
    }
}
