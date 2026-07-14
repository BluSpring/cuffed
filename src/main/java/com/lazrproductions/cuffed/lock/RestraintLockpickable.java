package com.lazrproductions.cuffed.lock;

import com.lazrproductions.cuffed.api.CuffedAPI;
import com.lazrproductions.cuffed.api.lock.Lockpickable;
import com.lazrproductions.cuffed.api.lock.LockpickableType;
import com.lazrproductions.cuffed.cap.RestrainableCapability;
import com.lazrproductions.cuffed.init.ModItems;
import com.lazrproductions.cuffed.init.ModStatistics;
import com.lazrproductions.cuffed.restraints.base.RestraintType;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.UUID;

public record RestraintLockpickable(
    UUID restrained,
    RestraintType restraintType
) implements Lockpickable {
    public static final MapCodec<RestraintLockpickable> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            UUIDUtil.CODEC.fieldOf("restrained")
                .forGetter(RestraintLockpickable::restrained),
            RestraintType.CODEC.fieldOf("restraint_type")
                .forGetter(RestraintLockpickable::restraintType)
        )
            .apply(instance, RestraintLockpickable::new)
    );

    public static final StreamCodec<FriendlyByteBuf, RestraintLockpickable> STREAM_CODEC = StreamCodec.composite(
        UUIDUtil.STREAM_CODEC, RestraintLockpickable::restrained,
        RestraintType.STREAM_CODEC, RestraintLockpickable::restraintType,
        RestraintLockpickable::new
    );

    public static final LockpickableType<RestraintLockpickable> TYPE = new LockpickableType<>(CODEC, STREAM_CODEC);

    @Override
    public void finishLockpicking(ServerPlayer lockpicker, boolean wasFailed) {
        ServerPlayer restrained = lockpicker.getServer().getPlayerList().getPlayer(this.restrained());

        if (restrained != null) {
            Level level = lockpicker.level();
            if (level != null && !level.isClientSide()) {
                ItemStack itemstack = lockpicker.getItemInHand(InteractionHand.MAIN_HAND);
                lockpicker.getCooldowns().addCooldown(ModItems.LOCKPICK, 20);
                if (wasFailed) {
                    itemstack.hurtAndBreak(1, lockpicker, EquipmentSlot.MAINHAND);
                    lockpicker.awardStat(ModStatistics.LOCKPICKS_BROKEN);
                } else {
                    level.playLocalSound((float) restrained.position().x, (float) restrained.position().y,
                        (float) restrained.position().z, SoundEvents.CHAIN_BREAK, SoundSource.PLAYERS,
                        1, 1,
                        true);

                    itemstack.hurtAndBreak(1, lockpicker, EquipmentSlot.MAINHAND);

                    lockpicker.awardStat(ModStatistics.SUCCESSFUL_LOCKPICKS);
                    RestrainableCapability cap = (RestrainableCapability) CuffedAPI.Capabilities.getRestrainableCapability(restrained);
                    cap.TryUnequipRestraint(restrained, lockpicker, this.restraintType());
                }
            }
        }
    }

    @Override
    public LockpickableType<?> type() {
        return TYPE;
    }
}
