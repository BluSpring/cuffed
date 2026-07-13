package com.lazrproductions.cuffed.init;

import com.lazrproductions.cuffed.CuffedMod;
import com.lazrproductions.cuffed.client.particle.BloodDripParticle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

public class ModParticleTypes {
    public static final SimpleParticleType BLOOD_DRIP_FALL_PARTICLE = register("blood_drip", FabricParticleTypes.simple(true));

    private static <T extends ParticleType<?>> T register(String name, T particle) {
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE, CuffedMod.id(name), particle);
    }

    public static void register() {
    }

    @Environment(EnvType.CLIENT)
    public static void registerSprites() {
        ParticleFactoryRegistry.getInstance().register(BLOOD_DRIP_FALL_PARTICLE, BloodDripParticle.Provider::new);
    }
}
