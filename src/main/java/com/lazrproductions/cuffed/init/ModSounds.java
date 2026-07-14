package com.lazrproductions.cuffed.init;

import com.lazrproductions.cuffed.CuffedMod;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Function;

public class ModSounds {
    public static final Holder<SoundEvent> HANDCUFFED = register("restraint.apply_handcuffs", SoundEvent::createVariableRangeEvent);
    public static final Holder<SoundEvent> SHACKLES_EQUIP = register("restraint.apply_shackles", SoundEvent::createVariableRangeEvent);
    public static final Holder<SoundEvent> PILLORY_USE = register("block.pillory.use", SoundEvent::createVariableRangeEvent);
    public static final Holder<SoundEvent> GUILLOTINE_USE = register("block.guillotine.use", SoundEvent::createVariableRangeEvent);
    
    public static final Holder<SoundEvent> SAFE_OPEN = register("block.safe.open", SoundEvent::createVariableRangeEvent);
    public static final Holder<SoundEvent> SAFE_CLOSE = register("block.safe.close", SoundEvent::createVariableRangeEvent);

    private static Holder<SoundEvent> register(String path, Function<ResourceLocation, SoundEvent> eventCreator) {
        var id = CuffedMod.id(path);
        return Registry.registerForHolder(BuiltInRegistries.SOUND_EVENT, id, eventCreator.apply(id));
    }

    public static void register() {
    }
}
