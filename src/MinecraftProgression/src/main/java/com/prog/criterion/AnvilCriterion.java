package com.prog.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class AnvilCriterion extends AbstractCriterion<AnvilCriterion.Conditions> {
    static final Identifier ID = new Identifier("anvil");

    @Override
    public Identifier getId() {
        return ID;
    }

    public Conditions conditionsFromJson(
            JsonObject jsonObject, EntityPredicate.Extended extended, AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer
    ) {
        ItemPredicate itemPredicate = ItemPredicate.fromJson(jsonObject.get("item"));
        NumberRange.IntRange intRange = NumberRange.IntRange.fromJson(jsonObject.get("levels"));
        return new Conditions(extended, itemPredicate, intRange);
    }

    public void trigger(ServerPlayerEntity player, ItemStack stack, int levels) {
        this.trigger(player, conditions -> conditions.matches(stack, levels));
    }

    public static class Conditions extends AbstractCriterionConditions {
        private final ItemPredicate item;
        private final NumberRange.IntRange levels;

        public Conditions(EntityPredicate.Extended player, ItemPredicate item, NumberRange.IntRange levels) {
            super(ID, player);
            this.item = item;
            this.levels = levels;
        }

        public static Conditions any() {
            return new Conditions(EntityPredicate.Extended.EMPTY, ItemPredicate.ANY, NumberRange.IntRange.ANY);
        }

        public static Conditions range(NumberRange.IntRange range) {
            return new Conditions(EntityPredicate.Extended.EMPTY, ItemPredicate.ANY, range);
        }

        public static Conditions exactly(int level) {
            return new Conditions(EntityPredicate.Extended.EMPTY, ItemPredicate.ANY, NumberRange.IntRange.exactly(level));
        }

        public static Conditions atLeast(int min) {
            return new Conditions(EntityPredicate.Extended.EMPTY, ItemPredicate.ANY, NumberRange.IntRange.atLeast(min));
        }

        public boolean matches(ItemStack stack, int levels) {
            return this.item.test(stack) && this.levels.test(levels);
        }

        @Override
        public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
            JsonObject jsonObject = super.toJson(predicateSerializer);
            jsonObject.add("item", this.item.toJson());
            jsonObject.add("levels", this.levels.toJson());
            return jsonObject;
        }
    }
}