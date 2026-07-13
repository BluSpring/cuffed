package com.lazrproductions.cuffed.items;

import java.util.List;

import com.lazrproductions.cuffed.api.CuffedAPI;
import com.lazrproductions.cuffed.cap.RestrainableCapability;
import com.lazrproductions.cuffed.restraints.base.RestraintType;
import org.jetbrains.annotations.NotNull;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class CreativeRestraintCutter extends Item {
    public CreativeRestraintCutter(Properties p) {
        super(p);
    }

    @Override
    public InteractionResult interactLivingEntity(@NotNull ItemStack stack, @NotNull Player player,
                                                  @NotNull LivingEntity entity, @NotNull InteractionHand hand) {
        if(!player.level().isClientSide()) {
            if (entity instanceof Player other) {
                RestrainableCapability cap = (RestrainableCapability)CuffedAPI.Capabilities.getRestrainableCapability(other);
                if(cap != null && cap.isRestrained()) {
                    cap.TryUnequipRestraint((ServerPlayer)other, (ServerPlayer)player, RestraintType.Head);
                    cap.TryUnequipRestraint((ServerPlayer)other, (ServerPlayer)player, RestraintType.Arm);
                    cap.TryUnequipRestraint((ServerPlayer)other, (ServerPlayer)player, RestraintType.Leg);
                    return InteractionResult.sidedSuccess(player.level().isClientSide());
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> lore, TooltipFlag tooltipFlag) {
        lore.add(Component.translatable(getDescriptionId()+".lore").withStyle(ChatFormatting.GRAY));
    }
}
