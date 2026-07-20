package com.lazrproductions.cuffed.init;

import com.lazrproductions.cuffed.CuffedMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> LOCKABLE_BLOCKS = tag("lockable_blocks");
        public static final TagKey<Block> REINFORCED_BLOCKS = tag("reinforced_blocks");

        public static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(CuffedMod.MODID, name));
        }
    }
    public static class Entities {
        public static final TagKey<EntityType<?>> CHAINABLE_ENTITIES = tag("chainable_entities");
        
        public static TagKey<EntityType<?>> tag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(CuffedMod.MODID, name));
        }
    }
    public static class Items {
        public static final TagKey<Item> CAN_REINFORCE_PADLOCK = tag("can_reinforce_padlock");
        public static final TagKey<Item> RESTRAINTS = tag("restraints");

        public static final TagKey<Item> SUPPORTS_IMBUE = tag("enchantment/supports_imbue");
        public static final TagKey<Item> SUPPORTS_FAMINE = tag("enchantment/supports_famine");
        public static final TagKey<Item> SUPPORTS_SHROUD = tag("enchantment/supports_shroud");
        public static final TagKey<Item> SUPPORTS_EXHAUST = tag("enchantment/supports_exhaust");
        public static final TagKey<Item> SUPPORTS_SILENCE = tag("enchantment/supports_silence");
        public static final TagKey<Item> SUPPORTS_BUOYANT = tag("enchantment/supports_buoyant");
        
        public static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(CuffedMod.MODID, name));
        }
    }
}
