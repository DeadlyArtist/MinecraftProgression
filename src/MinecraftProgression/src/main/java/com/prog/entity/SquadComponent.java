package com.prog.entity;

import com.prog.entity.attribute.XEntityAttributes;
import com.prog.itemOrBlock.tiers.PTierData;
import com.prog.text.PTexts;
import com.prog.utils.EntityAttributeModifierUtils;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SquadComponent implements Component, ServerTickingComponent {
    public final static String squadHealthModifierName = "squad_health";
    public final static String squadDamageModifierName = "squad_damage";
    public final static String squadProjectileDamageModifierName = "squad_projectile_damage";

    public final MobEntity entity;

    public boolean didInit = false;
    public int rank = 0;

    public SquadComponent(MobEntity entity) {
        this.entity = entity;
    }

    public void tryInit() {
        if (didInit) return;
        didInit = true;

        if (!(entity instanceof HostileEntity)) return;
        if (entity.hasCustomName()) return;

        setRandomRank();
        setTitle();
        increaseAttributes();
    }

    public void setRandomRank() {
        var player = entity.world.getClosestPlayer(entity, 100);

        // https://deadlyartist.github.io/aidevsuite/#local/live_calculator?mode=run
        //var level = 8
        //var prob = 0.1
        //var power = Math.pow(1.35, level - 1) - 0.5
        //prob = 1 - Math.pow(1 - prob, power);
        //[power, prob, Math.pow(prob, 5)]
        var power = 4D;
        if (player != null) {
            for (var stack : player.getInventory().armor) {
                var item = stack.getItem();
                var level = PTierData.getTierLevel(item);
                var weight = Math.pow(1.35, level - 1) - 0.5;
                power += weight;
            }
        }
        power /= 4;

        var prob = 0.1;
        prob = 1 - Math.pow(1 - prob, power);

        rank = randomIncrement(prob);
    }

    public boolean normal() {
        return rank == 0;
    }

    public void setTitle() {
        if (entity.hasCustomName() || normal()) return;

        entity.setCustomName(PTexts.nameByRank.get(Math.min(6, rank)).get());
    }

    public void increaseAttributes() {
        if (normal()) return;

        increaseHealth();
        increaseDamage();
        increaseProjectileDamage();
    }

    public void increaseHealth() {
        EntityAttributeInstance entityAttributeInstance = entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (entityAttributeInstance == null) return;

        Random random = new Random();
        double baseModifier = Math.pow(2, rank);
        double modifiedValue = randomOffset(baseModifier, 0.2);

        var modifier = EntityAttributeModifierUtils.of(squadHealthModifierName, modifiedValue, EntityAttributeModifier.Operation.MULTIPLY_BASE);
        if (!entityAttributeInstance.hasModifier(modifier)) entityAttributeInstance.addPersistentModifier(modifier);
        entity.setHealth((float) entity.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH));
    }

    public void increaseDamage() {
        EntityAttributeInstance entityAttributeInstance = entity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        if (entityAttributeInstance == null) return;

        Random random = new Random();
        double baseModifier = Math.pow(2, rank);
        double modifiedValue = randomOffset(baseModifier, 0.2);

        var modifier = EntityAttributeModifierUtils.of(squadDamageModifierName, modifiedValue, EntityAttributeModifier.Operation.MULTIPLY_BASE);
        if (!entityAttributeInstance.hasModifier(modifier)) entityAttributeInstance.addPersistentModifier(modifier);
    }

    public void increaseProjectileDamage() {
        EntityAttributeInstance entityAttributeInstance = entity.getAttributeInstance(XEntityAttributes.PROJECTILE_DAMAGE);
        if (entityAttributeInstance == null) return;

        Random random = new Random();
        double baseModifier = Math.pow(2, rank);
        double modifiedValue = randomOffset(baseModifier, 0.2);

        var modifier = EntityAttributeModifierUtils.of(squadProjectileDamageModifierName, modifiedValue, EntityAttributeModifier.Operation.MULTIPLY_BASE);
        assert entityAttributeInstance != null;
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
    public static int randomIncrement(int startVal, int maxVal, double prob) {
        if (startVal >= maxVal) {
            return startVal;
        }

        Random random = new Random();
        double randomChance;
        do {
            randomChance = random.nextDouble();
        } while(randomChance <= prob && startVal++ < maxVal);

        return startVal;
    }

    /**
     * Overloaded version with default parameters (starting value 0, max value Integer.MAX_VALUE).
     */
    public static int randomIncrement(double prob) {
        return randomIncrement(0, Integer.MAX_VALUE, prob);
    }

    /**
     * Applies a random offset to a baseValue within a ±maxOffset percentage.
     *
     * @param baseValue the original value to apply the random offset to.
     * @param maxOffset the maximum percentage offset (e.g., 0.2 for 20%).
     * @return the base value adjusted by a random factor within ±maxOffset percentage.
     * If maxOffset is 0, the base value is returned unmodified.
     */
    public static double randomOffset(double baseValue, double maxOffset) {
        if (maxOffset == 0) return baseValue;

        Random random = new Random();
        // Generate a random factor between (1 - maxOffset) and (1 + maxOffset)
        double randomFactor = 1 - maxOffset + (2 * maxOffset * random.nextDouble());
        return baseValue * randomFactor;
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
