package com.elpepe.client.hud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import org.joml.Vector2i;
import oshi.util.tuples.Pair;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@Environment(EnvType.CLIENT)
public class HudAnimation {
    public final KeyframesHolder<DelayKeyframe, Integer> delayKeyframesHolder;
    public final KeyframesHolder<MoveKeyframe, Vector2i> moveKeyframesHolder;
    public final AnimatedObject animatedObject;

    public HudAnimation(List<MoveKeyframe> moveKeyframes, List<DelayKeyframe> delayKeyframes, AnimatedObject animatedObject) {
        this.animatedObject = animatedObject;
        this.moveKeyframesHolder = new KeyframesHolder<>(moveKeyframes, new Vector2i(0, 0));
        this.delayKeyframesHolder = new KeyframesHolder<>(delayKeyframes, 0);
    }

    public HudAnimation(List<MoveKeyframe> moveKeyframes, List<DelayKeyframe> delayKeyframes, AnimatedObject animatedObject, Vector2i initialPosition) {
        this.animatedObject = animatedObject;
        this.moveKeyframesHolder = new KeyframesHolder<>(moveKeyframes, initialPosition);
        this.delayKeyframesHolder = new KeyframesHolder<>(delayKeyframes, 0);
    }

    public KeyframesHolder<?, ?>[] getHolders() {
        return new KeyframesHolder[]{ this.delayKeyframesHolder, this.moveKeyframesHolder };
    }

    public static class KeyframesHolder<E extends Keyframe<T>, T> {
        private static final Predicate<Pair<List<?>, Integer>> DEFAULT_PREDICATE = (pair -> pair.getB() < pair.getA().size());

        public final List<E> keyframes;
        public T currentValue;
        private int currentKeyframe = 0;

        private Predicate<Pair<List<E>, Integer>> canStart;

        public KeyframesHolder(List<E> keyframes, T initialCurrentValue) {
            this.keyframes = Collections.unmodifiableList(keyframes);
            this.currentValue = initialCurrentValue;
        }

        public void setCanStartPredicate(Predicate<Pair<List<E>, Integer>> predicate) {
            if(canStart == null) {
                this.canStart = predicate;
            }
        }

        public boolean canStart() {
            if(canStart != null) {
                return DEFAULT_PREDICATE.test(new Pair<>(keyframes, currentKeyframe)) && canStart.test(new Pair<>(keyframes, currentKeyframe));
            }
            return DEFAULT_PREDICATE.test(new Pair<>(keyframes, currentKeyframe));
        }

        public void keyframeReached() {
            this.currentKeyframe++;
        }

        public int getCurrentKeyframe() {
            return this.currentKeyframe;
        }

        public void initializeCurrentFrame() {
            this.keyframes.get(this.currentKeyframe).initialize(this);
        }

        public boolean isCurrentFrameReached() {
            return this.keyframes.get(this.currentKeyframe).isReached(currentValue);
        }

        public T moveCurrentFrame() {
            T newValue = this.keyframes.get(this.currentKeyframe).move(currentValue);
            this.currentValue = newValue;
            return newValue;
        }
    }

    @FunctionalInterface
    public interface AnimatedObject {
        void render(DrawContext drawContext, float tickDelta, int x, int y);
    }

    public static class MoveKeyframe extends Keyframe<Vector2i> {
        public final Vector2i destination;
        private boolean initialized = false;
        private Vector2i needsToPass = new Vector2i(0, 0);
        public Vector2i passed = new Vector2i(0, 0);

        public MoveKeyframe(Vector2i destination, int speed) {
            super(speed);
            this.destination = destination;
        }

        public boolean isInitialized() {
            return this.initialized;
        }

        @Override
        public void initialize(KeyframesHolder<?, Vector2i> holder) {
            if(!isInitialized()) {
                Vector2i destinationCopy = new Vector2i(this.destination.x, this.destination.y);
                this.needsToPass = destinationCopy.sub(holder.currentValue).absolute();
                this.needsToPass.div(this.speed);

                this.initialized = true;
            }
        }

        public boolean isReached(Vector2i currentPosition) {
            if(this.passed.x >= this.needsToPass.x && this.passed.y >= this.needsToPass.y) {
                return true;
            }

            return false;
        }

        public Vector2i move(Vector2i currentPosition) {
            if(this.passed.x < this.needsToPass.x) {
                currentPosition.add(this.speed, 0);
                this.passed.add(new Vector2i(1, 0));
            }

            if(this.passed.y < this.needsToPass.y) {
                currentPosition.add(0, this.speed);
                this.passed.add(new Vector2i(0, 1));
            }

            return currentPosition;
        }
    }

    public static class DelayKeyframe extends Keyframe<Integer> {
        public final int duration;

        public DelayKeyframe(int duration) {
            super(1);
            this.duration = duration;
        }

        @Override
        public boolean isReached(Integer currentProgress) {
            return currentProgress >= duration;
        }

        @Override
        public Integer move(Integer currentProgress) {
            return ++currentProgress;
        }

        @Override
        public boolean shouldStopNextKeyframes() {
            return true;
        }
    }

    public static abstract class Keyframe<T> {
        public final int speed;
        public Keyframe(int speed) {
            this.speed = speed;
        }

        public boolean shouldStopNextKeyframes() { return false; }

        public void initialize(KeyframesHolder<?, T> holder) {}

        public abstract boolean isReached(T currentProgress);
        public abstract T move(T currentProgress);
    }
}
