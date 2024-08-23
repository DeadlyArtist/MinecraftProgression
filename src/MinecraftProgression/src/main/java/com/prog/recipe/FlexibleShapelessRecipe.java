package com.prog.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.prog.itemOrBlock.data.FlexibleCraftingData;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class FlexibleShapelessRecipe implements FlexibleCraftingRecipe {
    final FlexibleCraftingData data;
    final int width;
    final int height;
    private final Identifier id;
    final String group;
    final ItemStack output;
    final DefaultedList<Ingredient> input;

    public FlexibleShapelessRecipe(FlexibleCraftingData data, Identifier id, String group, int width, int height, ItemStack output, DefaultedList<Ingredient> input) {
        this.data = data;
        this.id = id;
        this.group = group;
        this.width = width;
        this.height = height;
        this.output = output;
        this.input = input;
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return data.shapelessSerializer.get();
    }

    @Override
    public RecipeType<?> getType() {
        return data.recipeType;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public ItemStack getOutput() {
        return this.output;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return this.input;
    }

    public boolean matches(CraftingInventory craftingInventory, World world) {
        RecipeMatcher recipeMatcher = new RecipeMatcher();
        int i = 0;

        for (int j = 0; j < craftingInventory.size(); j++) {
            ItemStack itemStack = craftingInventory.getStack(j);
            if (!itemStack.isEmpty()) {
                i++;
                recipeMatcher.addInput(itemStack, 1);
            }
        }

        return i == this.input.size() && recipeMatcher.match(this, null);
    }

    public ItemStack craft(CraftingInventory craftingInventory) {
        return this.output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= this.input.size();
    }

    public static class Serializer implements RecipeSerializer<FlexibleShapelessRecipe> {
        public final FlexibleCraftingData data;

        public Serializer(FlexibleCraftingData data) {
            this.data = data;
        }

        public FlexibleShapelessRecipe read(Identifier identifier, JsonObject jsonObject) {
            String string = JsonHelper.getString(jsonObject, "group", "");
            DefaultedList<Ingredient> defaultedList = getIngredients(JsonHelper.getArray(jsonObject, "ingredients"));
            if (defaultedList.isEmpty()) {
                throw new JsonParseException("No ingredients for shapeless recipe");
            } else if (defaultedList.size() > data.height * data.width) {
                throw new JsonParseException("Too many ingredients for shapeless recipe");
            } else {
                ItemStack itemStack = ShapedRecipe.outputFromJson(JsonHelper.getObject(jsonObject, "result"));
                return new FlexibleShapelessRecipe(data, identifier, string, data.width, data.height, itemStack, defaultedList);
            }
        }

        private static DefaultedList<Ingredient> getIngredients(JsonArray json) {
            DefaultedList<Ingredient> defaultedList = DefaultedList.of();

            for (int i = 0; i < json.size(); i++) {
                Ingredient ingredient = Ingredient.fromJson(json.get(i));
                if (!ingredient.isEmpty()) {
                    defaultedList.add(ingredient);
                }
            }

            return defaultedList;
        }

        public FlexibleShapelessRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            String string = packetByteBuf.readString();
            int i = packetByteBuf.readVarInt();
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i, Ingredient.EMPTY);

            for (int j = 0; j < defaultedList.size(); j++) {
                defaultedList.set(j, Ingredient.fromPacket(packetByteBuf));
            }

            ItemStack itemStack = packetByteBuf.readItemStack();
            return new FlexibleShapelessRecipe(data, identifier, string, data.width, data.height, itemStack, defaultedList);
        }

        public void write(PacketByteBuf packetByteBuf, FlexibleShapelessRecipe shapelessRecipe) {
            packetByteBuf.writeString(shapelessRecipe.group);
            packetByteBuf.writeVarInt(shapelessRecipe.input.size());

            for (Ingredient ingredient : shapelessRecipe.input) {
                ingredient.write(packetByteBuf);
            }

            packetByteBuf.writeItemStack(shapelessRecipe.output);
        }
    }
}
