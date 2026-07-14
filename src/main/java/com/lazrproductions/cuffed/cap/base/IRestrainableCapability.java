package com.lazrproductions.cuffed.cap.base;

import com.lazrproductions.cuffed.restraints.base.*;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IRestrainableCapability {

    //#region Server-Side operations

    /**
     * Copy data from another capability to this one, the given capabilit must be the same restraintType to copy it's data.
     * @param cap The other capability to copy from
     */
    void copyFrom(CompoundTag tag, ServerLevel level);

    CompoundTag serializeNBT();
    void deserializeNBT(CompoundTag nbt);

    /**
    * Called every tick on the server
    * @param player The player that is getting ticked
    */
    void tickServer(ServerPlayer player);

    //#endregion

    //#region Client-Side operations

    /**
    * Called every tick only on the local client
    * @param player The client that is getting ticked
    */
    void tickClient(Player player);

    /**
     * Called on the client when any key is pressed, released, or held.
     * @param player The player who performed the action
     * @param keyCode The key that was pressed, released, or held.
     * @param action The action code cooresponding to whether the button was pressed, released, or held. (1 -> pressed, 0 -> released, 2 -> held.)
     */
    void onKeyInput(Player player, int keyCode, int action);
    /**
     * Called on the client when any mouse button is pressed, released, or held.
     * @param player The player who performed the action
     * @param keyCode The mouse button that was pressed, released, or held.
     * @param action The action code cooresponding to whether the button was pressed, released, or held. (1 -> pressed, 0 -> released, 2 -> held.)
     */
    void onMouseInput(Player player, int keyCode, int action);


    /**
    * Render any overlay this hero may have onto the screen.
    */
    void renderOverlay(Player player, GuiGraphics graphics, float partialTick, Window window);

    //#endregion

    //#region Restraint Management

    boolean restraintsDisabledBreakingBlocks();
    boolean restraintsDisabledItemUse();
    boolean restraintsDisabledMovement();
    boolean restraintsDisabledJumping();
    int encodeRestraintDisabilities();

    /** Get whether or not this player's head is restrained. */
    boolean headRestrained();
    /** Get whether or not this player's arms are restrained. */
    boolean armsRestrained();
    /** Get whether or not this player's legs are restrained. */
    boolean legsRestrained();
    /** Get whether or not this player is restrained in any way. */
    boolean isRestrained();
    /** Get whether or not this player is restrained with the given restraintType. */
    boolean isRestrained(RestraintType type);
    /** Get the id of this player's head restraint. */
    ResourceLocation getHeadRestraintId();
    /** Get the id of this player's arm restraint. */
    ResourceLocation getArmRestraintId();
    /** Get the id of this player's leg restraint. */
    ResourceLocation getLegRestraintId();
    /** Get the id of this player's restraint with the given restraintType. */
    ResourceLocation getRestraintId(RestraintType type);
    /** Get this player's head restraint */
    @Nullable
    AbstractHeadRestraint getHeadRestraint();
    /** Get this player's arm restraint. */
    @Nullable
    AbstractArmRestraint getArmRestraint();
    /** Get this player's leg restraint */
    @Nullable
    AbstractLegRestraint getLegRestraint();
    /** Get this player's restraint with the given restraintType */
    @Nullable
    AbstractRestraint getRestraint(RestraintType type);
    /** Set this player's leg restraint without sending any events. */
    void setHeadRestraintWithoutWarning(@NotNull ServerPlayer player, @Nullable AbstractHeadRestraint newValue);
    /** Set this player's arm restraint without sending any events. */
    void setArmRestraintWithoutWarning(@NotNull ServerPlayer player, @Nullable AbstractArmRestraint newValue);
    /** Set this player's leg restraint without sending any events. */
    void setLegRestraintWithoutWarning(@NotNull ServerPlayer player, @Nullable AbstractLegRestraint newValue);
    /** Set this player's restraint with the given restraintType without sending any events. */
    void setRestraintWithoutWarning(@NotNull ServerPlayer player, @NotNull AbstractRestraint newValue, RestraintType type);

    //#endregion

    //#region Escorting

    /**
     * Start escorting another player, and notify them that they are being escorted.
     */
    void startEscortingPlayer(@NotNull ServerPlayer self, @NotNull ServerPlayer other);
    /**
     * Notify this player that they have started to be escorted..
     */
    void startGettingEscortedByPlayer(@NotNull ServerPlayer other);

    /**
     * Stop escorting who you were, and notify them that they are no longer being escorted.
     */
    void stopEscortingPlayer();
    /**
     * Notify this player that they are no longer being escorted.
     */
    void stopGettingEscortedByPlayer();

    /**
     * Get the player who this player is escorting.
     */
    ServerPlayer getWhoImEscorting();
    /**
     * Get the player who is escorting this player.
     */
    ServerPlayer getMyEscort();
    
    //#endregion

    //#region Event Handlers

    boolean onInteractedByOther(ServerPlayer player, ServerPlayer other, double interactionHeight, ItemStack stack, InteractionHand hand, boolean avoidEscort);

    /** Called on the server when this player has died. */
    void onDeathServer(ServerPlayer player);
    /** Called on the client when this player has died. */
    void onDeathClient(Player player);

    /** Called on the server when this player logs in. */
    void onLoginServer(ServerPlayer player);
    /** Called on the client when this player logs in. */
    void onLoginClient(Player player);

    /** Called on the server when this player logs out. */
    void onLogoutServer(ServerPlayer player);
    /** Called on the client when this player logs out. */
    void onLogoutClient(Player player);

    /**
     * Called on the server when this player lands on the ground.
     * @param player The player who is landing.
     * @param damageToTake How much fall damage this player will take.
     * @return The modified amount of damage to take, will replace the original value and the player will instead take this amount of damage.
     */
    float onLandServer(ServerPlayer player, float distance, float damageMultiplier);
    /**
     * Called on the client when this player lands on the ground.
     * @param player The player who is landing.
     * @param damageToTake How much fall damage this player will take.
     */
    void onLandClient(Player player, float distance, float damageMultiplier);

    /** Called on the server when this player jumps. */
    void onJumpServer(ServerPlayer player);
    /** Called on the client when this player jumps. */
    void onJumpClient(Player player);

    boolean onTickRideServer(ServerPlayer player, Entity vehicle);
    boolean onTickRideClient(Player player, Entity vehicle);

    //#endregion
}
