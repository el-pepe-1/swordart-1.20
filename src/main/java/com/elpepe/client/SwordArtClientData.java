package com.elpepe.client;

import com.elpepe.client.hud.HudAnimation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class SwordArtClientData {
    public static final List<HudAnimation> hudAnimations = new ArrayList<>();
}
