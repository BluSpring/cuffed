package com.lazrproductions.cuffed.items;

import java.util.List;
import java.util.UUID;

import com.lazrproductions.cuffed.blocks.CellDoor;
import com.lazrproductions.cuffed.blocks.entity.LockableBlockEntity;
import com.lazrproductions.cuffed.blocks.entity.SafeBlockEntity;
import com.lazrproductions.cuffed.component.CuffedDataComponents;
import com.lazrproductions.cuffed.init.ModItems;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public class KeyItem extends Item {

    public KeyItem(Properties p) {
        super(p);
    }

    @Override
    public InteractionResult useOn(@NotNull UseOnContext context) {
        if (context.getPlayer() == null)
            return InteractionResult.FAIL;

        Level level = context.getLevel();
        if (!level.isClientSide && context.getHand() == InteractionHand.MAIN_HAND) {
            Player player = context.getPlayer();
            BlockState state = level.getBlockState(context.getClickedPos());
            if (player != null) {
                ItemStack stack = player.getItemInHand(context.getHand());
                if (state.getBlock() instanceof CellDoor) {
                    BlockPos bottomPos = context.getClickedPos();
                    if(state.getValue(CellDoor.HALF) == DoubleBlockHalf.UPPER) {
                        bottomPos = bottomPos.below();
                        state = level.getBlockState(bottomPos);
                    }

                    if (level.getBlockEntity(bottomPos) instanceof LockableBlockEntity lockable) {
                        if (!isBoundToALock(stack) && !lockable.hasBeenBound()) {
                            if (tryToSetBoundId(player, stack, lockable.getLockId(), "Cell Door")) {
                                lockable.bind();
                                player.awardStat(Stats.ITEM_USED.get(ModItems.KEY), 1);
                                return InteractionResult.SUCCESS;
                            } else
                                return InteractionResult.FAIL;
                        }
                    }
                } else if (level.getBlockEntity(context.getClickedPos()) instanceof LockableBlockEntity lockable) {
                    if (!isBoundToALock(stack) && !lockable.hasBeenBound()) {
                        if (tryToSetBoundId(player, stack, lockable.getLockId(), lockable.getLockName())) {
                            lockable.bind();
                            player.awardStat(Stats.ITEM_USED.get(ModItems.KEY), 1);
                            return InteractionResult.SUCCESS;
                        } else
                            return InteractionResult.FAIL;
                    }

                } else if (level.getBlockEntity(context.getClickedPos()) instanceof SafeBlockEntity safe) {
                    if (!isBoundToALock(stack) && !safe.hasBeenBound()) {
                        if (tryToSetBoundId(player, stack, safe.getLockId(), "block.cuffed.safe")) {
                            safe.bind();
                            player.awardStat(Stats.ITEM_USED.get(ModItems.KEY), 1);
                            return InteractionResult.SUCCESS;
                        } else
                            return InteractionResult.FAIL;
                    }

                }
            }
        }

        return InteractionResult.FAIL;
    }

    public static boolean tryToSetBoundId(Player player, ItemStack stack, UUID id, String lockName) {
        if (!stack.has(CuffedDataComponents.KEY)) {
            setBoundId(stack, id);
            if (!player.level().getGameRules().getBoolean(GameRules.RULE_REDUCEDDEBUGINFO))
                player.displayClientMessage(Component.translatable("item.cuffed.key.info.bound").append(Component.literal(""+id)), false);
            else
                player.displayClientMessage(Component.translatable("item.cuffed.key.info.bound").append(Component.translatable(lockName)), false);
            player.playSound(SoundEvents.CHAIN_FALL, 1.0F, 1.0F);
            return true;
        }
        return false;
    }

    public static void setBoundId(ItemStack stack, UUID id) {
        stack.set(CuffedDataComponents.KEY, id);
    }

    public static void removeBoundLock(ItemStack stack) {
        if (stack.has(CuffedDataComponents.KEY))
            stack.remove(CuffedDataComponents.KEY);
    }

    @Nullable
    public static UUID getBoundLock(ItemStack stack) {
        return stack.get(CuffedDataComponents.KEY);
    }

    public static boolean isBoundToLock(@NotNull ItemStack stack, @NotNull UUID id) {
        return id.equals(stack.get(CuffedDataComponents.KEY));
    }

    public static boolean isBoundToALock(@NotNull ItemStack stack) {
        return stack.has(CuffedDataComponents.KEY);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, TooltipContext context,
            @NotNull List<Component> pTooltipComponents,
            @NotNull TooltipFlag pIsAdvanced) {
        super.appendHoverText(stack, context, pTooltipComponents, pIsAdvanced);

        if (stack.has(CuffedDataComponents.KEY))
            pTooltipComponents.add(Component.translatable("item.cuffed.key.description.bound").withStyle(ChatFormatting.DARK_GRAY));
    }
}
