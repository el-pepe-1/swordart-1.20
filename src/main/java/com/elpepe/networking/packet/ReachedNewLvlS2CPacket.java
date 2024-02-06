package com.elpepe.networking.packet;

import com.elpepe.client.SwordArtClientData;
import com.elpepe.client.hud.HudAnimation;
import com.elpepe.client.hud.NewLevelReachedText;
import com.elpepe.util.SwordSkillData;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import org.joml.Vector2i;

import java.util.List;

public class ReachedNewLvlS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        if(client.player != null) {
            int lvl = buf.readInt();
            int lvlDelta = buf.readInt();
            NbtCompound nbt = buf.readNbt();
            SwordSkillData swordSkillData = SwordSkillData.fromNbt(nbt, client.player);

            SwordArtClientData.hudAnimations.add(NewLevelReachedText.getDefaultTextAnimation(lvlDelta));
        }
    }
}
