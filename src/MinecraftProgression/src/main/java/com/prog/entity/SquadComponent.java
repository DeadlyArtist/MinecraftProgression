package com.prog.entity;

import com.prog.entity.attribute.PEntityAttributes;
import com.prog.entity.attribute.XEntityAttributes;
import com.prog.itemOrBlock.tiers.PTierData;
import com.prog.text.PTexts;
import com.prog.utils.EntityAttributeModifierUtils;
import com.prog.utils.RandomUtils;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.SpawnHelper;
import net.minecraft.util.math.random.Random;

public class SquadComponent implements Component, ServerTickingComponent {
    public final static String squadHealthModifierName = "squad_health";
    public final static String squadDamageModifierName = "squad_damage";
    public final static String squadProjectileDamageModifierName = "squad_projectile_damage";

    public final MobEntity entity;
    public MobEntity leader = null;

    public boolean didInit = false;
    public int rank = 0;

    public SquadComponent(MobEntity entity) {
        this.entity = entity;
    }

    public void makeFollower(MobEntity leader) {
        this.leader = leader;
    }

    public void tryInit() {
        if (didInit) return;
        didInit = true;

        var isHostile = entity instanceof HostileEntity; // Ender dragon is not hostile entity
        var inBlacklist = entity instanceof WitherEntity ||
                entity instanceof ElderGuardianEntity ||
                entity instanceof WardenEntity;
        //var inWhitelist = entity instanceof IronGolemEntity iron && !iron.isPlayerCreated(); // Meh, not sure
        if (!isHostile || inBlacklist) return;
        if (entity.hasCustomName()) return;

        setRandomRank();
        if (normal()) return;

        setTitle();
        increaseAttributes();
        spawnFollowers();
    }

    public void setRandomRank() {
        if (MathHelper.nextInt(entity.random, 0, 9) != 9) return; // 90% chance to be normal, 10% chance to be ranked

        var player = entity.world.getClosestPlayer(entity, 1000);

        // https://deadlyartist.github.io/aidevsuite/#extern?url=data/Live%20Calculator.json&mode=run
        // var level = 7
        // var power = level * 6 / 10;
        // [power].join("    ")
        var power = 4D;
        if (player != null) {
            for (var stack : player.getInventory().armor) {
                var item = stack.getItem();
                var level = PTierData.getTierLevel(item);
                var weight = level * 6 / 10;
                power += weight;
            }
        }
        power /= 4;

        rank = (int) Math.round(power);
        rank = Math.max(1, rank);

        var randomInt = MathHelper.nextInt(entity.random, 1, 100);
        if (randomInt > 95) { // too strong (5% chance)
            rank += 1;
            var prob = 0.1;
            rank = randomIncrementRank(prob);
        } else if (randomInt <= 75) { // too weak (75% chance)
            rank = MathHelper.nextInt(entity.random, 1, Math.max(1, rank - 1));
        } // else about right (20% chance)

        if (isFollower()) rank = Math.min(rank, PComponents.SQUAD.get(leader).rank - 1);
    }

    public boolean normal() {
        return rank == 0;
    }

    public boolean isFollower() {
        return leader != null;
    }

    public void setTitle() {
        if (entity.hasCustomName() || normal()) return;

        Text name;
        var namedRanks = PTexts.nameByRank.size();
        if (rank > namedRanks) {
            name = Text.of("?".repeat(rank - namedRanks + 2));
        } else {
            name = PTexts.nameByRank.get(rank).get();
        }
        entity.setCustomName(name);
    }

    public void increaseAttributes() {
        if (normal()) return;

        increaseHealth();
        increaseDamage();
        increaseProjectileDamage();
    }

    public double getHealthMultiplier() {
        double baseModifier = Math.pow(2, rank);
        double modifiedValue = randomOffset(baseModifier, 0.2);
        return modifiedValue;
    }

    public double getDamageMultiplier() {
        double baseModifier = Math.pow(2, rank + 0.7);
        double modifiedValue = randomOffset(baseModifier, 0.2);
        return modifiedValue;
    }

    public double getProjectileDamageMultiplier() {
        double baseModifier = Math.pow(2, rank + 0.2);
        double modifiedValue = randomOffset(baseModifier, 0.2);
        return modifiedValue;
    }

    public void increaseHealth() {
        EntityAttributeInstance entityAttributeInstance = entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (entityAttributeInstance == null) return;

        var modifier = EntityAttributeModifierUtils.of(squadHealthModifierName, getHealthMultiplier(), EntityAttributeModifier.Operation.MULTIPLY_BASE);
        if (!entityAttributeInstance.hasModifier(modifier)) entityAttributeInstance.addPersistentModifier(modifier);
        entity.setHealth((float) entity.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH));
    }

    public void increaseDamage() {
        EntityAttributeInstance entityAttributeInstance = entity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        if (entityAttributeInstance == null) return;

        var modifier = EntityAttributeModifierUtils.of(squadDamageModifierName, getDamageMultiplier(), EntityAttributeModifier.Operation.MULTIPLY_BASE);
        if (!entityAttributeInstance.hasModifier(modifier)) entityAttributeInstance.addPersistentModifier(modifier);
    }

    public void increaseProjectileDamage() {
        EntityAttributeInstance entityAttributeInstance = entity.getAttributeInstance(PEntityAttributes.PROJECTILE_DAMAGE);
        if (entityAttributeInstance == null) return;

        var modifier = EntityAttributeModifierUtils.of(squadProjectileDamageModifierName, getProjectileDamageMultiplier(), EntityAttributeModifier.Operation.MULTIPLY_BASE);
        if (!entityAttributeInstance.hasModifier(modifier)) entityAttributeInstance.addPersistentModifier(modifier);
    }

    /**
     * Recursively increments the value based on the given probability.
     *
     * @param prob     The probability of incrementing the value (0.0 to 1.0).
     * @param startVal The starting value (default 0).
     * @param maxVal   The maximum value to reach (default Integer.MAX_VALUE).
     * @return The final recursively generated value.
     */
    public static int randomIncrement(Random random, int startVal, int maxVal, double prob) {
        if (startVal >= maxVal) {
            return startVal;
        }

        double randomChance;
        do {
            randomChance = random.nextDouble();
        } while(randomChance <= prob && startVal++ < maxVal);

        return startVal;
    }

    /**
     * Overloaded version with default parameters (starting value 0, max value Integer.MAX_VALUE).
     */
    public int randomIncrementRank(double prob) {
        return randomIncrement(entity.random, rank, Integer.MAX_VALUE, prob);
    }

    public double randomOffset(double baseValue, double maxOffset) {
        return RandomUtils.randomOffset(entity.random, baseValue, maxOffset);
    }

    public void spawnFollowers() {
        if (normal() || isFollower() || !(entity.world instanceof ServerWorld serverWorld)) return;

        var min = rank;
        var max = rank * 3;
        var amount = MathHelper.nextInt(entity.random, min, max);
        for (var i = 0; i < amount; i++) {
            spawnFollower();
        }
    }

    public void spawnFollower() {
        if (!(entity.world instanceof ServerWorld serverWorld)) return;

        double i = MathHelper.floor(entity.getX());
        double j = MathHelper.floor(entity.getY());
        double k = MathHelper.floor(entity.getZ());
        // Get the current entity's type
        EntityType<? extends MobEntity> entityType = (EntityType<? extends MobEntity>) entity.getType();

        // Now we are going to try to create a new entity of the same type as the original.
        // Use the `EntityType.create` method to create an entity with the same type in the same world.
        MobEntity follower = (MobEntity) entityType.create(entity.world);
        if (follower == null) return;
        PComponents.SQUAD.get(follower).makeFollower(entity);

        var minDistance = 1;
        var maxDistance = 5;
        for (int l = 0; l < 50; l++) {
            double m = i + MathHelper.nextDouble(entity.random, minDistance, maxDistance) * MathHelper.nextInt(entity.random, -1, 1);
            double n = j + MathHelper.nextDouble(entity.random, minDistance, maxDistance) * MathHelper.nextInt(entity.random, -1, 1);
            double o = k + MathHelper.nextDouble(entity.random, minDistance, maxDistance) * MathHelper.nextInt(entity.random, -1, 1);
            BlockPos blockPos = new BlockPos(m, n, o);
            SpawnRestriction.Location location = SpawnRestriction.getLocation(entityType);
            if (SpawnHelper.canSpawn(location, entity.world, blockPos, entityType)) {
                follower.setPosition((double) m, (double) n, (double) o);
                if (entity.world.isPlayerInRange((double) m, (double) n, (double) o, 7.0)) break;

                if (entity.world.doesNotIntersectEntities(follower)
                        && entity.world.isSpaceEmpty(follower)
                        && !entity.world.containsFluid(follower.getBoundingBox())) {
                    follower.initialize(serverWorld, entity.world.getLocalDifficulty(follower.getBlockPos()), SpawnReason.REINFORCEMENT, null, null);
                    serverWorld.spawnEntityAndPassengers(follower);
                    break;
                }
            }
        }
    }

    @Override
    public void serverTick() {
        tryInit();
    }

    @Override
    public void readFromNbt(NbtCompound nbt) {
        didInit = nbt.getBoolean("didInit");
        rank = nbt.getInt("rank");
    }

    @Override
    public void writeToNbt(NbtCompound nbt) {
        nbt.putBoolean("didInit", didInit);
        nbt.putInt("rank", rank);
    }
}
