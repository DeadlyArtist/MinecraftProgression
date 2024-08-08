package com.prog.entity;

import com.prog.Prog;
import com.prog.entity.attribute.PEntityAttributes;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class PlayerComponent implements Component, ServerTickingComponent, AutoSyncedComponent {
    public final PlayerEntity player;
    public boolean couldFly = false;
    public boolean didInit = false;

    public PlayerComponent(PlayerEntity player){
        this.player = player;
    }

    public void tryInit() {
        if (didInit) return;
        didInit = true;
        couldFly = canFly();
    }

    public boolean canFly() {
        return player.getAttributeValue(PEntityAttributes.FLIGHT) == 1;
    }

    public void updateFlight() {
        if (player.isCreative() || player.isSpectator()) return;
        tryInit();

        boolean canFly = canFly();
        var abilities = player.getAbilities();
        if (canFly) {
            if (!abilities.allowFlying) {
                abilities.allowFlying = true;
                player.sendAbilitiesUpdate();
            }
        } else if (couldFly) {
            if (abilities.allowFlying) {
                abilities.allowFlying = false;
                abilities.flying = false;
                player.sendAbilitiesUpdate();
            }
        }

        if (couldFly != canFly) {
            couldFly = canFly;
            PComponents.LIVING_ENTITY.sync(player);
        }
    }

    @Override
    public void serverTick() {
        // Not needed for now
    }

    @Override
    public void readFromNbt(NbtCompound nbt) {
        couldFly = nbt.getBoolean("couldFly");
    }

    @Override
    public void writeToNbt(NbtCompound nbt) {
        nbt.putBoolean("couldFly", couldFly);
    }
}
