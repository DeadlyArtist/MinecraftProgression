package com.prog.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import com.prog.entity.attribute.PEntityAttributes;
import com.prog.lootTable.PLootTables;
import com.prog.utils.FishingUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.*;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingBobberEntity.class)
public class FishingBobberEntityMixin {
    @Unique
    public final FishingBobberEntity self = (FishingBobberEntity)(Object)this;

    @Unique
    public FishingUtils.AllowedFluidTestResult previousResult;

    @Redirect(method = "removeIfInvalid", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 0))
    public boolean redirectIsOf(ItemStack instance, Item item) {
        return instance.getItem() instanceof FishingRodItem;
    }

    @Redirect(method = "removeIfInvalid", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 1))
    public boolean redirectIsOf2(ItemStack instance, Item item) {
        return instance.getItem() instanceof FishingRodItem;
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/fluid/FluidState;isIn(Lnet/minecraft/tag/TagKey;)Z", ordinal = 0))
    public boolean changeAllowedFluids(FluidState instance, TagKey<Fluid> tag, @Local LocalFloatRef fRef) {
        var result = previousResult = FishingUtils.changeAllowedFluids(self);
        if (result.inVoid) fRef.set(0.8f);
        return result.allowed && !result.inVoid;
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/fluid/FluidState;isIn(Lnet/minecraft/tag/TagKey;)Z", ordinal = 1))
    public boolean changeAllowedFluids(FluidState instance, TagKey<Fluid> tag) {
        return previousResult.allowed;
    }

    @Unique
    public Vec3d previousVec;
    @Unique
    public BlockPos previousPos;
    @Redirect(method = "tickFishingLogic", at = @At(value = "NEW", target = "(DDD)Lnet/minecraft/util/math/BlockPos;", ordinal = 0))
    public BlockPos redirectBlockPos(double d, double e, double f) {
        previousVec = new Vec3d(d, e + 1, f);
        return previousPos = new BlockPos(d, e, f);
    }
    @Redirect(method = "tickFishingLogic", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z", ordinal = 0))
    public boolean changeAllowedFluids3(BlockState instance, Block block, @Local ServerWorld serverWorld, @Local(ordinal = 0) float f) {
        var result = FishingUtils.changeAllowedFluids(self, previousPos);
        if (!result.allowed) return false;

        float g = MathHelper.sin(f);
        float h = MathHelper.cos(f);
        float k = g * 0.04F;
        float l = h * 0.04F;

        if (result.inLava && previousResult.inLava) {
            serverWorld.spawnParticles(ParticleTypes.FALLING_LAVA, previousVec.x, previousVec.y, previousVec.z, 1, 0.1F, 0.0, 0.1F, 0.0);
            serverWorld.spawnParticles(ParticleTypes.LAVA, previousVec.x, previousVec.y, previousVec.z, 0, (double) l, 0.01, (double) (-k), 1.0);
            serverWorld.spawnParticles(ParticleTypes.LAVA, previousVec.x, previousVec.y, previousVec.z, 0, (double) (-l), 0.01, (double) k, 1.0);
        }
        if (result.inVoid && previousResult.inVoid) {
            serverWorld.spawnParticles(ParticleTypes.DRAGON_BREATH, previousPos.getX(), previousPos.getY() + 1, previousPos.getZ(), 2 + self.random.nextInt(2), 0.1F, 0.0, 0.1F, 0.0); // Emulate tp behaviour
        }

        return result.inWater && previousResult.inWater;
    }
    @Redirect(method = "tickFishingLogic", at = @At(value = "NEW", target = "(DDD)Lnet/minecraft/util/math/BlockPos;", ordinal = 1))
    public BlockPos redirectBlockPos2(double d, double e, double f) {
        previousVec = new Vec3d(d, e + 1, f);
        return previousPos = new BlockPos(d, e, f);
    }
    @Redirect(method = "tickFishingLogic", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z", ordinal = 1))
    public boolean changeAllowedFluids4(BlockState instance, Block block, @Local ServerWorld serverWorld) {
        var result = FishingUtils.changeAllowedFluids(self, previousPos);
        if (!result.allowed) return false;

        if (result.inLava && previousResult.inLava) {
            serverWorld.spawnParticles(ParticleTypes.LAVA, previousVec.x, previousVec.y, previousVec.z, 2 + self.random.nextInt(2), 0.1F, 0.0, 0.1F, 0.0);
        }
        if (result.inVoid && previousResult.inVoid) {
            serverWorld.spawnParticles(ParticleTypes.DRAGON_BREATH, previousVec.x, previousVec.y, previousVec.z, 2 + self.random.nextInt(2), 0.1F, 0.0, 0.1F, 0.0);
        }

        return result.inWater && previousResult.inWater;
    }

    @Redirect(method = "tickFishingLogic", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/FishingBobberEntity;playSound(Lnet/minecraft/sound/SoundEvent;FF)V"))
    public void redirectPlaySound(FishingBobberEntity instance, SoundEvent soundEvent, float volume, float pitch, @Local ServerWorld serverWorld) {
        if (previousResult.inWater) {
            self.playSound(SoundEvents.ENTITY_FISHING_BOBBER_SPLASH, volume, pitch);
        }
        if (previousResult.inLava) {
            self.playSound(SoundEvents.ENTITY_FISHING_BOBBER_SPLASH, volume * 2, pitch - 0.8f);
        }
        if (previousResult.inVoid) {
            self.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, volume * 4, pitch);
        }

    }

    @Redirect(method = "tickFishingLogic", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;spawnParticles(Lnet/minecraft/particle/ParticleEffect;DDDIDDDD)I", ordinal = 3))
    public <T extends net.minecraft.particle.ParticleEffect> int redirectSpawnSuccessParticles1(ServerWorld instance, T particle, double x, double y, double z, int count, double deltaX, double deltaY, double deltaZ, double speed, @Local ServerWorld serverWorld) {
        if (previousResult.inWater) {
            serverWorld.spawnParticles(
                    ParticleTypes.BUBBLE, x, y, z, count, deltaX, deltaY, deltaZ, speed
            );
            serverWorld.spawnParticles(
                    ParticleTypes.FISHING, x, y, z, count, deltaX, deltaY, deltaZ, speed
            );
        }
        if (previousResult.inLava) {
            serverWorld.spawnParticles(
                    ParticleTypes.LAVA, x, y, z, count, deltaX, deltaY, deltaZ, speed
            );
        }
        if (previousResult.inVoid) {
            serverWorld.spawnParticles(ParticleTypes.DRAGON_BREATH, x, y, z, 10, 0.2F, 0.1, 0.2F, 0.0);
        }

        return 1;
    }

    @Redirect(method = "tickFishingLogic", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;spawnParticles(Lnet/minecraft/particle/ParticleEffect;DDDIDDDD)I", ordinal = 4))
    public <T extends net.minecraft.particle.ParticleEffect> int redirectSpawnSuccessParticles2(ServerWorld instance, T particle, double x, double y, double z, int count, double deltaX, double deltaY, double deltaZ, double speed, @Local ServerWorld serverWorld) {
        return 0;
    }

    @Shadow
    private int waitCountdown;
    @Final
    @Shadow
    private int lureLevel;
    @Inject(
            method = "tickFishingLogic",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/entity/projectile/FishingBobberEntity;waitCountdown:I",
                    opcode = 181, // Opcode for PUTFIELD (field assignment)
                    ordinal = 3,
                    shift = At.Shift.AFTER
            )
    )
    public void redirectWaitCountdownAssignment(BlockPos pos, CallbackInfo ci) {
        var wait = MathHelper.nextInt(self.random, 100, 600);
        var offset = 5;
        waitCountdown = wait * offset / (offset + lureLevel);
    }

    @Inject(method = "getPositionType(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/entity/projectile/FishingBobberEntity$PositionType;", at = @At("HEAD"), cancellable = true)
    private void getPositionType(BlockPos pos, CallbackInfoReturnable<FishingBobberEntity.PositionType> cir) {
        if (previousResult.allowed && previousResult.inVoid) cir.setReturnValue(FishingBobberEntity.PositionType.INSIDE_WATER);
    }

    @Redirect(method = "getPositionType(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/entity/projectile/FishingBobberEntity$PositionType;", at = @At(value = "INVOKE", target = "Lnet/minecraft/fluid/FluidState;isIn(Lnet/minecraft/tag/TagKey;)Z"))
    public boolean changeAllowedFluids2(FluidState instance, TagKey<Fluid> tag, @Local BlockPos pos) {
        var result = FishingUtils.changeAllowedFluids(self, pos);
        return result.inWater || result.inLava;
    }

    @Final
    @Shadow
    private int luckOfTheSeaLevel;
    @Redirect(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/loot/LootTable;generateLoot(Lnet/minecraft/loot/context/LootContext;)Lit/unimi/dsi/fastutil/objects/ObjectArrayList;"))
    public ObjectArrayList<ItemStack> generateLoot(LootTable instance, LootContext context, @Local LootContext.Builder builder) {
        var owner = self.getPlayerOwner();
        var result = FishingUtils.getFluidBelow(self.world, self.getBlockPos());
        if (result == FluidTags.LAVA && owner.getAttributeValue(PEntityAttributes.LAVA_FISHING) == 1) instance = self.world.getServer().getLootManager().getTable(PLootTables.LAVA_FISHING_GAMEPLAY);
        else if (result == null && owner.getAttributeValue(PEntityAttributes.VOID_FISHING) == 1) instance = self.world.getServer().getLootManager().getTable(PLootTables.VOID_FISHING_GAMEPLAY);

        var luck = (float) luckOfTheSeaLevel + owner.getLuck();
        var treasure = FishingUtils.isTreasure(self.random, luck);
        if (!treasure) return instance.generateLoot(context);

        var loot = new ObjectArrayList<ItemStack>();
        loot.add(FishingUtils.generateTreasure(self.random, self.getPlayerOwner().getAttributeValue(PEntityAttributes.TREASURE_QUALITY)));
        return loot;
    }
}
