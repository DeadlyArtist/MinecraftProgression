package com.prog.text;

import com.prog.Prog;
import com.prog.utils.LOGGER;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;

public class PTexts {
    public static class TextWrapper {
        public final String id;
        public TextWrapper(String id){
            this.id = id;
        }

        public Text get(){
            return Text.translatable(id);
        }
    }

    public static class TextData {
        public String text;

        public TextData(String text) {
            this.text = text;
        }
    }

    public static final Map<TextWrapper, TextData> data = new HashMap<>();


    public static final TextWrapper DISABLED_TOOLTIP = registerText("DISABLED_TOOLTIP", "Disabled");

    public static final TextWrapper FIREPROOF_TOOLTIP = registerText("FIREPROOF_TOOLTIP", "Fireproof");
    public static final TextWrapper SOULBOUND_TOOLTIP = registerText("SOULBOUND_TOOLTIP", "Soulbound");

    public static final TextWrapper UPGRADEABLE_UPGRADE_TOOLTIP = registerText("UPGRADEABLE_UPGRADE_TOOLTIP", "Upgrades");
    public static final TextWrapper UPGRADE_TOOLTIP = registerText("UPGRADE_TOOLTIP", "Upgrade");
    public static final TextWrapper TIER_CORE_TOOLTIP = registerText("TIER_CORE_TOOLTIP", "Tier Core");
    public static final TextWrapper UPGRADABLE_TOOLTIP = registerText("UPGRADABLE_TOOLTIP", "Upgradable");
    public static final TextWrapper GOURMET_NOT_EATEN_TOOLTIP = registerText("GOURMET_NOT_EATEN_TOOLTIP", "Must... eat...");
    public static final TextWrapper GOURMET_HAS_EATEN_TOOLTIP = registerText("GOURMET_HAS_EATEN_TOOLTIP", "Already tasted");
    public static final TextWrapper WHEN_HELD_TOOLTIP = registerText("WHEN_HELD_TOOLTIP", "When Held");
    public static final TextWrapper THROWN_TOOLTIP = registerText("THROWN_TOOLTIP", "Thrown");
    public static final TextWrapper ASSEMBLY_UI_TITLE = registerText("ASSEMBLY_UI_TITLE", "Assembly");
    public static final TextWrapper COSMIC_CONSTRUCTOR_UI_TITLE = registerText("COSMIC_CONSTRUCTOR_UI_TITLE", "Cosmic Constructor");
    public static final TextWrapper INCINERATOR_UI_TITLE = registerText("INCINERATOR_UI_TITLE", "Incinerator");
    public static final TextWrapper COSMIC_INCUBATOR_UI_TITLE = registerText("COSMIC_INCUBATOR_UI_TITLE", "Cosmic Incubator");

    public static final TextWrapper KEYBIND_TOGGLED_ON_TOOLTIP = registerText("KEYBIND_TOGGLED_ON_TOOLTIP", "Toggled %s On");
    public static final TextWrapper KEYBIND_TOGGLED_OFF_TOOLTIP = registerText("KEYBIND_TOGGLED_OFF_TOOLTIP", "Toggled %s Off");

    // Ranks
    public static final TextWrapper GENERAL_RANK = registerText("GENERAL_RANK", "General");
    public static final TextWrapper COMMANDER_RANK = registerText("COMMANDER_RANK", "Commander");
    public static final TextWrapper WARLORD_RANK = registerText("WARLORD_RANK", "Warlord");
    public static final TextWrapper MONARCH_RANK = registerText("MONARCH_RANK", "Monarch");
    public static final TextWrapper OVERLORD_RANK = registerText("OVERLORD_RANK", "Overlord");
    public static final TextWrapper EMPEROR_RANK = registerText("EMPEROR_RANK", "Emperor");
    public static final TextWrapper GOD_RANK = registerText("GOD_RANK", "God");
    public static final TextWrapper TITAN_RANK = registerText("TITAN_RANK", "Titan");
    public static final TextWrapper PRIMORDIAL_RANK = registerText("PRIMORDIAL_RANK", "Primordial");

    public static Map<Integer, TextWrapper> nameByRank = new HashMap<>();
    static {
        nameByRank.put(1, GENERAL_RANK);
        nameByRank.put(2, COMMANDER_RANK);
        nameByRank.put(3, WARLORD_RANK);
        nameByRank.put(4, MONARCH_RANK);
        nameByRank.put(5, OVERLORD_RANK);
        nameByRank.put(6, EMPEROR_RANK);
        nameByRank.put(7, GOD_RANK);
        nameByRank.put(8, TITAN_RANK);
        nameByRank.put(9, PRIMORDIAL_RANK);
    }

    // ---- MACHINE ADVANCEMENTS ----
    public static final TextWrapper ADV_MACHINE_SMITHING_TABLE_TITLE = registerAdvancementTitle("obtain_smithing_table", "Smithing Apprentice");
    public static final TextWrapper ADV_MACHINE_SMITHING_TABLE_DESC = registerAdvancementDescription("obtain_smithing_table", "Build a smithing table.");

    public static final TextWrapper ADV_MACHINE_ANVIL_TITLE = registerAdvancementTitle("obtain_anvil", "Lots Of Iron");
    public static final TextWrapper ADV_MACHINE_ANVIL_DESC = registerAdvancementDescription("obtain_anvil", "Craft an anvil to make use of looted books.");

    public static final TextWrapper ADV_MACHINE_ASSEMBLY_TITLE = registerAdvancementTitle("obtain_assembly", "Engineering Marvel");
    public static final TextWrapper ADV_MACHINE_ASSEMBLY_DESC = registerAdvancementDescription("obtain_assembly", "A high tech version of the crafting table.");

    public static final TextWrapper ADV_MACHINE_INCINERATOR_TITLE = registerAdvancementTitle("obtain_incinerator", "Hellish Flames");
    public static final TextWrapper ADV_MACHINE_INCINERATOR_DESC = registerAdvancementDescription("obtain_incinerator", "Use the flames of hell to craft an incinerator.");

    public static final TextWrapper ADV_MACHINE_COSMIC_CONSTRUCTOR_TITLE = registerAdvancementTitle("obtain_cosmic_constructor", "Galactic Engineering");
    public static final TextWrapper ADV_MACHINE_COSMIC_CONSTRUCTOR_DESC = registerAdvancementDescription("obtain_cosmic_constructor", "Bend the laws of space to craft beyond logic.");

    public static final TextWrapper ADV_MACHINE_COSMIC_INCUBATOR_TITLE = registerAdvancementTitle("obtain_cosmic_incubator", "Not For Hatching");
    public static final TextWrapper ADV_MACHINE_COSMIC_INCUBATOR_DESC = registerAdvancementDescription("obtain_cosmic_incubator", "But, can it?");

    // ---- MATERIAL TIER ADVANCEMENTS ----
    public static final TextWrapper ADV_TIER_STEEL_TITLE = registerAdvancementTitle("obtain_steel_ingot", "Industrial Strength");
    public static final TextWrapper ADV_TIER_STEEL_DESC = registerAdvancementDescription("obtain_steel_ingot", "Obtain a steel ingot, a material stronger than iron.");

    public static final TextWrapper ADV_TIER_STEEL_ARMOR_TITLE = registerAdvancementTitle("obtain_steel_armor", "Forged for Battle");
    public static final TextWrapper ADV_TIER_STEEL_ARMOR_DESC = registerAdvancementDescription("obtain_steel_armor", "Reinforce your protection with steel.");

    public static final TextWrapper ADV_TIER_STEEL_PICKAXE_TITLE = registerAdvancementTitle("obtain_steel_pickaxe", "Built For Eternity");
    public static final TextWrapper ADV_TIER_STEEL_PICKAXE_DESC = registerAdvancementDescription("obtain_steel_pickaxe", "Upgrade your pickaxe with durable steel.");

    public static final TextWrapper ADV_TIER_ULTIMATE_DIAMOND_ARMOR_TITLE = registerAdvancementTitle("obtain_ultimate_diamond_armor", "Diamond But Better");
    public static final TextWrapper ADV_TIER_ULTIMATE_DIAMOND_ARMOR_DESC = registerAdvancementDescription("obtain_ultimate_diamond_armor", "Merge your armor with diamond armor.");

    public static final TextWrapper ADV_TIER_ULTIMATE_DIAMOND_PICKAXE_TITLE = registerAdvancementTitle("obtain_ultimate_diamond_pickaxe", "True Beauty");
    public static final TextWrapper ADV_TIER_ULTIMATE_DIAMOND_PICKAXE_DESC = registerAdvancementDescription("obtain_ultimate_diamond_pickaxe", "Upgrade your pickaxe with the beauty of a diamond.");

    public static final TextWrapper ADV_TIER_REFINED_OBSIDIAN_TITLE = registerAdvancementTitle("obtain_refined_obsidian_ingot", "Obsidian Reinvented");
    public static final TextWrapper ADV_TIER_REFINED_OBSIDIAN_DESC = registerAdvancementDescription("obtain_refined_obsidian_ingot", "Smelt obsidian with flames from hell.");

    public static final TextWrapper ADV_TIER_REFINED_OBSIDIAN_ARMOR_TITLE = registerAdvancementTitle("obtain_refined_obsidian_armor", "True Resilience");
    public static final TextWrapper ADV_TIER_REFINED_OBSIDIAN_ARMOR_DESC = registerAdvancementDescription("obtain_refined_obsidian_armor", "Reinforce your armor with the resilience of obsidian.");

    public static final TextWrapper ADV_TIER_REFINED_OBSIDIAN_PICKAXE_TITLE = registerAdvancementTitle("obtain_refined_obsidian_pickaxe", "Flame Breaker");
    public static final TextWrapper ADV_TIER_REFINED_OBSIDIAN_PICKAXE_DESC = registerAdvancementDescription("obtain_refined_obsidian_pickaxe", "Upgrade your pickaxe with the resilience of obsidian.");

    public static final TextWrapper ADV_TIER_TITAN_TITLE = registerAdvancementTitle("obtain_titan_ingot", "Forged from Hell");
    public static final TextWrapper ADV_TIER_TITAN_DESC = registerAdvancementDescription("obtain_titan_ingot", "Plunder the resources of hell to craft a titan ingot.");

    public static final TextWrapper ADV_TIER_TITAN_ARMOR_TITLE = registerAdvancementTitle("obtain_titan_armor", "Legendary Titan");
    public static final TextWrapper ADV_TIER_TITAN_ARMOR_DESC = registerAdvancementDescription("obtain_titan_armor", "An armor for rulers.");

    public static final TextWrapper ADV_TIER_TITAN_PICKAXE_TITLE = registerAdvancementTitle("obtain_titan_pickaxe", "Breaker of Worlds");
    public static final TextWrapper ADV_TIER_TITAN_PICKAXE_DESC = registerAdvancementDescription("obtain_titan_pickaxe", "Upgrade your pickaxe with the strength of titan.");

    public static final TextWrapper ADV_TIER_PRIMAL_NETHERITE_ARMOR_TITLE = registerAdvancementTitle("obtain_primal_netherite_armor", "Primal Origins");
    public static final TextWrapper ADV_TIER_PRIMAL_NETHERITE_ARMOR_DESC = registerAdvancementDescription("obtain_primal_netherite_armor", "Merge your armor with primal energies.");

    public static final TextWrapper ADV_TIER_PRIMAL_NETHERITE_PICKAXE_TITLE = registerAdvancementTitle("obtain_primal_netherite_pickaxe", "Primal Destruction");
    public static final TextWrapper ADV_TIER_PRIMAL_NETHERITE_PICKAXE_DESC = registerAdvancementDescription("obtain_primal_netherite_pickaxe", "Merge your pickaxe with primal energies.");

    public static final TextWrapper ADV_TIER_EVERGLOOM_TITLE = registerAdvancementTitle("obtain_pure_evergloom_ingot", "Dark Whispers");
    public static final TextWrapper ADV_TIER_EVERGLOOM_DESC = registerAdvancementDescription("obtain_pure_evergloom_ingot", "Purify evergloom into an ingot.");

    public static final TextWrapper ADV_TIER_EVERGLOOM_ARMOR_TITLE = registerAdvancementTitle("obtain_evergloom_armor", "Camouflage");
    public static final TextWrapper ADV_TIER_EVERGLOOM_ARMOR_DESC = registerAdvancementDescription("obtain_evergloom_armor", "Obtain evergloom armor.");

    public static final TextWrapper ADV_TIER_EVERGLOOM_PICKAXE_TITLE = registerAdvancementTitle("obtain_evergloom_pickaxe", "Leave the Past Behind");
    public static final TextWrapper ADV_TIER_EVERGLOOM_PICKAXE_DESC = registerAdvancementDescription("obtain_evergloom_pickaxe", "Upgrade your pickaxe with knowledge powder.");

    public static final TextWrapper ADV_TIER_END_TITLE = registerAdvancementTitle("obtain_verum_ingot", "Fragment of Truth");
    public static final TextWrapper ADV_TIER_END_DESC = registerAdvancementDescription("obtain_verum_ingot", "Obtain a verum ingot.");

    public static final TextWrapper ADV_TIER_END_ARMOR_TITLE = registerAdvancementTitle("obtain_end_armor", "No Time to Relax");
    public static final TextWrapper ADV_TIER_END_ARMOR_DESC = registerAdvancementDescription("obtain_end_armor", "Obtain end armor.");

    public static final TextWrapper ADV_TIER_END_PICKAXE_TITLE = registerAdvancementTitle("obtain_end_pickaxe", "Is This The End?");
    public static final TextWrapper ADV_TIER_END_PICKAXE_DESC = registerAdvancementDescription("obtain_end_pickaxe", "Craft an end pickaxe.");

    // ---- SPECIAL MATERIAL TIER ADVANCEMENTS ----
    public static final TextWrapper ADV_TOOL_AMETHYST_SHEARS_TITLE = registerAdvancementTitle("obtain_amethyst_shears", "A Cut Above");
    public static final TextWrapper ADV_TOOL_AMETHYST_SHEARS_DESC = registerAdvancementDescription("obtain_amethyst_shears", "Improve your wool drops with amethyst shears.");

    public static final TextWrapper ADV_TOOL_STELLAR_TRIDENT_TITLE = registerAdvancementTitle("obtain_stellar_trident", "Cosmic Beauty");
    public static final TextWrapper ADV_TOOL_STELLAR_TRIDENT_DESC = registerAdvancementDescription("obtain_stellar_trident", "Infuse your trident with a stellar soul.");

    public static final TextWrapper ADV_TOOL_FLINT_AND_AMETHYST_TITLE = registerAdvancementTitle("obtain_flint_and_amethyst", "An Elegant Spark");
    public static final TextWrapper ADV_TOOL_FLINT_AND_AMETHYST_DESC = registerAdvancementDescription("obtain_flint_and_amethyst", "Obtain flint & amethyst, an elegant way to ignite flames.");

    public static final TextWrapper ADV_TOOL_END_SHEARS_TITLE = registerAdvancementTitle("obtain_end_shears", "Completionist");
    public static final TextWrapper ADV_TOOL_END_SHEARS_DESC = registerAdvancementDescription("obtain_end_shears", "Upgrade your shears to the end.");


    // ---- ENEMY RANKING ADVANCEMENTS ----
    public static final TextWrapper ADV_RANK_1_TITLE = registerAdvancementTitle("defeat_rank_1", "Proving Ground");
    public static final TextWrapper ADV_RANK_1_DESC = registerAdvancementDescription("defeat_rank_1", "Defeat an enemy of Rank 1.");

    public static final TextWrapper ADV_RANK_5_TITLE = registerAdvancementTitle("defeat_rank_5", "Warrior");
    public static final TextWrapper ADV_RANK_5_DESC = registerAdvancementDescription("defeat_rank_5", "Defeat an enemy of Rank 5.");

    public static final TextWrapper ADV_RANK_10_TITLE = registerAdvancementTitle("defeat_rank_10", "Unrivaled");
    public static final TextWrapper ADV_RANK_10_DESC = registerAdvancementDescription("defeat_rank_10", "Defeat an enemy of Rank 10.");

    public static final TextWrapper ADV_HIGHER_RANK_TITLE = registerAdvancementTitle("defeat_higher_rank", "Slayer");
    public static final TextWrapper ADV_HIGHER_RANK_DESC = registerAdvancementDescription("defeat_higher_rank", "Defeat an enemy beyond your level.");

    public static final TextWrapper ADV_MUCH_HIGHER_RANK_TITLE = registerAdvancementTitle("defeat_much_higher_rank", "First of Your Name");
    public static final TextWrapper ADV_MUCH_HIGHER_RANK_DESC = registerAdvancementDescription("defeat_much_higher_rank", "Defeat an enemy far beyond your level.");

    // ---- FOOD ADVANCEMENTS ----
    public static final TextWrapper ADV_EAT_GOLDEN_APPLE_TITLE = registerAdvancementTitle("eat_golden_apple", "A Taste of Wealth");
    public static final TextWrapper ADV_EAT_GOLDEN_APPLE_DESC = registerAdvancementDescription("eat_golden_apple", "Eat a golden apple.");

    public static final TextWrapper ADV_EAT_ENCHANTED_GOLDEN_APPLE_TITLE = registerAdvancementTitle("eat_enchanted_golden_apple", "A King's Banquet");
    public static final TextWrapper ADV_EAT_ENCHANTED_GOLDEN_APPLE_DESC = registerAdvancementDescription("eat_enchanted_golden_apple", "Consume the legendary enchanted golden apple.");

    public static final TextWrapper ADV_EAT_STAR_APPLE_TITLE = registerAdvancementTitle("eat_star_apple", "A Cosmic Treat");
    public static final TextWrapper ADV_EAT_STAR_APPLE_DESC = registerAdvancementDescription("eat_star_apple", "Taste celestial energy.");

    public static final TextWrapper ADV_EAT_ENCHANTED_STAR_APPLE_TITLE = registerAdvancementTitle("eat_enchanted_star_apple", "Wither Enjoyer");
    public static final TextWrapper ADV_EAT_ENCHANTED_STAR_APPLE_DESC = registerAdvancementDescription("eat_enchanted_star_apple", "Eat an enchanted star apple.");

    public static final TextWrapper ADV_EAT_COSMIC_SOUP_TITLE = registerAdvancementTitle("eat_cosmic_soup", "Soup of the Stars");
    public static final TextWrapper ADV_EAT_COSMIC_SOUP_DESC = registerAdvancementDescription("eat_cosmic_soup", "Enjoy a bowl of cosmic soup.");

    public static final TextWrapper ADV_EAT_NETHER_WART_TITLE = registerAdvancementTitle("eat_nether_wart", "Death Wish");
    public static final TextWrapper ADV_EAT_NETHER_WART_DESC = registerAdvancementDescription("eat_nether_wart", "Try surviving a nether wart.");

    public static final TextWrapper ADV_EAT_GLISTERING_MELON_TITLE = registerAdvancementTitle("eat_glistering_melon", "Definitely Edible!");
    public static final TextWrapper ADV_EAT_GLISTERING_MELON_DESC = registerAdvancementDescription("eat_glistering_melon", "Eat a glistering melon.");

    public static final TextWrapper ADV_EAT_GOLDEN_CARROT_TITLE = registerAdvancementTitle("eat_golden_carrot", "Very Good Eyes");
    public static final TextWrapper ADV_EAT_GOLDEN_CARROT_DESC = registerAdvancementDescription("eat_golden_carrot", "Eat a golden carrot.");

    public static final TextWrapper ADV_EAT_SLIME_TITLE = registerAdvancementTitle("eat_slime", "Gourmets Sans Frontières");
    public static final TextWrapper ADV_EAT_SLIME_DESC = registerAdvancementDescription("eat_slime", "Eat slime.");

    public static final TextWrapper ADV_EAT_ROTTEN_FLESH_TITLE = registerAdvancementTitle("eat_rotten_flesh", "A Questionable Snack");
    public static final TextWrapper ADV_EAT_ROTTEN_FLESH_DESC = registerAdvancementDescription("eat_rotten_flesh", "Eat rotten flesh.");

    public static final TextWrapper ADV_EAT_PUFFERFISH_TITLE = registerAdvancementTitle("eat_pufferfish", "Risky Delicacy");
    public static final TextWrapper ADV_EAT_PUFFERFISH_DESC = registerAdvancementDescription("eat_pufferfish", "Try surviving a pufferfish.");

    public static final TextWrapper ADV_EAT_TURTLE_EGG_TITLE = registerAdvancementTitle("eat_turtle_egg", "Forbidden Omelet");
    public static final TextWrapper ADV_EAT_TURTLE_EGG_DESC = registerAdvancementDescription("eat_turtle_egg", "Eat a turtle egg. Morally disgusting, personally...");

    // ---- SPECIAL ITEM COLLECTION ADVANCEMENTS ----
    public static final TextWrapper ADV_ITEM_JETPACK_TITLE = registerAdvancementTitle("obtain_jetpack", "Ascend Beyond");
    public static final TextWrapper ADV_ITEM_JETPACK_DESC = registerAdvancementDescription("obtain_jetpack", "Harness the power of flight with a jetpack.");

    public static final TextWrapper ADV_ITEM_BURNED_CD_TITLE = registerAdvancementTitle("obtain_burned_cd", "Is It Edible?");
    public static final TextWrapper ADV_ITEM_BURNED_CD_DESC = registerAdvancementDescription("obtain_burned_cd", "Fish a burned CD out of lava.");

    public static final TextWrapper ADV_ITEM_RUBY_TITLE = registerAdvancementTitle("obtain_ruby", "Real Treasure");
    public static final TextWrapper ADV_ITEM_RUBY_DESC = registerAdvancementDescription("obtain_ruby", "Find a ruby.");

    public static final TextWrapper ADV_ITEM_HEART_OF_GREED_TITLE = registerAdvancementTitle("obtain_heart_of_greed", "What For?");
    public static final TextWrapper ADV_ITEM_HEART_OF_GREED_DESC = registerAdvancementDescription("obtain_heart_of_greed", "Change your life by obtaining the heart of greed.");

    public static final TextWrapper ADV_ITEM_SILENT_HEART_TITLE = registerAdvancementTitle("obtain_silent_heart", "A Silent Past");
    public static final TextWrapper ADV_ITEM_SILENT_HEART_DESC = registerAdvancementDescription("obtain_silent_heart", "Obtain the silent heart, a remnant of what once was.");

    public static final TextWrapper ADV_ITEM_ANGEL_RING_TITLE = registerAdvancementTitle("obtain_angel_ring", "Flightless Wings");
    public static final TextWrapper ADV_ITEM_ANGEL_RING_DESC = registerAdvancementDescription("obtain_angel_ring", "Obtain an angel ring.");

    public static final TextWrapper ADV_ITEM_STAR_TITLE = registerAdvancementTitle("obtain_star", "True Star");
    public static final TextWrapper ADV_ITEM_STAR_DESC = registerAdvancementDescription("obtain_star", "Obtain a star from another era.");

    public static final TextWrapper ADV_ITEM_MECHANICAL_BOOTS_TITLE = registerAdvancementTitle("obtain_mechanical_boots", "Industrial Convenience");
    public static final TextWrapper ADV_ITEM_MECHANICAL_BOOTS_DESC = registerAdvancementDescription("obtain_mechanical_boots", "Step up your game with a pair of mechanical boots.");

    public static final TextWrapper ADV_ITEM_OCEANS_GRACE_TITLE = registerAdvancementTitle("obtain_oceans_grace", "Ocean's Blessing");
    public static final TextWrapper ADV_ITEM_OCEANS_GRACE_DESC = registerAdvancementDescription("obtain_oceans_grace", "Continue mining with ocean's grace.");

    public static final TextWrapper ADV_ITEM_DREAM_CATCHER_TITLE = registerAdvancementTitle("obtain_dream_catcher", "Peaceful Sleep");
    public static final TextWrapper ADV_ITEM_DREAM_CATCHER_DESC = registerAdvancementDescription("obtain_dream_catcher", "Overcome insomnia with a dream catcher.");

    public static final TextWrapper ADV_ITEM_ANCHOR_TITLE = registerAdvancementTitle("obtain_anchor", "Say No To Lifts");
    public static final TextWrapper ADV_ITEM_ANCHOR_DESC = registerAdvancementDescription("obtain_anchor", "Protect yourself against unwanted flights with an anchor.");

    // ---- ENCHANTING ADVANCEMENTS ----
    public static final TextWrapper ADV_ENCHANTING_X_TITLE = registerAdvancementTitle("enchant_x", "Archmage");
    public static final TextWrapper ADV_ENCHANTING_X_DESC = registerAdvancementDescription("enchant_x", "Enchant an item with a level X enchantment.");

    // ---- FISHING ADVANCEMENTS ----
    public static final TextWrapper ADV_FISH_LAVA_TITLE = registerAdvancementTitle("catch_lava_fish", "Hot Business");
    public static final TextWrapper ADV_FISH_LAVA_DESC = registerAdvancementDescription("catch_lava_fish", "Pull a fish out of lava.");

    public static final TextWrapper ADV_FISH_VOID_TITLE = registerAdvancementTitle("catch_void_fish", "Fisher of the Void");
    public static final TextWrapper ADV_FISH_VOID_DESC = registerAdvancementDescription("catch_void_fish", "Retrieve a fish from the abyss of nothingness.");

    public static final TextWrapper ADV_FISH_GOLDEN_LOBSTER_TITLE = registerAdvancementTitle("catch_golden_lobster", "Legendary Catch");
    public static final TextWrapper ADV_FISH_GOLDEN_LOBSTER_DESC = registerAdvancementDescription("catch_golden_lobster", "Reel in a golden lobster.");

    public static TextWrapper registerRaw(String id, String text) {
        TextWrapper wrapper = new TextWrapper(id);
        data.put(wrapper, new TextData(text));
        return wrapper;
    }

    public static TextWrapper registerText(String id, String text) {
        return registerRaw("text." + Prog.MOD_ID + "." + id.toLowerCase(), text);
    }

    public static TextWrapper registerAdvancementTitle(String id, String text) {
        return registerRaw("advancement." + Prog.MOD_ID + "." + id.toLowerCase() + ".title", text);
    }

    public static TextWrapper registerAdvancementDescription(String id, String text) {
        return registerRaw("advancement." + Prog.MOD_ID + "." + id.toLowerCase() + ".description", text);
    }

    public static void init() {
        LOGGER.info("Registering Translatable Texts for: " + Prog.MOD_ID);
    }
}
