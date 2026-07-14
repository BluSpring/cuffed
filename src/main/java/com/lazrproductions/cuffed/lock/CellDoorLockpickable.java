package com.lazrproductions.cuffed.lock;

import com.lazrproductions.cuffed.api.lock.Lockpickable;
import com.lazrproductions.cuffed.api.lock.LockpickableType;
import com.lazrproductions.cuffed.init.ModItems;
import com.lazrproductions.cuffed.init.ModStatistics;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public record CellDoorLockpickable(BlockPos pos) implements Lockpickable {
    public static final MapCodec<CellDoorLockpickable> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            BlockPos.CODEC.fieldOf("pos")
                .forGetter(CellDoorLockpickable::pos)
        )
            .apply(instance, CellDoorLockpickable::new)
    );

    public static final StreamCodec<FriendlyByteBuf, CellDoorLockpickable> STREAM_CODEC = StreamCodec.composite(
        BlockPos.STREAM_CODEC, CellDoorLockpickable::pos,
        CellDoorLockpickable::new
    );

    public static final LockpickableType<CellDoorLockpickable> TYPE = new LockpickableType<>(CODEC, STREAM_CODEC);

    @Override
    public LockpickableType<?> type() {
        return TYPE;
    }

    @Override
    public void finishLockpicking(ServerPlayer lockpicker, boolean wasFailed) {
        Level level = lockpicker.level();
        if (level != null) {
            if (!level.isClientSide()) {
                ItemStack itemstack = lockpicker.getItemInHand(InteractionHand.MAIN_HAND);
                lockpicker.getCooldowns().addCooldown(ModItems.LOCKPICK, 20);
                if (wasFailed) {
                    itemstack.hurtAndBreak(1, lockpicker, EquipmentSlot.MAINHAND);

                    lockpicker.awardStat(ModStatistics.LOCKPICKS_BROKEN);
                } else {
                    level.playLocalSound((float) lockpicker.position().x, (float) lockpicker.position().y,
                        (float) lockpicker.position().z, SoundEvents.CHAIN_BREAK, SoundSource.PLAYERS,
                        1, 1,
                        true);

                    lockpicker.awardStat(ModStatistics.SUCCESSFUL_LOCKPICKS);

                    itemstack.hurtAndBreak(1, lockpicker, EquipmentSlot.MAINHAND);

                    level.destroyBlock(this.pos(), true);
                }
            }
        }
    }
}
