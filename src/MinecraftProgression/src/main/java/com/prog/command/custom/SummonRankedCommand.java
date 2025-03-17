package com.prog.command.custom;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.prog.command.PSuggestionProviders;
import com.prog.command.argumentType.MobEntitySummonArgumentType;
import com.prog.entity.PComponents;
import com.prog.utils.LOGGER;
import net.minecraft.command.argument.*;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SummonRankedCommand {
    private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.summon.failed"));
    private static final SimpleCommandExceptionType FAILED_UUID_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.summon.failed.uuid"));
    private static final SimpleCommandExceptionType INVALID_POSITION_EXCEPTION = new SimpleCommandExceptionType(
            Text.translatable("commands.summon.invalidPosition")
    );

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("summon_ranked")
                        .requires(source -> source.hasPermissionLevel(2))
                        .then(
                                CommandManager.argument("mob", MobEntitySummonArgumentType.entitySummon())
                                        .suggests(PSuggestionProviders.SUMMONABLE_MOB_ENTITIES)
                                        .then(
                                                CommandManager.argument("rank", IntegerArgumentType.integer(0))
                                                        .executes(
                                                                context -> execute(
                                                                        context.getSource(), EntitySummonArgumentType.getEntitySummon(context, "mob"), IntegerArgumentType.getInteger(context, "rank"), context.getSource().getPosition(), new NbtCompound(), true
                                                                )
                                                        )
                                                        .then(
                                                                CommandManager.argument("pos", Vec3ArgumentType.vec3())
                                                                        .executes(
                                                                                context -> execute(
                                                                                        context.getSource(), EntitySummonArgumentType.getEntitySummon(context, "mob"), IntegerArgumentType.getInteger(context, "rank"), Vec3ArgumentType.getVec3(context, "pos"), new NbtCompound(), true
                                                                                )
                                                                        )
                                                                        .then(
                                                                                CommandManager.argument("nbt", NbtCompoundArgumentType.nbtCompound())
                                                                                        .executes(
                                                                                                context -> execute(
                                                                                                        context.getSource(),
                                                                                                        EntitySummonArgumentType.getEntitySummon(context, "mob"), IntegerArgumentType.getInteger(context, "rank"),
                                                                                                        Vec3ArgumentType.getVec3(context, "pos"),
                                                                                                        NbtCompoundArgumentType.getNbtCompound(context, "nbt"),
                                                                                                        false
                                                                                                )
                                                                                        )
                                                                        )
                                                        )
                                        )
                        )
        );
    }

    private static int execute(ServerCommandSource source, Identifier entity, int rank, Vec3d pos, NbtCompound nbt, boolean initialize) throws CommandSyntaxException {
        BlockPos blockPos = new BlockPos(pos);
        if (!World.isValid(blockPos)) {
            throw INVALID_POSITION_EXCEPTION.create();
        } else {
            NbtCompound nbtCompound = nbt.copy();
            nbtCompound.putString("id", entity.toString());
            ServerWorld serverWorld = source.getWorld();
            Entity entity2 = EntityType.loadEntityWithPassengers(nbtCompound, serverWorld, entityx -> {
                entityx.refreshPositionAndAngles(pos.x, pos.y, pos.z, entityx.getYaw(), entityx.getPitch());
                return entityx;
            });
            if (entity2 == null) {
                throw FAILED_EXCEPTION.create();
            } else {
                if (initialize && entity2 instanceof MobEntity mob) {
                    mob.initialize(source.getWorld(), source.getWorld().getLocalDifficulty(entity2.getBlockPos()), SpawnReason.COMMAND, null, null);
                    PComponents.SQUAD.get(mob).setForcedRank(rank);
                } else {
                    throw FAILED_EXCEPTION.create();
                }

                if (!serverWorld.spawnNewEntityAndPassengers(entity2)) {
                    throw FAILED_UUID_EXCEPTION.create();
                } else {
                    source.sendFeedback(Text.translatable("commands.summon.success", entity2.getDisplayName()), true);
                    return 1;
                }
            }
        }
    }
}
