package com.prog.mixin.compat.ironjetpacks;

import com.blakebr0.ironjetpacks.config.ModConfigs;
import com.blakebr0.ironjetpacks.handler.JetpackClientHandler;
import com.blakebr0.ironjetpacks.item.JetpackItem;
import com.blakebr0.ironjetpacks.registry.Jetpack;
import com.blakebr0.ironjetpacks.sound.JetpackSound;
import com.prog.utils.JetpackUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.ParticlesMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(value = JetpackClientHandler.class, remap = false)
public class JetpackClientHandlerMixin {
    @Unique
    private static final Random RANDOM = Random.create();

    @Inject(method = "onClientTick", at = @At("HEAD"), cancellable = true)
    private static void injectOnClientTick(MinecraftClient client, CallbackInfo ci) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player != null && mc.world != null && !mc.isPaused()) {
            ItemStack stack = JetpackUtils.getJetpackItemStack(mc.player);
            if (stack != null && JetpackUtils.isFlying(mc.player)) {
                Item item = stack.getItem();
                if (ModConfigs.getClient().general.enableJetpackParticles && mc.options.getParticles().getValue() != ParticlesMode.MINIMAL) {
                    Jetpack jetpack = JetpackUtils.getJetpack(stack);
                    Vec3d playerPos = mc.player.getPos().add(0.0, 1.5, 0.0);
                    float random = (RANDOM.nextFloat() - 0.5F) * 0.1F;
                    double[] sneakBonus = mc.player.isSneaking() ? new double[]{-0.3, -0.1} : new double[]{0.0, 0.0};
                    Vec3d rotation = Vec3d.fromPolar(0.0F, mc.player.bodyYaw);
                    Vec3d vLeft = (new Vec3d(-0.18, -0.9 + sneakBonus[1], -0.3 + sneakBonus[0])).rotateX(0.0F).rotateY(mc.player.bodyYaw * -0.017453292F);
                    Vec3d vRight = (new Vec3d(0.18, -0.9 + sneakBonus[1], -0.3 + sneakBonus[0])).rotateX(0.0F).rotateY(mc.player.bodyYaw * -0.017453292F);
                    Vec3d v = playerPos.add(vLeft).add(mc.player.getVelocity().multiply(jetpack.speedSide));
                    mc.particleManager.addParticle(ParticleTypes.FLAME, v.x, v.y, v.z, (double) random, -0.2, (double) random);
                    mc.particleManager.addParticle(ParticleTypes.SMOKE, v.x, v.y, v.z, (double) random, -0.2, (double) random);
                    v = playerPos.add(vRight).add(mc.player.getVelocity().multiply(jetpack.speedSide));
                    mc.particleManager.addParticle(ParticleTypes.FLAME, v.x, v.y, v.z, (double) random, -0.2, (double) random);
                    mc.particleManager.addParticle(ParticleTypes.SMOKE, v.x, v.y, v.z, (double) random, -0.2, (double) random);
                }

                if (ModConfigs.getClient().general.enableJetpackSounds && !JetpackSound.playing(mc.player.getId())) {
                    mc.getSoundManager().play(new JetpackSound(mc.player, RANDOM));
                }
            }
        }

        ci.cancel(); // Overwrite
    }
}
