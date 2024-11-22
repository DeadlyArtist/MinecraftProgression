package com.prog.mixin;

import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

@Mixin(ZombieEntity.class)
public abstract class ZombieEntityMixin {

    @Shadow
    private static Predicate<Difficulty> DOOR_BREAK_DIFFICULTY_CHECKER = difficulty -> false;
}