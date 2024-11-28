package com.prog.entity.component;

import com.prog.entity.PComponents;
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
    public boolean luminanceDisabled = false;
    public boolean stepAssistDisabled = false;
    public boolean badOmenImmunityDisabled = false;
    public boolean magnetDisabled = false;
    public boolean silentDisabled = false;
    public boolean elytraDisabled = false;

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
        luminanceDisabled = nbt.getBoolean("luminanceDisabled");
        stepAssistDisabled = nbt.getBoolean("stepAssistDisabled");
        badOmenImmunityDisabled = nbt.getBoolean("badOmenImmunityDisabled");
        magnetDisabled = nbt.getBoolean("magnetDisabled");
        silentDisabled = nbt.getBoolean("silentDisabled");
        elytraDisabled = nbt.getBoolean("elytraDisabled");
    }

    @Override
    public void writeToNbt(NbtCompound nbt) {
        nbt.putBoolean("couldFly", couldFly);
        nbt.putBoolean("luminanceDisabled", luminanceDisabled);
        nbt.putBoolean("stepAssistDisabled", stepAssistDisabled);
        nbt.putBoolean("badOmenImmunityDisabled", badOmenImmunityDisabled);
        nbt.putBoolean("magnetDisabled", magnetDisabled);
        nbt.putBoolean("silentDisabled", silentDisabled);
        nbt.putBoolean("elytraDisabled", elytraDisabled);
    }
}
