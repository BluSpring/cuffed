package com.lazrproductions.cuffed.items;

import com.lazrproductions.cuffed.effect.WoundedEffect;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class KnifeItem extends Item {
    public KnifeItem(Properties p) {
        super(p);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if(target instanceof LivingEntity living) {
            WoundedEffect.woundEntity(living, 20);
        }

        return super.hurtEnemy(stack, target, attacker);
    }
}
