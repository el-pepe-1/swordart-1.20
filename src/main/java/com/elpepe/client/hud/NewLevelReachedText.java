package com.elpepe.client.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import java.util.List;

public class NewLevelReachedText implements HudAnimation.AnimatedObject {
    //public static final HudAnimation textAnimation;

    @Override
    public void render(DrawContext drawContext, float tickDelta, int x, int y) {
        MinecraftClient client = MinecraftClient.getInstance();
        if(client != null) {
            drawContext.drawCenteredTextWithShadow(client.textRenderer, Text.translatable("message.swordart.new_ss_level_reached"), x, y, 0xFFFFFF);
        }
    }

    static {
        //textAnimation = new HudAnimation(List.of(new HudAnimation.Keyframe(120, 120)));
    }
}
