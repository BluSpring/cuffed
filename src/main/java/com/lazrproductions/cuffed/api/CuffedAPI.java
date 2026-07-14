package com.lazrproductions.cuffed.api;

import com.lazrproductions.cuffed.CuffedMod;
import com.lazrproductions.cuffed.api.lock.Lockpickable;
import com.lazrproductions.cuffed.blocks.PilloryBlock;
import com.lazrproductions.cuffed.blocks.base.ILockableBlock;
import com.lazrproductions.cuffed.cap.RestrainableCapability;
import com.lazrproductions.cuffed.cap.base.IRestrainableCapability;
import com.lazrproductions.cuffed.client.gui.screen.LockpickingScreen;
import com.lazrproductions.cuffed.entity.PadlockEntity;
import com.lazrproductions.cuffed.init.ModTags;
import com.lazrproductions.cuffed.lock.CellDoorLockpickable;
import com.lazrproductions.cuffed.lock.LockLockpickable;
import com.lazrproductions.cuffed.lock.RestraintLockpickable;
import com.lazrproductions.cuffed.network.packet.RestraintEquippedPacket;
import com.lazrproductions.cuffed.network.packet.RestraintSyncPacket;
import com.lazrproductions.cuffed.network.packet.RestraintUtilityPacket;
import com.lazrproductions.cuffed.network.packet.clientbound.ClientLockpickLockPacket;
import com.lazrproductions.cuffed.network.packet.serverbound.ServerLockpickLockPacket;
import com.lazrproductions.cuffed.restraints.base.AbstractRestraint;
import com.lazrproductions.cuffed.restraints.base.RestraintType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.UUID;

public class CuffedAPI {
    public static class Networking {
        public static void sendRestraintSyncPacket(@NotNull ServerPlayer client) {
            IRestrainableCapability cap = Capabilities.getRestrainableCapability(client);
            RestraintSyncPacket packet = new RestraintSyncPacket(client.getId(), client.getUUID().toString(),
                    cap.serializeNBT());
            NETWORK.sendTo(packet, client); // sends the sync packet to the client we want.
        }
        

        public static void sendRestraintEquipPacket(@NotNull ServerPlayer client, @Nullable ServerPlayer captor,
                RestraintType type, @Nullable AbstractRestraint newRestraint,
                @Nullable AbstractRestraint oldRestraint) {
            RestraintEquippedPacket packet = new RestraintEquippedPacket(client.getId(), client.getUUID().toString(),
                    type, oldRestraint != null ? oldRestraint.serializeNBT() : null,
                    newRestraint != null ? newRestraint.serializeNBT() : null,
                    captor != null ? captor.getUUID().toString() : "null");
            NETWORK.sendTo(packet, client); // sends the sync packet to the client we want.
        }


        public static void sendRestraintUtilityPacketToClient(ServerPlayer client, RestraintType restraintType,
                int utiltiyCode, int integerArg, boolean booleanArg, double doubleArg, String stringArg) {
            RestraintUtilityPacket packet = new RestraintUtilityPacket(restraintType.toInteger(),
                    utiltiyCode, integerArg, booleanArg, doubleArg, stringArg);
            NETWORK.sendTo(packet, client);
        }
        public static void sendRestraintUtilityPacketToServer(RestraintType restraintType, int utiltiyCode,
                int integerArg, boolean booleanArg, double doubleArg, String stringArg) {
            RestraintUtilityPacket packet = new RestraintUtilityPacket(restraintType.toInteger(),
                    utiltiyCode, integerArg, booleanArg, doubleArg, stringArg);
            NETWORK.sendToServer(packet);
        }

        
        public static void sendLockpickFinishPickingLockPacketToServer(boolean wasFailed, int lockId) {
            ClientPlayNetworking.send(new ServerLockpickLockPacket(wasFailed, new LockLockpickable(lockId)));
        }
        public static void sendLockpickFinishPickingRestraintPacketToServer(boolean wasFailed, UUID restrained, RestraintType restraintType) {
            ClientPlayNetworking.send(new ServerLockpickLockPacket(wasFailed, new RestraintLockpickable(restrained, restraintType)));
        }
        public static void sendLockpickFinishPickingCellDoorPacketToServer(boolean wasFailed, BlockPos pos) {
            ClientPlayNetworking.send(new ServerLockpickLockPacket(wasFailed, new CellDoorLockpickable(pos)));
        }


        public static void sendLockpickBeginPickingLockPacketToClient(@NotNull ServerPlayer player, int lockId, int speedIncreasePerPhase, int progressPerPick) {
            ServerPlayNetworking.send(player, new ClientLockpickLockPacket(speedIncreasePerPhase, progressPerPick, new LockLockpickable(lockId)));
        }
        public static void sendLockpickBeginPickingRestraintPacketToClient(@NotNull ServerPlayer player, UUID restrained, RestraintType restraintType, int speedIncreasePerPhase, int progressPerPick) {
            ServerPlayNetworking.send(player, new ClientLockpickLockPacket(speedIncreasePerPhase, progressPerPick, new RestraintLockpickable(restrained, restraintType)));
        }
        public static void sendLockpickBeginPickingCellDoorPacketToClient(@NotNull ServerPlayer player, BlockPos pos, int speedIncreasePerPhase, int progressPerPick) {
            ServerPlayNetworking.send(player, new ClientLockpickLockPacket(speedIncreasePerPhase, progressPerPick, new CellDoorLockpickable(pos)));
        }


        public static void registerPackets() {
            PayloadTypeRegistry.playC2S().register(ServerLockpickLockPacket.TYPE, ServerLockpickLockPacket.CODEC);
            PayloadTypeRegistry.playS2C().register(ClientLockpickLockPacket.TYPE, ClientLockpickLockPacket.CODEC);

            NETWORK.registerPacket(RestraintSyncPacket.class, RestraintSyncPacket::new);
            NETWORK.registerPacket(RestraintEquippedPacket.class, RestraintEquippedPacket::new);
            NETWORK.registerPacket(RestraintUtilityPacket.class, RestraintUtilityPacket::new);
        }
    }

    public static class Lockpicking {
        @Environment(EnvType.CLIENT)
        public static void beginLockpicking(@NotNull Minecraft instance, int speedIncreasePerPhase, int progressPerPick, Lockpickable lockpickable) {
            LockpickingScreen overlay = new LockpickingScreen(instance);
            overlay.speedIncreasePerPhase = speedIncreasePerPhase;
            overlay.progressPerPick = progressPerPick;
            
            overlay.lockpickable = lockpickable;
            
            instance.setScreen(overlay);
        }
    
        public static boolean isLockedAt(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos) {
            if (state.is(ModTags.Blocks.LOCKABLE_BLOCKS)) {
                PadlockEntity padlock = PadlockEntity.getLockAt(level, pos);
                if (padlock != null && padlock.isLocked())
                    return true;

                if (state.getBlock() instanceof ILockableBlock)
                    if (ILockableBlock.isLocked(state))
                    return true;
                
                if (state.getBlock() instanceof DoorBlock door) {
                    PadlockEntity eB = PadlockEntity.getLockAt(level, pos.below());
                    PadlockEntity eA = PadlockEntity.getLockAt(level, pos.above());
                    if (level.getBlockState(pos.below()).is(door) && eB != null && eB.isLocked())
                        return true;
                    else if (level.getBlockState(pos.above()).is(door) && eA != null && eA.isLocked())
                        return true;
                }

                if (state.getBlock() instanceof PilloryBlock pillory) {
                    PadlockEntity eB = PadlockEntity.getLockAt(level, pos.below());
                    PadlockEntity eA = PadlockEntity.getLockAt(level, pos.above());
                    if (level.getBlockState(pos.below()).is(pillory) && eB != null && eB.isLocked())
                        return true;
                    else if (level.getBlockState(pos.above()).is(pillory) && eA != null && eA.isLocked())
                        return true;
                }
                
                if (state.getBlock() instanceof ChestBlock chest) {
                    if(state.getValue(ChestBlock.TYPE) == ChestType.SINGLE) return false;
                    
                    Direction dir = ChestBlock.getConnectedDirection(state);
                    BlockPos otherPos = pos.relative(dir);
                    PadlockEntity otherPadlock = PadlockEntity.getLockAt(level, otherPos);
                    return level.getBlockState(otherPos).is(net.minecraft.world.level.block.Blocks.CHEST)
                        && otherPadlock != null && otherPadlock.isLocked();
                } 
            }

            return false;
        }
    
        public static final HashMap<BlockPos, UUID> registeredLocks = new HashMap<>();

        public static void loadWorld() {
            registeredLocks.clear();

            // Load keys from world files
        }

        public static void saveWorld() {

        }
    }

    public static class Capabilities {
        public static final ResourceLocation RESTRAINABLE_CAPABILITY_NAME = ResourceLocation.fromNamespaceAndPath(CuffedMod.MODID,
                "restrainable");
        public static final Capability<RestrainableCapability> RESTRAINABLE_CAPABILITY = CapabilityManager
                .get(new CapabilityToken<RestrainableCapability>() {
                });

        public static IRestrainableCapability getRestrainableCapability(Player player) {
            return player.getCapability(Capabilities.RESTRAINABLE_CAPABILITY).orElseGet(RestrainableCapability::new);
        }
    }

    public static class Privacy {
        // TODO: next update's problem
        // public static boolean attemptToAnchor(@NotNull ServerPlayer player, @NotNull ServerPlayer playerAnchoring) {
        //     if(playerAnchoring.hasPermissions(1))
        //         return true; // allow moderators and higher to bypass privacy restrictions
            
        //     IPrivacyOperand priv = (IPrivacyOperand)player;
        //     IRestrainableCapability cap = Capabilities.getRestrainableCapability(player);
        //     switch (priv.getAnchoringRestrictions()) {
        //         case NEVER:
        //             return true;
        //         case ONLY_WHEN_RESTRAINED:
        //             return cap.armsOrLegsRestrained();
        //         case ASK:
        //             // ask for permission
        //             return false;
        //         case ALWAYS:
        //             return false;
        //     }

        //     return false;
        // }


        // public static void askPlayerForPermission(@NotNull ServerPlayer player) {

        // }
    }
}
