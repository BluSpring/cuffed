package com.lazrproductions.cuffed.items;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.lazrproductions.cuffed.blocks.CellDoor;
import com.lazrproductions.cuffed.blocks.SafeBlock;
import com.lazrproductions.cuffed.blocks.entity.LockableBlockEntity;
import com.lazrproductions.cuffed.blocks.entity.SafeBlockEntity;
import com.lazrproductions.cuffed.component.CuffedDataComponents;
import com.lazrproductions.cuffed.component.KeyData;
import com.lazrproductions.cuffed.init.ModItems;
import org.jetbrains.annotations.NotNull;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public class KeyRingItem extends Item {
    public KeyRingItem(Properties p) {
        super(p.component(CuffedDataComponents.KEY_COUNT, 2));
    }

    @Override
    public InteractionResult useOn(@NotNull UseOnContext context) {
        if (context.getPlayer() == null)
            return InteractionResult.FAIL;

        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockState state = level.getBlockState(context.getClickedPos());
        if (player != null) {
            ItemStack stack = player.getItemInHand(context.getHand());
            if (!level.isClientSide && context.getHand() == InteractionHand.MAIN_HAND) {
                if (canBindLock(stack)) {
                    if (state.getBlock() instanceof CellDoor) {
                        BlockPos bottomPos = context.getClickedPos();
                        if (state.getValue(CellDoor.HALF) == DoubleBlockHalf.UPPER) {
                            bottomPos = bottomPos.below();
                            state = level.getBlockState(bottomPos);
                        }

                        if (level.getBlockEntity(bottomPos) instanceof LockableBlockEntity lockable) {
                            if(!lockable.hasBeenBound()) {
                                if (tryToAddBoundId(player, stack, lockable.getLockId(), "block.cuffed.cell_door")) {
                                    lockable.bind();
                                    player.awardStat(Stats.ITEM_USED.get(ModItems.KEY), 1);
                                    return InteractionResult.SUCCESS;
                                } else
                                    return InteractionResult.FAIL;
                            }
                        }
                    } else if (level.getBlockEntity(context.getClickedPos()) instanceof LockableBlockEntity lockable) {
                        if(!lockable.hasBeenBound()) {
                            if(tryToAddBoundId(player, stack, lockable.getLockId(), lockable.getLockName())) {
                                lockable.bind();
                                player.awardStat(Stats.ITEM_USED.get(ModItems.KEY_RING), 1);
                                return InteractionResult.SUCCESS;
                            }
                        }
                    }else if (state.getBlock() instanceof SafeBlock) {
                        if (level.getBlockEntity(context.getClickedPos()) instanceof SafeBlockEntity safe) {
                            if(!safe.hasBeenBound()) {
                                if(tryToAddBoundId(player, stack, safe.getLockId(), "block.cuffed.safe")) {
                                    safe.bind();
                                    player.awardStat(Stats.ITEM_USED.get(ModItems.KEY_RING), 1);
                                    return InteractionResult.SUCCESS;
                                }
                            }
                        }
                    }
                }
            }
        }

        return InteractionResult.FAIL;
    }

    public static void addBoundId(ItemStack stack, UUID id) {
        var set = new HashSet<>(stack.getOrDefault(CuffedDataComponents.BOUND_LOCKS, Set.of()));
        set.add(new KeyData(id));

        stack.set(CuffedDataComponents.BOUND_LOCKS, set);
    }

    public static void addKey(ItemStack stack, ItemStack key) {
        var set = new HashSet<>(stack.getOrDefault(CuffedDataComponents.BOUND_LOCKS, Set.of()));
        set.add(new KeyData(key.get(CuffedDataComponents.KEY), Optional.ofNullable(key.get(DataComponents.CUSTOM_NAME))));

        stack.set(CuffedDataComponents.BOUND_LOCKS, set);
    }

    public static boolean tryToAddBoundId(Player player, ItemStack stack, UUID id, String lockName) {
        if (canBindLock(stack)) {
            if (!hasBoundId(stack, id)) {
                addBoundId(stack, id);
                if (player.level().getGameRules().getBoolean(GameRules.RULE_REDUCEDDEBUGINFO))
                    player.displayClientMessage(
                            Component.translatable("item.cuffed.key.info.bound").append(Component.literal(""+id)), false);
                else
                    player.displayClientMessage(Component.translatable("item.cuffed.key.info.bound").append(Component.translatable(lockName)), false);
                player.playSound(SoundEvents.CHAIN_FALL, 1.0F, 1.0F);
                return true;
            }
        }
        
        return false;
    }

    public static void removeBoundId(ItemStack stack, UUID id) {
        var set = new HashSet<>(stack.getOrDefault(CuffedDataComponents.BOUND_LOCKS, Set.of()));
        set.removeIf(data -> data.id().equals(id));

        stack.set(CuffedDataComponents.BOUND_LOCKS, set);
    }

    public static boolean hasBoundId(ItemStack stack, UUID id) {
        Set<KeyData> set = stack.getOrDefault(CuffedDataComponents.BOUND_LOCKS, Set.of());

        for (KeyData data : set) {
            if (data.id().equals(id))
                return true;
        }

        return false;
    }

    public static boolean canBindLock(ItemStack stack) {
        Set<KeyData> set = stack.getOrDefault(CuffedDataComponents.BOUND_LOCKS, Set.of());
        if (set.isEmpty())
            return true;

        int bindings = set.size();
        int keys = stack.getOrDefault(CuffedDataComponents.KEY_COUNT, 2);

        return bindings < keys;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, TooltipContext context,
            @NotNull List<Component> pTooltipComponents,
            @NotNull TooltipFlag pIsAdvanced) {
        super.appendHoverText(stack, context, pTooltipComponents, pIsAdvanced);

        int amount = stack.getOrDefault(CuffedDataComponents.KEY_COUNT, 0);

        pTooltipComponents.add(Component.translatable("item.cuffed.key_ring.description.amount", amount)
                .withStyle(ChatFormatting.GRAY));

        int bindings = stack.getOrDefault(CuffedDataComponents.BOUND_LOCKS, Set.of()).size();
        if (bindings == amount)
            pTooltipComponents.add(Component.translatable("item.cuffed.key_ring.description.amount", bindings)
                    .withStyle(ChatFormatting.GRAY));
        else
            pTooltipComponents.add(Component.translatable("item.cuffed.key_ring.description.amount", bindings)
                    .withStyle(ChatFormatting.DARK_GRAY));
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int num,
            boolean boo) {
        if (!stack.has(CuffedDataComponents.KEY_COUNT))
            stack.set(CuffedDataComponents.KEY_COUNT, 1);
        super.inventoryTick(stack, level, entity, num, boo);
    }
}
