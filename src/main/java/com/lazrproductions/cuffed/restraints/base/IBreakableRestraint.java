package com.lazrproductions.cuffed.restraints.base;

import net.minecraft.client.Options;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;

public interface IBreakableRestraint {
    SoundEvent getBreakSound();
    int getMaxDurability();
    boolean isKeyToAttemptBreak(int keyCode, Options options);
    boolean requireAlternateKeysToAttemptBreak();
    boolean dropItemOnBroken();
    boolean canBeBrokenOutOf();

    int getDurability();

    void attemptToBreak(Player player, int keyCode, int action, Options options);
    void setDurability(ServerPlayer player, int value);
    void incrementDurability(ServerPlayer player, int value);

    void onBrokenServer(ServerPlayer player);
    void onBrokenClient(Player player);
}
