package com.prog.network;

import com.prog.Prog;
import com.prog.entity.PComponents;
import com.prog.utils.LOGGER;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class PNetwork {
    public static Identifier PACKET_ID = Identifier.of(Prog.MOD_ID, "network");
    public static int counter = 0;
    public static int TOGGLE_HEADLIGHT = register();
    public static int TOGGLE_STEP_ASSIST = register();
    public static int TOGGLE_BAD_OMEN_IMMUNITY = register();
    public static int TOGGLE_MAGNET = register();

    static {
        ServerPlayNetworking.registerGlobalReceiver(PACKET_ID, (server, player, handler, buf, responseSender) -> {
            int id = buf.readInt();
            var pComponent = PComponents.PLAYER.get(player);
            var sync = false;
            if (id == TOGGLE_HEADLIGHT) {
                sync = true;
                pComponent.headlightDisabled = !pComponent.headlightDisabled;
            } else if (id == TOGGLE_STEP_ASSIST) {
                sync = true;
                pComponent.stepAssistDisabled = !pComponent.stepAssistDisabled;
            } else if (id == TOGGLE_BAD_OMEN_IMMUNITY) {
                sync = true;
                pComponent.badOmenImmunityDisabled = !pComponent.badOmenImmunityDisabled;
            } else if (id == TOGGLE_MAGNET) {
                sync = true;
                pComponent.magnetDisabled = !pComponent.magnetDisabled;
            }

            if (sync) PComponents.PLAYER.sync(player);
        });
    }

    @Environment(EnvType.CLIENT)
    public static void sendToServer(int message) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeInt(message);
        ClientPlayNetworking.send(PACKET_ID, buf);
    }

    public static int register() {
        counter++;
        return counter - 1;
    }

    public static void init() {
        LOGGER.info("Registering Network Receiver for: " + Prog.MOD_ID);
    }
}
