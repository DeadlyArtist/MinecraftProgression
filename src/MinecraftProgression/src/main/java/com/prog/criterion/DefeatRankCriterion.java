package com.prog.criterion;

import com.deadlyartist.jpa.util.JetpackUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.prog.Prog;
import com.prog.entity.PComponents;
import com.prog.utils.SquadUtils;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.OnKilledCriterion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.DamageSourcePredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class DefeatRankCriterion extends AbstractCriterion<DefeatRankCriterion.Conditions> {
    static final Identifier ID = new Identifier(Prog.MOD_ID, "defeat_rank");

    @Override
    protected Conditions conditionsFromJson(JsonObject json, EntityPredicate.Extended playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        var abovePlayer = false;
        if (json.has("above")) abovePlayer = json.get("above").getAsBoolean();
        Conditions conditions = new Conditions(playerPredicate, json.get("rank").getAsInt(), abovePlayer);
        return conditions;
    }

    @Override
    public Identifier getId() {
        return ID;
    }

    public void trigger(ServerPlayerEntity player, MobEntity mob) {
        LootContext lootContext = EntityPredicate.createAdvancementEntityLootContext(player, mob);
        this.trigger(player, conditions -> conditions.test(player, mob));
    }

    public static class Conditions extends AbstractCriterionConditions {
        public int rank;
        public boolean abovePlayer;

        public Conditions(EntityPredicate.Extended player, int rank, boolean abovePlayer) {
            super(DefeatRankCriterion.ID, player);
            this.rank = rank;
            this.abovePlayer = abovePlayer;
        }

        public static Conditions atLeastAbovePlayer(int rankDifference) {
            return new Conditions(EntityPredicate.Extended.EMPTY, rankDifference, true);
        }

        public static Conditions atLeast(int rank) {
            return new Conditions(EntityPredicate.Extended.EMPTY, rank, false);
        }

        @Override
        public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("rank", rank);
            jsonObject.addProperty("above", abovePlayer);
            return jsonObject;
        }

        boolean test(ServerPlayerEntity player, MobEntity mob) {
            var mobRank = PComponents.SQUAD.get(mob).rank;
            var playerRank = SquadUtils.getPlayerRank(player);
            if (abovePlayer) {
                if (mobRank - playerRank >= rank) return true;
            } else {
                if (mobRank >= rank) return true;
            }

            return false;
        }
    }
}