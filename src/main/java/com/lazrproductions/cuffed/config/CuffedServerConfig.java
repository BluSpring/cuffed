package com.lazrproductions.cuffed.config;

import com.lazrproductions.cuffed.CuffedMod;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;

public class CuffedServerConfig {
    private static final ExtendedBuilder BUILDER = new ExtendedBuilder();
    
    public final ConfigValue<Integer> MAX_KEYS_PER_RING = BUILDER.define("Maximum Keys Per Ring", "The maximum number of keys that can fit on a single key ring.", 16);
    public final ConfigValue<Integer> SAFE_SLOTS = BUILDER.define("Safes Slots", "The total number of slots in the safe.", 36);
    public final ConfigValue<Boolean> REQUIRE_LOW_HEALTH_TO_RESTRAIN = BUILDER.define("Require Low Health To Restrain", "Whether or not to require players to be under 30% health to be restrained. If a player is already restrained then this setting doesn't take effect.", false);
    public final ConfigValue<Boolean> ALLOW_BREAKING_OUT_OF_PILLORY = BUILDER.define("Allow breaking out of the pillory", "Whether or not to allow players to spam crouch to break out of pillories.", true);
    public final ConfigValue<Boolean> GUILLOTINE_DROPS_HEAD = BUILDER.define("Guillote drops the player's head", "Whether or not the guillotine drops the player's head when they die in it.", true);
    public final ConfigValue<Boolean> ALLOW_SELF_RESTRAINING = BUILDER.define("Allow restraining yourself", "Whether or not players can apply/remove their own head and leg restraints.", true);

    public final ConfigValue<Boolean> ANCHORING_ANCHOR_ONLY_WHEN_RESTRAINED = BUILDER.push("Anchoring Settings").define("Only Restrained Players Can Be Restrained", "Whether or not to require players to be restrained to get anchored.", false);
    public final ConfigValue<Float> ANCHORING_MAX_CHAIN_LENGTH = BUILDER.define("Max Chain Length", "The maximum length of the chain when anchoring.", 5.0F);
    public final ConfigValue<Float> ANCHORING_SUFFOCATION_LENGTH = BUILDER.define("Suffocation Length", "The distance when anchored entites start suffocating.", 12.0F);
    public final ConfigValue<Boolean> ANCHORING_ALLOW_ANCHORING_TO_FENCES = BUILDER.define("Allow Anchoring To Fences", "Whether or not players should be allowed to anchor entities to FENCES.", true);
    public final ConfigValue<Boolean> ANCHORING_ALLOW_ANCHORING_TO_TRIPWIRE_HOOKS = BUILDER.define("Allow Anchoring To Tripwire Hook", "Whether or not players should be allowed to anchor entities to TRIPWIRE HOOKS.", true);
    public final ConfigValue<Boolean> ANCHORING_ALLOW_ANCHORING_TO_WEIGHTED_ANCHORS = BUILDER.define("Allow Anchoring To Weighted Anchors", "Whether or not players should be allowed to anchor entities to WEIGHTED ANCHORS.", true);

    public final ConfigValue<Boolean> NICKNAME_PERSISTS_ON_DEATH = BUILDER.popPush("Nickname Settings").define("Nickname Persists On Death", "Whether or not nicknames should persist on death.", true);
    public final ConfigValue<Boolean> NICKNAME_PERSISTS_ON_LOGOUT = BUILDER.define("Nickname Persists On Logout", "Whether or not nicknames should persist on logout.", true);

    public final ConfigValue<Integer> LOCKPICKING_PROGRESS_PER_PICK_FOR_BREAKING_PADLOCKS = BUILDER.popPush("Lockpicking Settings").define("Progress Per Pick For Breaking Padlocks", "How much progress is gained on a successfull pick when lockpicking a PADLOCK.", 8);
    public final ConfigValue<Integer> LOCKPICKING_SPEED_INCREASE_PER_PICK_FOR_BREAKING_PADLOCKS = BUILDER.define("Speed Increase Per Pick For Breaking Padlocks", "How much the progress-loss speeds up per pick when lockpicking a PADLOCK.", 10);
    public final ConfigValue<Integer> LOCKPICKING_PROGRESS_PER_PICK_FOR_BREAKING_REINFORCED_PADLOCKS = BUILDER.define("Progress Per Pick For Breaking Reinforced Padlocks", "How much progress is gained on a successfull pick when lockpicking a REINFORCED PADLOCK.", 6);
    public final ConfigValue<Integer> LOCKPICKING_SPEED_INCREASE_PER_PICK_FOR_BREAKING_REINFORCED_PADLOCKS = BUILDER.define("Speed Increase Per Pick For Breaking Reinforced Padlocks", "How much the progress-loss speeds up per pick when lockpicking a REINFORCED PADLOCK.", 13);
    public final ConfigValue<Integer> LOCKPICKING_PROGRESS_PER_PICK_FOR_BREAKING_CELL_DOORS = BUILDER.define("Progress Per Pick For Breaking Cell Doors", "How much progress is gained on a successfull pick when lockpicking a CELL DOOR.", 6);
    public final ConfigValue<Integer> LOCKPICKING_SPEED_INCREASE_PER_PICK_FOR_BREAKING_CELL_DOORS = BUILDER.define("Speed Increase Per Pick For Breaking Cell Doors", "How much the progress-loss speeds up per pick when lockpicking a CELL DOOR.", 14);
    public final ConfigValue<Integer> LOCKPICKING_PROGRESS_PER_PICK_FOR_BREAKING_SAFES = BUILDER.define("Progress Per Pick For Breaking Safes", "How much progress is gained on a successfull pick when lockpicking a SAFES.", 3);
    public final ConfigValue<Integer> LOCKPICKING_SPEED_INCREASE_PER_PICK_FOR_BREAKING_SAFES = BUILDER.define("Speed Increase Per Pick For Breaking Safes", "How much the progress-loss speeds up per pick when lockpicking a SAFES.", 10);



    public final ConfigValue<Integer> RESTRAINT_DURABILITY_HANDCUFFS = BUILDER.popPush("Restraint Durabilities").define("Handcuffs Durability", "The amount of durability handcuffs have.", 40);
    public final ConfigValue<Integer> RESTRAINT_DURABILITY_FUZZY_HANDCUFFS = BUILDER.define("Fuzzy Handcuffs Durability", "The amount of durability fuzzy handcuffs have.", 30);
    public final ConfigValue<Integer> RESTRAINT_DURABILITY_SHACKLES = BUILDER.define("Shackles Durability", "The amount of durability shackles have.", 15);



    public final ConfigValue<Boolean> HANDCUFFS_ON_ARMS_CAN_BE_BROKEN_OUT_OF = BUILDER.popPush("Handcuffs when on Arms").define("Can Be Broken Out Of", "Whether or not this restraint can be broken out of.", true);
    public final ConfigValue<Boolean> HANDCUFFS_ON_ARMS_DROP_ITEM_WHEN_BROKEN = BUILDER.define("Drop Item When Broken", "Whether or not to drop the item when broken out of.", false);
    public final ConfigValue<Boolean> HANDCUFFS_ON_ARMS_LOCKPICKABLE = BUILDER.define("Lockpickable", "Whether or not this restraint is lockpickable.", true);
    public final ConfigValue<Integer> HANDCUFFS_ON_ARMS_LOCKPICKING_PROGRESS_PER_PICK = BUILDER.define("Lockpicking Progresss Per Pick", "The amount of progress gained per pick when lockpicking this restraint.", 6);
    public final ConfigValue<Integer> HANDCUFFS_ON_ARMS_LOCKPICKING_SPEED_INCREASE_PER_PICK = BUILDER.define("Lockpicking Speed Increase Per Pick", "The speed increase per pick when lockpicking this restraint.", 12);

    public final ConfigValue<Boolean> FUZZY_HANDCUFFS_ON_ARMS_CAN_BE_BROKEN_OUT_OF = BUILDER.popPush("Fuzzy Handcuffs when on Arms").define("Can Be Broken Out Of", "Whether or not this restraint can be broken out of.", true);
    public final ConfigValue<Boolean> FUZZY_HANDCUFFS_ON_ARMS_DROP_ITEM_WHEN_BROKEN = BUILDER.define("Drop Item When Broken", "Whether or not to drop the item when broken out of.", false);
    public final ConfigValue<Boolean> FUZZY_HANDCUFFS_ON_ARMS_LOCKPICKABLE = BUILDER.define("Lockpickable", "Whether or not this restraint is lockpickable.", true);
    public final ConfigValue<Integer> FUZZY_HANDCUFFS_ON_ARMS_LOCKPICKING_PROGRESS_PER_PICK = BUILDER.define("Lockpicking Progresss Per Pick", "The amount of progress gained per pick when lockpicking this restraint.", 6);
    public final ConfigValue<Integer> FUZZY_HANDCUFFS_ON_ARMS_LOCKPICKING_SPEED_INCREASE_PER_PICK = BUILDER.define("Lockpicking Speed Increase Per Pick", "The speed increase per pick when lockpicking this restraint.", 14);

    public final ConfigValue<Boolean> SHACKLES_ON_ARMS_CAN_BE_BROKEN_OUT_OF = BUILDER.popPush("Shackles when on Arms").define("Can Be Broken Out Of", "Whether or not this restraint can be broken out of.", true);
    public final ConfigValue<Boolean> SHACKLES_ON_ARMS_DROP_ITEM_WHEN_BROKEN = BUILDER.define("Drop Item When Broken", "Whether or not to drop the item when broken out of.", false);
    public final ConfigValue<Boolean> SHACKLES_ON_ARMS_LOCKPICKABLE = BUILDER.define("Lockpickable", "Whether or not this restraint is lockpickable.", true);
    public final ConfigValue<Integer> SHACKLES_ON_ARMS_LOCKPICKING_PROGRESS_PER_PICK = BUILDER.define("Lockpicking Progresss Per Pick", "The amount of progress gained per pick when lockpicking this restraint.", 8);
    public final ConfigValue<Integer> SHACKLES_ON_ARMS_LOCKPICKING_SPEED_INCREASE_PER_PICK = BUILDER.define("Lockpicking Speed Increase Per Pick", "The speed increase per pick when lockpicking this restraint.", 10);

    public final ConfigValue<Boolean> DUCK_TAPE_ON_ARMS_CAN_BE_BROKEN_OUT_OF = BUILDER.popPush("Duck Tape when on Arms").define("Can Be Broken Out Of", "Whether or not this restraint can be broken out of.", true);
    public final ConfigValue<Boolean> DUCK_TAPE_ON_ARMS_DROP_ITEM_WHEN_BROKEN = BUILDER.define("Drop Item When Broken", "Whether or not to drop the item when broken out of.", true);
    public final ConfigValue<Integer> DUCK_TAPE_ON_ARMS_DURABILITY = BUILDER.define("Durability", "The total durability of this restraint when breaking out of it.", 5);



    public final ConfigValue<Boolean> HANDCUFFS_ON_LEGS_CAN_BE_BROKEN_OUT_OF = BUILDER.popPush("Handcuffs when on Legs").define("Can Be Broken Out Of", "Whether or not this restraint can be broken out of.", true);
    public final ConfigValue<Boolean> HANDCUFFS_ON_LEGS_DROP_ITEM_WHEN_BROKEN = BUILDER.define("Drop Item When Broken", "Whether or not to drop the item when broken out of.", false);
    public final ConfigValue<Boolean> HANDCUFFS_ON_LEGS_LOCKPICKABLE = BUILDER.define("Lockpickable", "Whether or not this restraint is lockpickable.", true);
    public final ConfigValue<Integer> HANDCUFFS_ON_LEGS_LOCKPICKING_PROGRESS_PER_PICK = BUILDER.define("Lockpicking Progresss Per Pick", "The amount of progress gained per pick when lockpicking this restraint.", 6);
    public final ConfigValue<Integer> HANDCUFFS_ON_LEGS_LOCKPICKING_SPEED_INCREASE_PER_PICK = BUILDER.define("Lockpicking Speed Increase Per Pick", "The speed increase per pick when lockpicking this restraint.", 12);

    public final ConfigValue<Boolean> SHACKLES_ON_LEGS_CAN_BE_BROKEN_OUT_OF = BUILDER.popPush("Shackles when on Legs").define("Can Be Broken Out Of", "Whether or not this restraint can be broken out of.", true);
    public final ConfigValue<Boolean> SHACKLES_ON_LEGS_DROP_ITEM_WHEN_BROKEN = BUILDER.define("Drop Item When Broken", "Whether or not to drop the item when broken out of.", false);
    public final ConfigValue<Boolean> SHACKLES_ON_LEGS_LOCKPICKABLE = BUILDER.define("Lockpickable", "Whether or not this restraint is lockpickable.", true);
    public final ConfigValue<Integer> SHACKLES_ON_LEGS_LOCKPICKING_PROGRESS_PER_PICK = BUILDER.define("Lockpicking Progresss Per Pick", "The amount of progress gained per pick when lockpicking this restraint.", 8);
    public final ConfigValue<Integer> SHACKLES_ON_LEGS_LOCKPICKING_SPEED_INCREASE_PER_PICK = BUILDER.define("Lockpicking Speed Increase Per Pick", "The speed increase per pick when lockpicking this restraint.", 10);

    public final ConfigValue<Boolean> DUCK_TAPE_ON_LEGS_CAN_BE_BROKEN_OUT_OF = BUILDER.popPush("Duck Tape when on Legs").define("Can Be Broken Out Of", "Whether or not this restraint can be broken out of.", true);
    public final ConfigValue<Boolean> DUCK_TAPE_ON_LEGS_DROP_ITEM_WHEN_BROKEN = BUILDER.define("Drop Item When Broken", "Whether or not to drop the item when broken out of.", true);
    public final ConfigValue<Integer> DUCK_TAPE_ON_LEGS_DURABILITY = BUILDER.define("Durability", "The total durability of this restraint when breaking out of it.", 5);

    private static class ExtendedBuilder extends ModConfigSpec.Builder {
        public <T> ConfigValue<T> define(String path, String description, T defaultValue) {
            return this.comment(description).define(path, defaultValue);
        }

        public ExtendedBuilder push(String path) {
            super.push(path);
            return this;
        }

        public ExtendedBuilder popPush(String path) {
            super.pop();
            return this.push(path);
        }
    }

    public void registerConfig() {
        NeoForgeConfigRegistry.INSTANCE.register(CuffedMod.MODID, ModConfig.Type.SERVER, BUILDER.pop().build());
    }
}
