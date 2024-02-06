package com.elpepe.client.hud;

import com.elpepe.Swordart;
import com.elpepe.client.SwordArtClientData;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawContext;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.List;

public class SwordArtHud implements HudRenderCallback {
    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        if(!SwordArtClientData.hudAnimations.isEmpty()) {
            List<Integer> endedAnimations = new ArrayList<>();

            for(int i = 0; i < SwordArtClientData.hudAnimations.size(); i++) {
                HudAnimation hudAnimation = SwordArtClientData.hudAnimations.get(i);
                int skippedHolders = 0;
                for(int j = 0; j < hudAnimation.getHolders().length; j++) {
                    HudAnimation.KeyframesHolder<?, ?> holder = hudAnimation.getHolders()[j];
                    if(!holder.canStart()) {
                        skippedHolders++;
                        continue;
                    }

                    holder.initializeCurrentFrame();

                    if(holder.isCurrentFrameReached()) {
                        holder.keyframeReached();
                        Swordart.LOGGER.info("Keyframe reached!");
                        if(skippedHolders >= hudAnimation.getHolders().length) {
                            Swordart.LOGGER.info("Animation ended!");
                            endedAnimations.add(i);
                        }
                        hudAnimation.animatedObject.render(drawContext, tickDelta, hudAnimation.moveKeyframesHolder.currentValue.x, hudAnimation.moveKeyframesHolder.currentValue.y);
                    }
                    else {
                        holder.moveCurrentFrame();
                        Swordart.LOGGER.info("New pos is: " + holder.currentValue);

                        hudAnimation.animatedObject.render(drawContext, tickDelta, hudAnimation.moveKeyframesHolder.currentValue.x, hudAnimation.moveKeyframesHolder.currentValue.y);
                        if(holder.keyframes.get(holder.getCurrentKeyframe()).shouldStopNextKeyframes()) {
                            break;
                        }
                    }
                }
            }

            if(!endedAnimations.isEmpty()) {
                for(int i: endedAnimations) {
                    SwordArtClientData.hudAnimations.remove(i);
                }
            }
        }
    }
}