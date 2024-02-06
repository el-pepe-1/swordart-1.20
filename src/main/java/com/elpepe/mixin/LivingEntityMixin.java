package com.elpepe.mixin;

import com.elpepe.Swordart;
import com.elpepe.item.BasicSword;
import com.elpepe.util.IEntityDataSaver;
import com.elpepe.util.SwordSkillData;
import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "applyDamage", at = @At("TAIL"))
    private void onDamaged(DamageSource source, float amount, CallbackInfo ci) {
        Entity attacker = source.getAttacker();

        if(attacker instanceof ServerPlayerEntity player) {
            ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);
            LivingEntity thisEntity = (LivingEntity) (Object) this;
            if(thisEntity.getHealth() <= 0 && stack.getItem() instanceof BasicSword || stack.getItem() instanceof SwordItem) {
                IEntityDataSaver playerData = (IEntityDataSaver) player;
                SwordSkillData playerSkillData = SwordSkillData.fromNbt(playerData.getPersistentData(), player);

                playerSkillData.setExperience(playerSkillData.getExperience() + getExperienceBasedOnType(thisEntity.getType()));
                playerSkillData.writeToNbt(playerData.getPersistentData());
            }
        }
    }

    @Unique
    private int getExperienceBasedOnType(EntityType<?> type) {
        //TODO
        return 1;
    }
}
