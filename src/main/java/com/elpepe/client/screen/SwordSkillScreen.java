package com.elpepe.client.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class SwordSkillScreen extends Screen {
    public final Screen previousScreen;

    public SwordSkillScreen(@Nullable Screen previousScreen) {
        super(Text.translatable("screen.swordart.sword_skill"));
        this.previousScreen = previousScreen;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
