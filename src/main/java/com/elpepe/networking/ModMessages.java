package com.elpepe.networking;

import com.elpepe.Swordart;
import com.elpepe.networking.packet.ReachedNewLvlS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;

public class ModMessages {
    public static final Identifier REACHED_NEW_SS_LEVEL = new Identifier(Swordart.MOD_ID, "reached_new_ss_lvl");;

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(REACHED_NEW_SS_LEVEL, ReachedNewLvlS2CPacket::receive);
    }

    public static void registerC2SPackets() {

    }
}
