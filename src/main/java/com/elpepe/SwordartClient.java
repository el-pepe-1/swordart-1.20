package com.elpepe;

import com.elpepe.client.hud.SwordArtHud;
import com.elpepe.client.keybind.KeyInputHandler;
import com.elpepe.networking.ModMessages;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class SwordartClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModMessages.registerS2CPackets();
        KeyInputHandler.register();

        HudRenderCallback.EVENT.register(new SwordArtHud());
    }
}
