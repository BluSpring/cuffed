package com.lazrproductions.cuffed.init;

import com.lazrproductions.cuffed.CuffedMod;
import com.lazrproductions.cuffed.restraints.base.AbstractRestraint;
import com.lazrproductions.cuffed.restraints.custom.*;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class ModRestraints {
    private static boolean isInitialized = false;

    public static final Registry<AbstractRestraint> REGISTRY = FabricRegistryBuilder.<AbstractRestraint>createSimple(ResourceKey.createRegistryKey(CuffedMod.id("restraints")))
        .buildAndRegister();
    
    public static final AbstractRestraint BUNDLE = register("bundle", new BundleRestraint());
    public static final AbstractRestraint PILLORY = register("pillory", new PilloryRestraint());
    public static final AbstractRestraint DUCK_TAPE_HEAD = register("duck_tape_head", new DuckTapeHeadRestraint());

    public static final AbstractRestraint HANDCUFFS_ARMS = register("handcuffs_arms", new HandcuffsArmsRestraint());
    public static final AbstractRestraint SHACKLES = register("shackles_arms", new ShacklesArmsRestraint());
    public static final AbstractRestraint DUCK_TAPE_ARMS = register("duck_tape_arms", new DuckTapeArmsRestraint());

    public static final AbstractRestraint HANDCUFFS_LEGS = register("handcuffs_legs", new HandcuffsLegsRestraint());
    public static final AbstractRestraint SHACKLES_LEGS = register("shackles_legs", new ShacklesLegsRestraint());
    public static final AbstractRestraint DUCK_TAPE_LEGS = register("duck_tape_legs", new DuckTapeLegsRestraint());


    // Supporter only restraints:
    public static final AbstractRestraint FUZZY_HANDCUFFS = register("fuzzy_handcuffs", new FuzzyHandcuffsRestraint());

    private static AbstractRestraint register(String name, AbstractRestraint restraint) {
        return Registry.register(REGISTRY, CuffedMod.id(name), restraint);
    }

    public static void register() {
        if (isInitialized) {
            throw new IllegalStateException("Restraints already initialized");
        }
        isInitialized = true;
        CuffedMod.LOGGER.info("Registered restraints");
    }
}
