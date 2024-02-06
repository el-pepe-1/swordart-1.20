package com.elpepe.client.keybind;

import com.elpepe.client.screen.SwordSkillScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_SWORDART = "key.category.swordart";
    public static final String KEY_OPEN_SWORD_SKILL_SCREEN = "key.sworart.open_sword_skill_screen";

    public static KeyBinding openScreenKey;

    private static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(openScreenKey.wasPressed()) {
                if(client.currentScreen instanceof SwordSkillScreen screen) {
                    client.setScreen(screen.previousScreen);
                }
                else {
                    client.setScreen(new SwordSkillScreen(client.currentScreen));
                }
            }
        });
    }

    public static void register() {
        openScreenKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_OPEN_SWORD_SKILL_SCREEN,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                KEY_CATEGORY_SWORDART
        ));

        registerKeyInputs();
    }
}
