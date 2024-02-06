package com.elpepe.command;

import com.elpepe.networking.ModMessages;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class SwordArtCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("swordart")
                .then(CommandManager.literal("test").executes((context -> {
                    ServerPlayerEntity player = context.getSource().getPlayer();
                    ServerPlayNetworking.send(player, ModMessages.REACHED_NEW_SS_LEVEL, PacketByteBufs.create());

                    return 1;
                })))
        );
    }
}
