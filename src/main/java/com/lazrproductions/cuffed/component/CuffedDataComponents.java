package com.lazrproductions.cuffed.component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.lazrproductions.cuffed.CuffedMod;
import com.mojang.serialization.Codec;

import net.minecraft.core.Registry;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;

public class CuffedDataComponents {
    public static final DataComponentType<UUID> KEY = register("key", DataComponentType.<UUID>builder()
        .persistent(UUIDUtil.CODEC)
        .networkSynchronized(UUIDUtil.STREAM_CODEC)
    );
    public static final DataComponentType<Set<KeyData>> BOUND_LOCKS = register("bound_locks", DataComponentType.<Set<KeyData>>builder()
        .persistent(KeyData.CODEC.listOf().xmap(HashSet::new, ArrayList::new))
        .networkSynchronized(ByteBufCodecs.<RegistryFriendlyByteBuf, KeyData>list().apply(KeyData.STREAM_CODEC).map(HashSet::new, ArrayList::new))
    );
    public static final DataComponentType<Integer> KEY_COUNT = register("key_count", DataComponentType.<Integer>builder()
        .persistent(Codec.INT)
        .networkSynchronized(ByteBufCodecs.VAR_INT)
    );
    public static final DataComponentType<Component> KEY_NAME = register("key_name", DataComponentType.<Component>builder()
        .persistent(ComponentSerialization.CODEC)
        .networkSynchronized(ComponentSerialization.STREAM_CODEC)
    );
    public static final DataComponentType<Integer> QUALITY = register("quality", DataComponentType.<Integer>builder()
        .persistent(Codec.INT)
        .networkSynchronized(ByteBufCodecs.VAR_INT)
    );

    private static <T> DataComponentType<T> register(String name, DataComponentType.Builder<T> builder) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, CuffedMod.id(name), builder.build());
    }
}
