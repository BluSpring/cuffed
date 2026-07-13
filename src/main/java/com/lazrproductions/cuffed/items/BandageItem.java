package com.lazrproductions.cuffed.items;

import com.lazrproductions.cuffed.effect.WoundedEffect;
import com.lazrproductions.cuffed.init.ModEffects;
import org.jetbrains.annotations.NotNull;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BandageItem extends Item {

    public BandageItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if(player.isCrouching()) {
            if(player.hasEffect(ModEffects.WOUNDED_EFFECT)) {
                WoundedEffect.treatEntity(player);
                player.getItemInHand(hand).shrink(1);
                return InteractionResultHolder.consume(player.getItemInHand(hand));
            }
        }
        return super.use(level, player, hand);
    }

    @Override
    public InteractionResult interactLivingEntity(@NotNull ItemStack stack, @NotNull Player player,
            @NotNull LivingEntity entity,
            @NotNull InteractionHand hand) {
        if(entity.hasEffect(ModEffects.WOUNDED_EFFECT)) {
            WoundedEffect.treatEntity(entity);
            stack.shrink(1);
            return InteractionResult.SUCCESS;
        }
        
        return super.interactLivingEntity(stack, player, entity, hand);
    }
}
