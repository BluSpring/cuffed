package com.lazrproductions.cuffed.component;

import com.lazrproductions.cuffed.CuffedMod;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;

public class CuffedDataComponents {

    private static <T> DataComponentType<T> register(String name, DataComponentType.Builder<T> builder) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, CuffedMod.id(name), builder.build());
    }
}
