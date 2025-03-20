package com.prog.data;

import com.prog.itemOrBlock.PItems;
import com.prog.lootTable.PLootTables;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootTableEntry;
import net.minecraft.loot.function.EnchantWithLevelsLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.function.SetDamageLootFunction;
import net.minecraft.loot.function.SetPotionLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.potion.Potions;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.FishingHookPredicate;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;

public class PFishingLootTableProvider extends SimpleFabricLootTableProvider {
    public PFishingLootTableProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator, LootContextTypes.FISHING);
    }

    @Override
    public void accept(BiConsumer<Identifier, LootTable.Builder> biConsumer) {

        // Lava Fishing
        biConsumer.accept(
                PLootTables.LAVA_FISHING_GAMEPLAY,
                LootTable.builder()
                        .pool(
                                LootPool.builder()
                                        .rolls(ConstantLootNumberProvider.create(1.0F))
                                        .with(LootTableEntry.builder(PLootTables.LAVA_FISHING_JUNK_GAMEPLAY).weight(10).quality(-2))
                                        .with(
                                                LootTableEntry.builder(PLootTables.LAVA_FISHING_TREASURE_GAMEPLAY)
                                                        .weight(5)
                                                        .quality(2)
                                                        .conditionally(
                                                                EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, EntityPredicate.Builder.create().typeSpecific(FishingHookPredicate.of(true)))
                                                        )
                                        )
                                        .with(LootTableEntry.builder(PLootTables.LAVA_FISHING_FISH_GAMEPLAY).weight(85).quality(-1))
                        )
        );
        biConsumer.accept(
                PLootTables.LAVA_FISHING_FISH_GAMEPLAY,
                LootTable.builder()
                        .pool(
                                LootPool.builder()
                                        .with(ItemEntry.builder(PItems.SUNFISH).weight(60))
                                        .with(ItemEntry.builder(PItems.RAINBOWFISH).weight(25))
                                        .with(ItemEntry.builder(PItems.GOLDEN_LOBSTER).weight(2))
                                        .with(ItemEntry.builder(PItems.BLACK_BASS).weight(13))
                        )
        );
        biConsumer.accept(
                PLootTables.LAVA_FISHING_JUNK_GAMEPLAY,
                LootTable.builder()
                        .pool(
                                LootPool.builder()
                                        .with(ItemEntry.builder(Items.NETHER_WART).weight(17))
                                        .with(ItemEntry.builder(Items.GOLDEN_BOOTS).weight(10).apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.0F, 0.9F))))
                                        .with(ItemEntry.builder(PItems.BURNED_CD).weight(10))
                                        .with(ItemEntry.builder(PItems.BLUE_ALGAE).weight(10))
                                        .with(ItemEntry.builder(Items.BONE).weight(10))
                                        .with(ItemEntry.builder(Items.BLAZE_ROD).weight(10))
                                        .with(ItemEntry.builder(Items.GOLDEN_SWORD).weight(2).apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.0F, 0.9F))))
                                        .with(ItemEntry.builder(Items.GOLDEN_AXE).weight(2).apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.0F, 0.9F))))
                                        .with(ItemEntry.builder(Items.CROSSBOW).weight(2).apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.0F, 0.9F))))
                                        .with(ItemEntry.builder(Items.SADDLE).weight(2))
                                        .with(ItemEntry.builder(Items.COOKED_PORKCHOP).weight(2))
                                        .with(ItemEntry.builder(Items.MUSIC_DISC_PIGSTEP).weight(2))
                        )
        );
        biConsumer.accept(
                PLootTables.LAVA_FISHING_TREASURE_GAMEPLAY,
                LootTable.builder()
                        .pool(
                                LootPool.builder()
                                        .with(ItemEntry.builder(PItems.RUBY))
                                        .with(ItemEntry.builder(Items.NETHERITE_SCRAP))
                                        .with(ItemEntry.builder(Items.EXPERIENCE_BOTTLE))
                                        .with(ItemEntry.builder(Items.WITHER_SKELETON_SKULL))
                                        .with(ItemEntry.builder(Items.GHAST_TEAR))
                                        .with(ItemEntry.builder(Items.POTION).apply(SetPotionLootFunction.builder(Potions.FIRE_RESISTANCE)))
                                        .with(ItemEntry.builder(Items.BOOK).apply(EnchantWithLevelsLootFunction.builder(ConstantLootNumberProvider.create(30.0F)).allowTreasureEnchantments()))
                        )
        );

        // Void Fishing
        biConsumer.accept(
                PLootTables.VOID_FISHING_GAMEPLAY,
                LootTable.builder()
                        .pool(
                                LootPool.builder()
                                        .rolls(ConstantLootNumberProvider.create(1.0F))
                                        .with(LootTableEntry.builder(PLootTables.VOID_FISHING_JUNK_GAMEPLAY).weight(10).quality(-2))
                                        .with(
                                                LootTableEntry.builder(PLootTables.VOID_FISHING_TREASURE_GAMEPLAY)
                                                        .weight(5)
                                                        .quality(2)
                                                        .conditionally(
                                                                EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, EntityPredicate.Builder.create().typeSpecific(FishingHookPredicate.of(true)))
                                                        )
                                        )
                                        .with(LootTableEntry.builder(PLootTables.VOID_FISHING_FISH_GAMEPLAY).weight(85).quality(-1))
                        )
        );
        biConsumer.accept(
                PLootTables.VOID_FISHING_FISH_GAMEPLAY,
                LootTable.builder()
                        .pool(
                                LootPool.builder()
                                        .with(ItemEntry.builder(PItems.FLYING_FISH).weight(60))
                                        .with(ItemEntry.builder(PItems.STELLAR_JELLY).weight(25))
                                        .with(ItemEntry.builder(PItems.SPACE_EEL).weight(5))
                                        .with(ItemEntry.builder(PItems.VACUUM_FISH).weight(10))
                        )
        );
        biConsumer.accept(
                PLootTables.VOID_FISHING_JUNK_GAMEPLAY,
                LootTable.builder()
                        .pool(
                                LootPool.builder()
                                        .with(ItemEntry.builder(Items.ENDER_PEARL).weight(17))
                                        .with(ItemEntry.builder(Items.GRASS_BLOCK).weight(10))
                                        .with(ItemEntry.builder(Items.ARROW).weight(10))
                                        .with(ItemEntry.builder(Items.GLASS_BOTTLE).weight(10))
                                        .with(ItemEntry.builder(Items.SHULKER_SHELL).weight(10))
                                        .with(ItemEntry.builder(Items.POTION).weight(10).apply(SetPotionLootFunction.builder(Potions.LEAPING)))
                                        .with(
                                                ItemEntry.builder(Items.ELYTRA)
                                                        .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.0F, 0.25F)))
                                                        .weight(2))
                                        .with(ItemEntry.builder(Items.MUSIC_DISC_FAR).weight(2))
                                        .with(ItemEntry.builder(Items.RED_BED).weight(2))
                        )
        );
        biConsumer.accept(
                PLootTables.VOID_FISHING_TREASURE_GAMEPLAY,
                LootTable.builder()
                        .pool(
                                LootPool.builder()
                                        .with(ItemEntry.builder(PItems.STAR_FRAGMENT))
                                        .with(ItemEntry.builder(PItems.RAW_VERUM))
                                        .with(ItemEntry.builder(PItems.STELLAR_SHARD))
                                        .with(
                                                ItemEntry.builder(Items.TRIDENT)
                                                        .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.0F, 0.25F)))
                                                        .apply(EnchantWithLevelsLootFunction.builder(ConstantLootNumberProvider.create(30.0F)).allowTreasureEnchantments())
                                        )
                                        .with(ItemEntry.builder(Items.BOOK).apply(EnchantWithLevelsLootFunction.builder(ConstantLootNumberProvider.create(30.0F)).allowTreasureEnchantments()))
                        )
        );
    }
}
