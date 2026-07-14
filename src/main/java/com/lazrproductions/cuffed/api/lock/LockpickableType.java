package com.lazrproductions.cuffed.api.lock;

import com.lazrproductions.cuffed.CuffedMod;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;

public record LockpickableType<T extends Lockpickable>(MapCodec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
    public static final Registry<LockpickableType<?>> REGISTRY = FabricRegistryBuilder.<LockpickableType<?>>createSimple(ResourceKey.createRegistryKey(CuffedMod.id("lockpickable_type"))).buildAndRegister();
}
