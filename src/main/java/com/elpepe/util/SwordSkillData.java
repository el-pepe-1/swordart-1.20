package com.elpepe.util;

import com.elpepe.networking.ModMessages;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class SwordSkillData {
    private static final String LEVEL_KEY = "currentLvl";
    private static final String EXPERIENCE_KEY = "experience";

    private int level;
    private int experience;
    private Entity entity;

    private SwordSkillData() {}

    public void writeToNbt(NbtCompound nbt) {
        nbt.putInt(LEVEL_KEY, this.level);
        nbt.putInt(EXPERIENCE_KEY, this.experience);
    }

    public void setExperience(int experience) {
        this.experience = Math.max(experience, 0);
    }

    public void addExperience(int experience) {
        setExperience(this.experience + experience);
    }

    public void setLevel(int level) {
        this.level = Math.max(level, 0);
    }

    public void updateLevel() {
        if(this.experience >= getExperienceNeededToNextLvl()) {
            this.experience -= getExperienceNeededToNextLvl();
            this.level++;
            updateLevel(1);
        }
    }

    private void updateLevel(int iteration) {
        if(this.experience >= getExperienceNeededToNextLvl()) {
            this.experience -= getExperienceNeededToNextLvl();
            this.level++;
            updateLevel(iteration + 1);
        }
        else {
            sendOnNewSSLevelReachedPacket(iteration);
        }
    }

    public int getExperience() {
        return experience;
    }

    public int getLevel() {
        return level;
    }

    public int getExperienceNeededToNextLvl() {
        float multiplier = this.level >= 15 ? 5.5f : 10.0f;
        return Math.round(this.level * multiplier);
    }

    public static SwordSkillData fromNbt(NbtCompound nbt, Entity entity) {
        SwordSkillData swordSkillData = new SwordSkillData();

        swordSkillData.level = nbt.getInt(LEVEL_KEY);
        swordSkillData.experience = nbt.getInt(EXPERIENCE_KEY);
        swordSkillData.entity = entity;

        return swordSkillData;
    }

    public void sendOnNewSSLevelReachedPacket(int levelsDelta) {
        if(this.entity instanceof ServerPlayerEntity player) {
            NbtCompound nbt = new NbtCompound();
            this.writeToNbt(nbt);

            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeInt(this.level);
            buf.writeInt(levelsDelta);
            buf.writeNbt(nbt);

            ServerPlayNetworking.send(player, ModMessages.REACHED_NEW_SS_LEVEL, buf);
        }
    }
}
