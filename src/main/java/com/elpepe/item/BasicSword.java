package com.elpepe.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;

public class BasicSword extends Item {
    private final ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> attributes = ImmutableMultimap.builder();
    protected EquipmentSlot defaultEquipmentSlot = EquipmentSlot.MAINHAND;

    public BasicSword(Settings settings) {
        super(settings);
        addAttribute(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier("Attack Speed", -2.6, EntityAttributeModifier.Operation.ADDITION));
        addAttribute(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier("Attack Damage", 5, EntityAttributeModifier.Operation.ADDITION));
    }

    public BasicSword addAttribute(EntityAttribute attribute, EntityAttributeModifier modifier) {
        attributes.put(attribute, modifier);
        return this;
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return slot == this.defaultEquipmentSlot ? attributes.build() : super.getAttributeModifiers(slot);
    }
}
