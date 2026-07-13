package com.lazrproductions.cuffed.init;

import com.lazrproductions.cuffed.CuffedMod;
import com.lazrproductions.cuffed.effect.RestrainedEffect;
import com.lazrproductions.cuffed.effect.WoundedEffect;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class ModEffects {
    public static final Holder<MobEffect> RESTRAINED_EFFECT = register("restrained",
        new RestrainedEffect(MobEffectCategory.HARMFUL, 0x000000));
            
    public static final Holder<MobEffect> WOUNDED_EFFECT = register("wounded",
        new WoundedEffect(MobEffectCategory.HARMFUL, 0x000000));

    public static void register() {
    }

    private static Holder<MobEffect> register(String name, MobEffect effect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, CuffedMod.id(name), effect);
    }
}
