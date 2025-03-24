package com.prog.data;

import com.deadlyartist.jpa.config.Jetpacks;
import com.google.common.collect.Maps;
import com.prog.criterion.AnvilCriterion;
import com.prog.criterion.DefeatRankCriterion;
import com.prog.itemOrBlock.PBlocks;
import com.prog.itemOrBlock.PItems;
import com.prog.text.PTexts;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.CriterionMerger;
import net.minecraft.advancement.criterion.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Consumer;

public class PAdvancementProvider extends FabricAdvancementProvider {
    public PAdvancementProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    public static Advancement fake(Identifier id) {
        return new Advancement(id, null, null, null, new LinkedHashMap<>(), null);
    }

    public static Advancement fake(String id) {
        return fake(new Identifier(id));
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> consumer) {
        // --- STEEL TIER ---
        Advancement steelIngot = registerItemAdvancement(consumer, PItems.STEEL_INGOT, "obtain_steel_ingot",
                PTexts.ADV_TIER_STEEL_TITLE.get(), PTexts.ADV_TIER_STEEL_DESC.get(), fake("story/smelt_iron"));

        Advancement smithingTable = registerItemAdvancement(consumer, Items.SMITHING_TABLE, "obtain_smithing_table",
                PTexts.ADV_MACHINE_SMITHING_TABLE_TITLE.get(), PTexts.ADV_MACHINE_SMITHING_TABLE_DESC.get(), steelIngot);

        Advancement steelArmor = registerItemAnyAdvancement(consumer, List.of(PItems.STEEL_HELMET, PItems.STEEL_CHESTPLATE, PItems.STEEL_LEGGINGS, PItems.STEEL_BOOTS), PItems.STEEL_CHESTPLATE, "obtain_steel_armor",
                PTexts.ADV_TIER_STEEL_ARMOR_TITLE.get(), PTexts.ADV_TIER_STEEL_ARMOR_DESC.get(), smithingTable);

        Advancement steelPickaxe = registerItemAdvancement(consumer, PItems.STEEL_PICKAXE, "obtain_steel_pickaxe",
                PTexts.ADV_TIER_STEEL_PICKAXE_TITLE.get(), PTexts.ADV_TIER_STEEL_PICKAXE_DESC.get(), smithingTable);

        // --- ULTIMATE DIAMOND TIER ---
        Advancement ultimateDiamondArmor = registerItemAnyAdvancement(consumer, List.of(PItems.ULTIMATE_DIAMOND_HELMET, PItems.ULTIMATE_DIAMOND_CHESTPLATE, PItems.ULTIMATE_DIAMOND_LEGGINGS, PItems.ULTIMATE_DIAMOND_BOOTS), PItems.ULTIMATE_DIAMOND_CHESTPLATE, "obtain_ultimate_diamond_armor",
                PTexts.ADV_TIER_ULTIMATE_DIAMOND_ARMOR_TITLE.get(), PTexts.ADV_TIER_ULTIMATE_DIAMOND_ARMOR_DESC.get(), fake("story/mine_diamond"));

        Advancement ultimateDiamondPickaxe = registerItemAdvancement(consumer, PItems.ULTIMATE_DIAMOND_PICKAXE, "obtain_ultimate_diamond_pickaxe",
                PTexts.ADV_TIER_ULTIMATE_DIAMOND_PICKAXE_TITLE.get(), PTexts.ADV_TIER_ULTIMATE_DIAMOND_PICKAXE_DESC.get(), fake("story/mine_diamond"));

        // --- REFINED OBSIDIAN TIER ---
        Advancement assembly = registerItemAdvancement(consumer, PBlocks.ASSEMBLY.asItem(), "obtain_assembly",
                PTexts.ADV_MACHINE_ASSEMBLY_TITLE.get(), PTexts.ADV_MACHINE_ASSEMBLY_DESC.get(), fake("story/form_obsidian"));

        Advancement incinerator = registerItemAdvancement(consumer, PBlocks.INCINERATOR.asItem(), "obtain_incinerator",
                PTexts.ADV_MACHINE_INCINERATOR_TITLE.get(), PTexts.ADV_MACHINE_INCINERATOR_DESC.get(), fake("nether/obtain_blaze_rod"));

        Advancement refinedObsidianIngot = registerItemAdvancement(consumer, PItems.REFINED_OBSIDIAN_INGOT, "obtain_refined_obsidian_ingot",
                PTexts.ADV_TIER_REFINED_OBSIDIAN_TITLE.get(), PTexts.ADV_TIER_REFINED_OBSIDIAN_DESC.get(), incinerator);

        Advancement refinedObsidianArmor = registerItemAnyAdvancement(consumer, List.of(PItems.REFINED_OBSIDIAN_HELMET, PItems.REFINED_OBSIDIAN_CHESTPLATE, PItems.REFINED_OBSIDIAN_LEGGINGS, PItems.REFINED_OBSIDIAN_BOOTS), PItems.REFINED_OBSIDIAN_CHESTPLATE, "obtain_refined_obsidian_armor",
                PTexts.ADV_TIER_REFINED_OBSIDIAN_ARMOR_TITLE.get(), PTexts.ADV_TIER_REFINED_OBSIDIAN_ARMOR_DESC.get(), refinedObsidianIngot);

        Advancement refinedObsidianPickaxe = registerItemAdvancement(consumer, PItems.REFINED_OBSIDIAN_PICKAXE, "obtain_refined_obsidian_pickaxe",
                PTexts.ADV_TIER_REFINED_OBSIDIAN_PICKAXE_TITLE.get(), PTexts.ADV_TIER_REFINED_OBSIDIAN_PICKAXE_DESC.get(), refinedObsidianIngot);

        // --- TITAN TIER ---
        Advancement titanIngot = registerItemAdvancement(consumer, PItems.TITAN_INGOT, "obtain_titan_ingot",
                PTexts.ADV_TIER_TITAN_TITLE.get(), PTexts.ADV_TIER_TITAN_DESC.get(), refinedObsidianPickaxe);

        Advancement titanArmor = registerItemAnyAdvancement(consumer, List.of(PItems.TITAN_HELMET, PItems.TITAN_CHESTPLATE, PItems.TITAN_LEGGINGS, PItems.TITAN_BOOTS), PItems.TITAN_CHESTPLATE, "obtain_titan_armor",
                PTexts.ADV_TIER_TITAN_ARMOR_TITLE.get(), PTexts.ADV_TIER_TITAN_ARMOR_DESC.get(), titanIngot);

        Advancement titanPickaxe = registerItemAdvancement(consumer, PItems.TITAN_PICKAXE, "obtain_titan_pickaxe",
                PTexts.ADV_TIER_TITAN_PICKAXE_TITLE.get(), PTexts.ADV_TIER_TITAN_PICKAXE_DESC.get(), titanIngot);

        // --- PRIMAL NETHERITE TIER ---
        Advancement primalNetheriteArmor = registerItemAnyAdvancement(consumer, List.of(PItems.PRIMAL_NETHERITE_HELMET, PItems.PRIMAL_NETHERITE_CHESTPLATE, PItems.PRIMAL_NETHERITE_LEGGINGS, PItems.PRIMAL_NETHERITE_BOOTS), PItems.PRIMAL_NETHERITE_CHESTPLATE, "obtain_primal_netherite_armor",
                PTexts.ADV_TIER_PRIMAL_NETHERITE_ARMOR_TITLE.get(), PTexts.ADV_TIER_PRIMAL_NETHERITE_ARMOR_DESC.get(), fake("nether/obtain_ancient_debris"));

        Advancement primalNetheritePickaxe = registerItemAdvancement(consumer, PItems.PRIMAL_NETHERITE_PICKAXE, "obtain_primal_netherite_pickaxe",
                PTexts.ADV_TIER_PRIMAL_NETHERITE_PICKAXE_TITLE.get(), PTexts.ADV_TIER_PRIMAL_NETHERITE_PICKAXE_DESC.get(), fake("nether/obtain_ancient_debris"));

        // --- EVERGLOOM TIER ---
        Advancement evergloomIngot = registerItemAdvancement(consumer, PItems.PURE_EVERGLOOM_INGOT, "obtain_evergloom_ingot",
                PTexts.ADV_TIER_EVERGLOOM_TITLE.get(), PTexts.ADV_TIER_EVERGLOOM_DESC.get(), primalNetheritePickaxe);

        Advancement evergloomArmor = registerItemAnyAdvancement(consumer, List.of(PItems.EVERGLOOM_HELMET, PItems.EVERGLOOM_CHESTPLATE, PItems.EVERGLOOM_LEGGINGS, PItems.EVERGLOOM_BOOTS), PItems.EVERGLOOM_CHESTPLATE, "obtain_evergloom_armor",
                PTexts.ADV_TIER_EVERGLOOM_ARMOR_TITLE.get(), PTexts.ADV_TIER_EVERGLOOM_ARMOR_DESC.get(), evergloomIngot);

        Advancement evergloomPickaxe = registerItemAdvancement(consumer, PItems.EVERGLOOM_PICKAXE, "obtain_evergloom_pickaxe",
                PTexts.ADV_TIER_EVERGLOOM_PICKAXE_TITLE.get(), PTexts.ADV_TIER_EVERGLOOM_PICKAXE_DESC.get(), evergloomIngot);

        // --- END TIER ---
        Advancement cosmicConstructor = registerItemAdvancement(consumer, PBlocks.COSMIC_CONSTRUCTOR.asItem(), "obtain_cosmic_constructor",
                PTexts.ADV_MACHINE_COSMIC_CONSTRUCTOR_TITLE.get(), PTexts.ADV_MACHINE_COSMIC_CONSTRUCTOR_DESC.get(), fake("end/root"));

        Advancement cosmicIncubator = registerItemAdvancement(consumer, PBlocks.COSMIC_INCUBATOR.asItem(), "obtain_cosmic_incubator",
                PTexts.ADV_MACHINE_COSMIC_INCUBATOR_TITLE.get(), PTexts.ADV_MACHINE_COSMIC_INCUBATOR_DESC.get(), fake("end/dragon_egg"));

        Advancement verumIngot = registerItemAdvancement(consumer, PItems.VERUM_INGOT, "obtain_verum_ingot",
                PTexts.ADV_TIER_END_TITLE.get(), PTexts.ADV_TIER_END_DESC.get(), cosmicIncubator);

        Advancement endArmor = registerItemAnyAdvancement(consumer, List.of(PItems.END_HELMET, PItems.END_CHESTPLATE, PItems.END_LEGGINGS, PItems.END_BOOTS), PItems.END_CHESTPLATE, "obtain_end_armor",
                PTexts.ADV_TIER_END_ARMOR_TITLE.get(), PTexts.ADV_TIER_END_ARMOR_DESC.get(), verumIngot);

        Advancement endPickaxe = registerItemAdvancement(consumer, PItems.END_PICKAXE, "obtain_end_pickaxe",
                PTexts.ADV_TIER_END_PICKAXE_TITLE.get(), PTexts.ADV_TIER_END_PICKAXE_DESC.get(), verumIngot);

        // --- SPECIAL TIER ITEMS ---
        Advancement amethystShears = registerItemAdvancement(consumer, PItems.AMETHYST_SHEARS, "obtain_amethyst_shears",
                PTexts.ADV_TOOL_AMETHYST_SHEARS_TITLE.get(), PTexts.ADV_TOOL_AMETHYST_SHEARS_DESC.get(), fake("adventure/root"));

        Advancement stellarTrident = registerItemAdvancement(consumer, PItems.STELLAR_TRIDENT, "obtain_stellar_trident",
                PTexts.ADV_TOOL_STELLAR_TRIDENT_TITLE.get(), PTexts.ADV_TOOL_STELLAR_TRIDENT_DESC.get(), fake("adventure/throw_trident"));

        Advancement flintAndAmethyst = registerItemAdvancement(consumer, PItems.FLINT_AND_AMETHYST, "obtain_flint_and_amethyst",
                PTexts.ADV_TOOL_FLINT_AND_AMETHYST_TITLE.get(), PTexts.ADV_TOOL_FLINT_AND_AMETHYST_DESC.get(), fake("story/enter_the_nether"));

        Advancement endShears = registerItemAdvancement(consumer, PItems.END_SHEARS, "obtain_end_shears",
                PTexts.ADV_TOOL_END_SHEARS_TITLE.get(), PTexts.ADV_TOOL_END_SHEARS_DESC.get(), amethystShears);

        // --- ENEMY RANKING ITEMS ---
        Advancement rank1 = registerAdvancement(consumer, "defeat_rank_1",
                PTexts.ADV_RANK_1_TITLE.get(), PTexts.ADV_RANK_1_DESC.get(), PItems.STEEL_SWORD, fake("adventure/kill_a_mob"), AdvancementFrame.GOAL, DefeatRankCriterion.Conditions.atLeast(1));

        Advancement rank5 = registerAdvancement(consumer, "defeat_rank_5",
                PTexts.ADV_RANK_5_TITLE.get(), PTexts.ADV_RANK_5_DESC.get(), PItems.REFINED_OBSIDIAN_SWORD, rank1, AdvancementFrame.GOAL, DefeatRankCriterion.Conditions.atLeast(5));

        Advancement rank10 = registerAdvancement(consumer, "defeat_rank_10",
                PTexts.ADV_RANK_10_TITLE.get(), PTexts.ADV_RANK_10_DESC.get(), PItems.END_SWORD, rank5, AdvancementFrame.GOAL, DefeatRankCriterion.Conditions.atLeast(10));

        Advancement higherRank = registerHiddenAdvancement(consumer, "defeat_higher_rank",
                PTexts.ADV_HIGHER_RANK_TITLE.get(), PTexts.ADV_HIGHER_RANK_DESC.get(), PItems.ULTIMATE_DIAMOND_SWORD, fake("adventure/kill_a_mob"), AdvancementFrame.CHALLENGE, DefeatRankCriterion.Conditions.atLeastAbovePlayer(2));

        Advancement muchHigherRank = registerHiddenAdvancement(consumer, "defeat_much_higher_rank",
                PTexts.ADV_MUCH_HIGHER_RANK_TITLE.get(), PTexts.ADV_MUCH_HIGHER_RANK_DESC.get(), PItems.PRIMAL_NETHERITE_SWORD, higherRank, AdvancementFrame.CHALLENGE, DefeatRankCriterion.Conditions.atLeastAbovePlayer(4));


        // --- EAT FOOD ---
        Advancement goldenApple = registerConsumableAdvancement(consumer, Items.GOLDEN_APPLE, "eat_golden_apple",
                PTexts.ADV_EAT_GOLDEN_APPLE_TITLE.get(), PTexts.ADV_EAT_GOLDEN_APPLE_DESC.get(), fake("husbandry/root"), AdvancementFrame.GOAL);

        Advancement enchantedGoldenApple = registerConsumableAdvancement(consumer, Items.ENCHANTED_GOLDEN_APPLE, "eat_enchanted_golden_apple",
                PTexts.ADV_EAT_ENCHANTED_GOLDEN_APPLE_TITLE.get(), PTexts.ADV_EAT_ENCHANTED_GOLDEN_APPLE_DESC.get(), goldenApple, AdvancementFrame.CHALLENGE);

        Advancement starApple = registerConsumableAdvancement(consumer, PItems.STAR_APPLE, "eat_star_apple",
                PTexts.ADV_EAT_STAR_APPLE_TITLE.get(), PTexts.ADV_EAT_STAR_APPLE_DESC.get(), enchantedGoldenApple, AdvancementFrame.GOAL);

        Advancement enchantedStarApple = registerConsumableAdvancement(consumer, PItems.ENCHANTED_STAR_APPLE, "eat_enchanted_star_apple",
                PTexts.ADV_EAT_ENCHANTED_STAR_APPLE_TITLE.get(), PTexts.ADV_EAT_ENCHANTED_STAR_APPLE_DESC.get(), starApple, AdvancementFrame.CHALLENGE);

        Advancement cosmicSoup = registerConsumableAdvancement(consumer, PItems.COSMIC_SOUP, "eat_cosmic_soup",
                PTexts.ADV_EAT_COSMIC_SOUP_TITLE.get(), PTexts.ADV_EAT_COSMIC_SOUP_DESC.get(), fake("husbandry/root"), AdvancementFrame.GOAL);

        Advancement netherWart = registerConsumableAdvancement(consumer, Items.NETHER_WART, "eat_nether_wart",
                PTexts.ADV_EAT_NETHER_WART_TITLE.get(), PTexts.ADV_EAT_NETHER_WART_DESC.get(), fake("husbandry/root"), AdvancementFrame.GOAL);

        Advancement glisteringMelon = registerConsumableAdvancement(consumer, Items.GLISTERING_MELON_SLICE, "eat_glistering_melon",
                PTexts.ADV_EAT_GLISTERING_MELON_TITLE.get(), PTexts.ADV_EAT_GLISTERING_MELON_DESC.get(), fake("husbandry/root"), AdvancementFrame.GOAL);

        Advancement goldenCarrot = registerConsumableAdvancement(consumer, Items.GOLDEN_CARROT, "eat_golden_carrot",
                PTexts.ADV_EAT_GOLDEN_CARROT_TITLE.get(), PTexts.ADV_EAT_GOLDEN_CARROT_DESC.get(), fake("husbandry/root"), AdvancementFrame.GOAL);

        Advancement slime = registerConsumableAdvancement(consumer, Items.SLIME_BALL, "eat_slime",
                PTexts.ADV_EAT_SLIME_TITLE.get(), PTexts.ADV_EAT_SLIME_DESC.get(), fake("husbandry/root"), AdvancementFrame.GOAL);

        Advancement rottenFlesh = registerConsumableAdvancement(consumer, Items.ROTTEN_FLESH, "eat_rotten_flesh",
                PTexts.ADV_EAT_ROTTEN_FLESH_TITLE.get(), PTexts.ADV_EAT_ROTTEN_FLESH_DESC.get(), fake("husbandry/root"), AdvancementFrame.GOAL);

        Advancement pufferfish = registerConsumableAdvancement(consumer, Items.PUFFERFISH, "eat_pufferfish",
                PTexts.ADV_EAT_PUFFERFISH_TITLE.get(), PTexts.ADV_EAT_PUFFERFISH_DESC.get(), fake("husbandry/root"), AdvancementFrame.GOAL);

        Advancement turtleEgg = registerConsumableAdvancement(consumer, Items.TURTLE_EGG, "eat_turtle_egg",
                PTexts.ADV_EAT_TURTLE_EGG_TITLE.get(), PTexts.ADV_EAT_TURTLE_EGG_DESC.get(), fake("husbandry/root"), AdvancementFrame.GOAL);

        // --- FISHING ---
        Advancement lavaFish = registerFishAnyAdvancement(consumer, "catch_lava_fish",
                PTexts.ADV_FISH_LAVA_TITLE.get(), PTexts.ADV_FISH_LAVA_DESC.get(), List.of(PItems.SUNFISH, PItems.RAINBOWFISH, PItems.BLACK_BASS, PItems.GOLDEN_LOBSTER), PItems.REFINED_OBSIDIAN_FISHING_ROD, fake("husbandry/fishy_business"), AdvancementFrame.TASK);

        Advancement voidFish = registerFishAnyAdvancement(consumer, "catch_void_fish",
                PTexts.ADV_FISH_VOID_TITLE.get(), PTexts.ADV_FISH_VOID_DESC.get(), List.of(PItems.FLYING_FISH, PItems.STELLAR_JELLY, PItems.VACUUM_FISH, PItems.SPACE_EEL), PItems.END_FISHING_ROD, lavaFish, AdvancementFrame.TASK);

        Advancement goldenLobster = registerFishAdvancement(consumer, "obtain_golden_lobster",
                PTexts.ADV_FISH_GOLDEN_LOBSTER_TITLE.get(), PTexts.ADV_FISH_GOLDEN_LOBSTER_DESC.get(), PItems.GOLDEN_LOBSTER, lavaFish, AdvancementFrame.CHALLENGE);

        // --- SPECIAL ITEMS ---
        Advancement jetpack = registerItemAdvancement(consumer, Jetpacks.DEFAULT.item.get(), "obtain_jetpack",
                PTexts.ADV_ITEM_JETPACK_TITLE.get(), PTexts.ADV_ITEM_JETPACK_DESC.get(), refinedObsidianIngot);

        Advancement burnedCd = registerItemAdvancement(consumer, PItems.BURNED_CD, "obtain_burned_cd",
                PTexts.ADV_ITEM_BURNED_CD_TITLE.get(), PTexts.ADV_ITEM_BURNED_CD_DESC.get(), lavaFish, AdvancementFrame.GOAL);

        Advancement ruby = registerItemAdvancement(consumer, PItems.RUBY, "obtain_ruby",
                PTexts.ADV_ITEM_RUBY_TITLE.get(), PTexts.ADV_ITEM_RUBY_DESC.get(), lavaFish, AdvancementFrame.GOAL);

        Advancement heartOfGreed = registerItemAdvancement(consumer, PItems.HEART_OF_GREED, "obtain_heart_of_greed",
                PTexts.ADV_ITEM_HEART_OF_GREED_TITLE.get(), PTexts.ADV_ITEM_HEART_OF_GREED_DESC.get(), fake("adventure/root"), AdvancementFrame.GOAL);

        Advancement silentHeart = registerItemAdvancement(consumer, PItems.SILENT_HEART, "obtain_silent_heart",
                PTexts.ADV_ITEM_SILENT_HEART_TITLE.get(), PTexts.ADV_ITEM_SILENT_HEART_DESC.get(), fake("adventure/kill_mob_near_sculk_catalyst"), AdvancementFrame.GOAL);

        Advancement angelRing = registerItemAdvancement(consumer, PItems.ANGEL_RING, "obtain_angel_ring",
                PTexts.ADV_ITEM_ANGEL_RING_TITLE.get(), PTexts.ADV_ITEM_ANGEL_RING_DESC.get(), fake("adventure/root"), AdvancementFrame.GOAL);

        Advancement star = registerItemAdvancement(consumer, PItems.STAR, "obtain_star",
                PTexts.ADV_ITEM_STAR_TITLE.get(), PTexts.ADV_ITEM_STAR_DESC.get(), voidFish, AdvancementFrame.CHALLENGE);

        Advancement mechanicalBoots = registerItemAdvancement(consumer, PItems.MECHANICAL_BOOTS, "obtain_mechanical_boots",
                PTexts.ADV_ITEM_MECHANICAL_BOOTS_TITLE.get(), PTexts.ADV_ITEM_MECHANICAL_BOOTS_DESC.get(), fake("adventure/root"), AdvancementFrame.GOAL);

        Advancement oceansGrace = registerItemAdvancement(consumer, PItems.OCEANS_GRACE, "obtain_oceans_grace",
                PTexts.ADV_ITEM_OCEANS_GRACE_TITLE.get(), PTexts.ADV_ITEM_OCEANS_GRACE_DESC.get(), fake("adventure/root"), AdvancementFrame.GOAL);

        Advancement dreamCatcher = registerItemAdvancement(consumer, PItems.DREAM_CATCHER, "obtain_dream_catcher",
                PTexts.ADV_ITEM_DREAM_CATCHER_TITLE.get(), PTexts.ADV_ITEM_DREAM_CATCHER_DESC.get(), fake("adventure/root"), AdvancementFrame.GOAL);

        Advancement anchor = registerItemAdvancement(consumer, PItems.ANCHOR, "obtain_anchor",
                PTexts.ADV_ITEM_ANCHOR_TITLE.get(), PTexts.ADV_ITEM_ANCHOR_DESC.get(), fake("adventure/root"));

        // --- ENCHANTING ---
        Advancement anvil = registerItemAdvancement(consumer, Items.ANVIL, "obtain_anvil",
                PTexts.ADV_MACHINE_ANVIL_TITLE.get(), PTexts.ADV_MACHINE_ANVIL_DESC.get(), fake("story/root"));

        Advancement enchantX = registerAdvancement(consumer, "enchant_x",
                PTexts.ADV_ENCHANTING_X_TITLE.get(), PTexts.ADV_ENCHANTING_X_DESC.get(), Items.ANVIL, anvil, AdvancementFrame.GOAL, AnvilCriterion.Conditions.atLeast(10));
    }

    private Advancement registerConsumableAdvancement(Consumer<Advancement> consumer, Item item, String id, Text title, Text desc, Advancement parent, AdvancementFrame frame) {
        var advancement = Advancement.Builder.create()
                .parent(parent)
                .display(item, title, desc, null, frame, true, true, false)
                .criterion(id, ConsumeItemCriterion.Conditions.item(item))
                .build(consumer, "prog/" + id);
        return advancement;
    }

    private Advancement registerAdvancement(Consumer<Advancement> consumer, String id, Text title, Text desc, Item icon, Advancement parent, AdvancementFrame frame, CriterionConditions conditions) {
        var advancement = Advancement.Builder.create()
                .parent(parent)
                .display(icon, title, desc, null, frame, true, true, false)
                .criterion(id, conditions)
                .build(consumer, "prog/" + id);
        return advancement;
    }

    private Advancement registerHiddenAdvancement(Consumer<Advancement> consumer, String id, Text title, Text desc, Item icon, Advancement parent, AdvancementFrame frame, CriterionConditions conditions) {
        var advancement = Advancement.Builder.create()
                .parent(parent)
                .display(icon, title, desc, null, frame, true, true, true)
                .criterion(id, conditions)
                .build(consumer, "prog/" + id);
        return advancement;
    }

    private Advancement registerItemAdvancement(Consumer<Advancement> consumer, Item item, String id, Text title, Text desc, Advancement parent, AdvancementFrame frame) {
        Advancement.Builder builder = Advancement.Builder.create()
                .parent(parent)
                .display(item, title, desc, null, frame, true, true, false)
                .criterion(id, InventoryChangedCriterion.Conditions.items(item));

        Advancement advancement = builder.build(consumer, "prog/" + id);
        return advancement;
    }

    private Advancement registerItemAdvancement(Consumer<Advancement> consumer, Item item, String id, Text title, Text desc, Advancement parent) {
        return registerItemAdvancement(consumer, item, id, title, desc, parent, AdvancementFrame.TASK);
    }

    private Advancement registerItemAnyAdvancement(Consumer<Advancement> consumer, List<Item> items, Item icon, String id, Text title, Text desc, Advancement parent, AdvancementFrame frame) {
        Advancement.Builder builder = Advancement.Builder.create()
                .parent(parent)
                .display(icon, title, desc, null, frame, true, true, false)
                .criteriaMerger(CriterionMerger.OR);

        for (var item : items) {
            builder.criterion(
                    Registry.ITEM.getId(item).getPath(),
                    InventoryChangedCriterion.Conditions.items(item)
            );
        }

        Advancement advancement = builder.build(consumer, "prog/" + id);
        return advancement;
    }

    private Advancement registerItemAnyAdvancement(Consumer<Advancement> consumer, List<Item> items, Item icon, String id, Text title, Text desc, Advancement parent) {
        return registerItemAnyAdvancement(consumer, items, icon, id, title, desc, parent, AdvancementFrame.TASK);
    }

    private Advancement registerFishAdvancement(Consumer<Advancement> consumer, String id, Text title, Text desc, Item item, Advancement parent, AdvancementFrame frame) {
        Advancement.Builder builder = Advancement.Builder.create()
                .parent(parent)
                .display(item, title, desc, null, frame, true, true, false)
                .criterion(id, FishingRodHookedCriterion.Conditions.create(ItemPredicate.ANY, EntityPredicate.ANY, ItemPredicate.Builder.create().items(item).build()));

        Advancement advancement = builder.build(consumer, "prog/" + id);
        return advancement;
    }

    private Advancement registerFishAnyAdvancement(Consumer<Advancement> consumer, String id, Text title, Text desc, List<Item> items, Item icon, Advancement parent, AdvancementFrame frame) {
        Advancement.Builder builder = Advancement.Builder.create()
                .parent(parent)
                .display(icon, title, desc, null, frame, true, true, false)
                .criteriaMerger(CriterionMerger.OR);
        for (var item : items) {
            builder.criterion(
                    Registry.ITEM.getId(item).getPath(),
                    FishingRodHookedCriterion.Conditions.create(ItemPredicate.ANY, EntityPredicate.ANY, ItemPredicate.Builder.create().items(item).build())
            );
        }

        Advancement advancement = builder.build(consumer, "prog/" + id);
        return advancement;
    }
}
