package com.elpepe.client.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import org.joml.Vector2i;

import java.util.List;

public class NewLevelReachedText implements HudAnimation.AnimatedObject {
    public final int levelsReached;


    public NewLevelReachedText(int levelsReached) {
        this.levelsReached = levelsReached;
    }

    @Override
    public void render(DrawContext drawContext, float tickDelta, int x, int y) {
        MinecraftClient client = MinecraftClient.getInstance();
        if(client != null) {
            drawContext.drawCenteredTextWithShadow(client.textRenderer, Text.translatable("message.swordart.new_ss_level_reached", levelsReached), x, y, 0xFFFFFF);
        }
    }

    public static HudAnimation getDefaultTextAnimation(int levelsReached) {
        return new HudAnimation(
                List.of(new HudAnimation.MoveKeyframe(new Vector2i(120, 120), 1)),
                List.of(new HudAnimation.DelayKeyframe(40)),
                new NewLevelReachedText(levelsReached),
                new Vector2i(120, 0)
        );
    }
}
