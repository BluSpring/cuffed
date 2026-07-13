package com.lazrproductions.cuffed.init;

import com.lazrproductions.cuffed.CuffedMod;
import com.lazrproductions.cuffed.recipes.*;
import com.lazrproductions.cuffed.recipes.serializer.KeyMoldBakeRecipeSerializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

public class ModRecipes {
    public static final RecipeSerializer<KeyRingCreateRecipe> KEY_RING_CREATE = register("key_ring_create", new SimpleCraftingRecipeSerializer<>(KeyRingCreateRecipe::new));
    public static final RecipeSerializer<KeyRingAddRecipe> KEY_RING_ADD = register("key_ring_add", new SimpleCraftingRecipeSerializer<>(KeyRingAddRecipe::new));
    public static final RecipeSerializer<KeyRingDisassembleRecipe> KEY_RING_DISASSEMBLE = register("key_ring_disassemble", new SimpleCraftingRecipeSerializer<>(KeyRingDisassembleRecipe::new));


    public static final RecipeSerializer<KeyMoldCopyRecipe> KEY_MOLD_COPY = register("key_mold_copy", new SimpleCraftingRecipeSerializer<>(KeyMoldCopyRecipe::new));
    public static final RecipeSerializer<KeyMoldBakeRecipe> KEY_MOLD_BAKE = register("key_mold_bake", new KeyMoldBakeRecipeSerializer<>(KeyMoldBakeRecipe::new));

    public static final RecipeSerializer<BakedKeyMoldCopyRecipe> BAKED_KEY_MOLD_COPY = register("baked_key_mold_copy", new SimpleCraftingRecipeSerializer<>(BakedKeyMoldCopyRecipe::new));

    public static final RecipeSerializer<PosterChangeRecipe> POSTER_CHANGE = register("poster_change", new SimpleCraftingRecipeSerializer<>(PosterChangeRecipe::new));

    public static void register() {
    }
    
    private static <T extends Recipe<?>> RecipeSerializer<T> register(String name, RecipeSerializer<T> serializer) {
        return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, CuffedMod.id(name), serializer);
    }
}
