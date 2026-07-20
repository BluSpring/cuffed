package com.lazrproductions.cuffed.restraints;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.lazrproductions.cuffed.CuffedMod;
import com.lazrproductions.cuffed.entity.animation.ArmRestraintAnimationFlags;
import com.lazrproductions.cuffed.entity.animation.LegRestraintAnimationFlags;
import com.lazrproductions.cuffed.restraints.base.AbstractRestraint;
import com.lazrproductions.cuffed.restraints.base.RestraintType;
import com.mojang.datafixers.util.Pair;
import org.jetbrains.annotations.ApiStatus;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class RestraintAPI {
    /**
     * Where all restraint registries found by Cuffed are kept and accessed.
     */
    public static final class Registries {
        static final Collection<Registry<AbstractRestraint>> RESTRAINT_REGISTRIES = new HashSet<>();

        static {
            discoverRegistries();
        }

        @ApiStatus.Internal
        public static void discoverRegistries() {
            for (Registry<?> registry : BuiltInRegistries.REGISTRY) {
                if (registry.size() > 0 && registry.getAny().orElseThrow().value() instanceof AbstractRestraint)
                    RESTRAINT_REGISTRIES.add((Registry<AbstractRestraint>) registry);
            }
        }

        /**
         * !! NOT TO BE USED BY MOD AUTHORS !!
         * <p>
         * Add a registry to the list of all restraint registries.
         * 
         * <p>
         * Used by Cuffed to keep track of all restraints added by Cuffed and other
         * mods.
         */
        @SuppressWarnings("unchecked")
        public static void register(Registry<?> registry) {
            Registry<AbstractRestraint> r = (Registry<AbstractRestraint>) registry;

            for (ResourceLocation key : r.keySet()) {
                if (containsKey(key)) {
                    RestraintRegistryContainsKeyException ex = new RestraintRegistryContainsKeyException(key);
                    CuffedMod.LOGGER.error("The Restraint Registry already contains the key " + key, ex);
                    throw ex;
                }

                AbstractRestraint restraint = r.get(key);
                if(containsValue(restraint)) {
                    RestraintRegistryContainsRestraintException ex = new RestraintRegistryContainsRestraintException(key);
                    CuffedMod.LOGGER.error("The Restraint Registry already contains the restraint " + key, ex);
                    throw ex;
                }

                AbstractRestraint otherWithSameItem = get(restraint.getItem(), restraint.getType());
                if(otherWithSameItem != null && otherWithSameItem.getType() == restraint.getType()) {
                    ConflictingRestraintItemAndTypeException ex = new ConflictingRestraintItemAndTypeException(key, otherWithSameItem.getType());
                    CuffedMod.LOGGER.error("The Restraint Registry already contains a restraint for " + key + " with the same restraint restraintType of " + otherWithSameItem.getType(), ex);
                    throw ex;
                }
            }


            RESTRAINT_REGISTRIES.add(r);
        }

        /**
         * Get whether or not Cuffed has found the restraint with the given key.
         * 
         * @param key The key to check for
         */
        public static boolean containsKey(ResourceLocation key) {
            for (Registry<?> i : RESTRAINT_REGISTRIES)
                if (i.containsKey(key))
                    return true;
            return false;
        }

        /**
         * Get whether or not Cuffed has found the given restraint.
         * 
         * @param restraint The restraint to check for
         */
        public static boolean containsValue(AbstractRestraint restraint) {
            for (Registry<AbstractRestraint> i : RESTRAINT_REGISTRIES)
                if (i.getKey(restraint) != null)
                    return true;
            return false;
        }

        /**
         * Get the restraint with the given key
         * 
         * @param key The key of the restraint to get
         */
        public static AbstractRestraint get(ResourceLocation key) {
            for (Registry<AbstractRestraint> i : RESTRAINT_REGISTRIES)
                if (i.containsKey(key))
                    return i.get(key);
            return null;
        }

        /**
         * Get the restraint from the registry with the given restraint item.
         * 
         * @param restraintItem The item to get a restraint from
         * @param type The restraintType of restraint to look for
         */
        public static AbstractRestraint get(Item restraintItem, RestraintType type) {
            List<Pair<Item, AbstractRestraint>> pairs = getAllRestraintItemsAndTheirRestraints();
            for (Pair<Item, AbstractRestraint> pair : pairs) {
                if (pair.getFirst().equals(restraintItem))
                    if(pair.getSecond().getType() == type)
                        return pair.getSecond();
            }
            return null;
        }

        /**
         * Get all of the restraints found by Cuffed.
         */
        public static List<AbstractRestraint> getAllRestraints() {
            ArrayList<AbstractRestraint> res = new ArrayList<>();
            for (Registry<AbstractRestraint> reg : RESTRAINT_REGISTRIES)
                for (AbstractRestraint ent : reg)
                    res.add(ent);
            return res;
        }

        /**
         * Get all of the restraints found by Cuffed.
         */
        public static List<Item> getAllRestraintItems() {
            ArrayList<Item> res = new ArrayList<>();
            for (Registry<AbstractRestraint> reg : RESTRAINT_REGISTRIES)
                for (AbstractRestraint ent : reg)
                    res.add(ent.getItem());
            return res;
        }

        /**
         * Get all of the restraints found by Cuffed and retrun them and their restraint
         * item as pairs.
         */
        public static List<Pair<Item, AbstractRestraint>> getAllRestraintItemsAndTheirRestraints() {
            ArrayList<Pair<Item, AbstractRestraint>> pairs = new ArrayList<>();
            for (Registry<AbstractRestraint> reg : RESTRAINT_REGISTRIES)
                for (AbstractRestraint ent : reg)
                    pairs.add(new Pair<>(ent.getItem(), ent));
            return pairs;
        }

        /**
         * How many registries the Cuffed has found to be Restraint registries.
         */
        public static int size() {
            return RESTRAINT_REGISTRIES.size();
        }
        
        /**
         * Get the total amount of restraints registered in all registries.
         */
        public static int total() {
            int total = 0;
            for (Registry<AbstractRestraint> i : RESTRAINT_REGISTRIES)
                total += i.size();
            return total;
        }

        public static class RestraintRegistryContainsKeyException extends RuntimeException {
            public RestraintRegistryContainsKeyException() {
                super("The Restraint Registry already contains this key!");
            }

            public RestraintRegistryContainsKeyException(ResourceLocation key) {
                super("The Restraint Registry already contains the key " + key);
            }
        }

        public static class RestraintRegistryContainsRestraintException extends RuntimeException {
            public RestraintRegistryContainsRestraintException() {
                super("The Restraint Registry already contains this restraint!");
            }

            public RestraintRegistryContainsRestraintException(ResourceLocation key) {
                super("The Restraint Registry already contains the restraint " + key);
            }
        }

        public static class ConflictingRestraintItemAndTypeException extends RuntimeException {
            public ConflictingRestraintItemAndTypeException() {
                super("The Restraint Registry already contains a restraint for this item with the same restraint restraintType!");

            }

            public ConflictingRestraintItemAndTypeException(ResourceLocation key, RestraintType type) {
                super("The Restraint Registry already contains a restraint for " + key + " with the same restraint restraintType of " + type);
            }
        }
    }

    /**
     * Get a restraint from it's serialized data.
     * 
     * @param tag The serialized data of the restraint to get.
     * @return A new restraint serialized from the given data.
     */
    public static AbstractRestraint getRestraintFromTag(CompoundTag tag) {
        if (tag.contains("Id")) {
            AbstractRestraint r = getNewRestraintByKey(ResourceLocation.bySeparator(tag.getString("Id"),':'));
            if (r != null) {
                r.deserializeNBT(tag);
                return r;
            }
        }
        return null;
    }

    /**
     * Get the base class for the restraint with the given key
     * 
     * @param key The key of the restraint to get.
     * @return The base class for the restraint with the given, this is not to be
     *         assigned as the worn restraint of any player before the data is
     *         modified or deserialized.
     */
    public static AbstractRestraint getNewRestraintByKey(ResourceLocation key) {
        if (Registries.containsKey(key))
            return Registries.get(key);
        return null;
    }

    /**
     * Get a new restraint instance from it's item.
     * 
     * @param stack  The itemstack to get a new restraint from
     * @param type   The RestraintType to get
     * @param player The player being restrained
     * @param captor The player who is applying the restraints
     * @return The new restraint instance from the item given
     */
    public static AbstractRestraint getRestraintFromStack(ItemStack stack, RestraintType type, ServerPlayer player, ServerPlayer captor) {
        if (stack.getItem() != null) {
            var restraintBase = Registries.get(stack.getItem(), type);
            if(restraintBase != null) {
                String className = restraintBase.getClass().getName();

                try {
                    Object o = Class.forName(className).getConstructor(ItemStack.class, ServerPlayer.class, ServerPlayer.class).newInstance(stack, player, captor);
                    if(o instanceof AbstractRestraint r)
                        return r;
                } catch (Exception e) {
                    CuffedMod.LOGGER.info("Error getting new instance of restraint for stack " + stack.getHoverName().getString());
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * Get whether or not the given itemstack is a restraint item.
     * @param stack The stack to check
     */
    public static boolean isRestraintItem(ItemStack stack) {
        return IsHeadRestraintItem(stack) || IsArmRestraintItem(stack) || IsLegRestraintItem(stack);
    }
    
    /**
     * Get whether or not the given itemstack is a head restraint item.
     * @param stack The stack to check
     */
    public static boolean IsHeadRestraintItem(ItemStack stack) {
        AbstractRestraint r = Registries.get(stack.getItem(), RestraintType.HEAD);
        return r != null && r.getType() == RestraintType.HEAD;
    }

    /**
     * Get whether or not the given itemstack is a head restraint item.
     * @param stack The stack to check
     */
    public static boolean IsArmRestraintItem(ItemStack stack) {
        AbstractRestraint r = Registries.get(stack.getItem(),RestraintType.ARM);
        return r != null && r.getType() == RestraintType.ARM;
    }
    
    /**
     * Get whether or not the given itemstack is a leg restraint item.
     * @param stack The stack to check
     */
    public static boolean IsLegRestraintItem(ItemStack stack) {
        AbstractRestraint r = Registries.get(stack.getItem(), RestraintType.LEG);
        return r != null && r.getType() == RestraintType.LEG;
    }

    /**
     * Get whether or not the given item stack can be equipped as a restraint.
     * @param stack The item stack of the restraint to check
     * @param type The restraintType of restraint to check for
     * @param player The player being restrained
     * @param captor The player restraining
     * @return Whether or not the restraint item can be equipped
     */
    public static boolean canEquipRestriantItem(ItemStack stack, RestraintType type, ServerPlayer player, ServerPlayer captor) {
        var r = Registries.get(stack.getItem(), type);
        return r != null && r.canEquipRestraintItem(stack, player, captor);
    }

    /**
     * Get the arm animation flags by its key.
     * @param key The key to check
     */
    public static ArmRestraintAnimationFlags getArmAnimationFlagByKey(ResourceLocation key) {
        var r = Registries.get(key);
        return r != null ? r.getArmAnimationFlags() : ArmRestraintAnimationFlags.NONE;
    }
    
    /**
     * Get the leg animation flags by its key.
     * @param key The key to check
     */
    public static LegRestraintAnimationFlags getLegAnimationFlagByKey(ResourceLocation key) {
        var r = Registries.get(key);
        return r != null ? r.getLegAnimationFlags() : LegRestraintAnimationFlags.NONE;
    }
}
