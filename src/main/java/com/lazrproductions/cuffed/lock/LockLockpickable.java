package com.lazrproductions.cuffed.lock;

import com.lazrproductions.cuffed.api.lock.Lockpickable;
import com.lazrproductions.cuffed.api.lock.LockpickableType;
import com.lazrproductions.cuffed.entity.PadlockEntity;
import com.lazrproductions.cuffed.init.ModItems;
import com.lazrproductions.cuffed.init.ModStatistics;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public record LockLockpickable(int lockId) implements Lockpickable {
    public static final MapCodec<LockLockpickable> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            Codec.INT.fieldOf("lock_id")
                .forGetter(LockLockpickable::lockId)
        )
            .apply(instance, LockLockpickable::new)
    );

    public static final StreamCodec<FriendlyByteBuf, LockLockpickable> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_INT, LockLockpickable::lockId,
        LockLockpickable::new
    );

    public static final LockpickableType<LockLockpickable> TYPE = new LockpickableType<>(CODEC, STREAM_CODEC);

    @Override
    public void finishLockpicking(ServerPlayer player, boolean wasFailed) {
        Level level = player.level();

        if (level != null) {
            if (!level.isClientSide()) {
                ItemStack itemstack = player.getItemInHand(InteractionHand.MAIN_HAND);
                player.getCooldowns().addCooldown(ModItems.LOCKPICK, 20);
                if (wasFailed) {
                    itemstack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);

                    player.awardStat(ModStatistics.LOCKPICKS_BROKEN);
                } else {
                    level.playLocalSound((float) player.position().x, (float) player.position().y,
                        (float) player.position().z, SoundEvents.CHAIN_BREAK, SoundSource.PLAYERS,
                        1, 1,
                        true);

                    itemstack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
                    if (level.getEntity(lockId) instanceof PadlockEntity e) {
                        player.awardStat(ModStatistics.SUCCESSFUL_LOCKPICKS);
                        e.RemoveLock();
                    }
                }
            }
        }
    }

    @Override
    public LockpickableType<?> type() {
        return TYPE;
    }
}
