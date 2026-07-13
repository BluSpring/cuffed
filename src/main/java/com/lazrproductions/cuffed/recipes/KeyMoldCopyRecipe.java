package com.lazrproductions.cuffed.recipes;

import java.util.ArrayList;

import com.lazrproductions.cuffed.init.ModItems;
import com.lazrproductions.cuffed.init.ModRecipes;
import com.lazrproductions.cuffed.items.KeyItem;
import com.lazrproductions.cuffed.items.KeyMoldItem;
import org.jetbrains.annotations.NotNull;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class KeyMoldCopyRecipe extends CustomRecipe {
    public KeyMoldCopyRecipe(ResourceLocation idIn, CraftingBookCategory category) {
        super(idIn, category);
    }

    @Override
    public boolean matches(@NotNull CraftingContainer inv, @NotNull Level level) {
        return isGridValid(inv);
    }

    @SuppressWarnings("null")
    @Override
    public ItemStack assemble(@NotNull CraftingContainer inv, @NotNull RegistryAccess access) {

        if (matches(inv, null)) {
            ItemStack keyStack = getKeyStackFromGrid(inv);
            return keyStack.getOrCreateTag().contains(KeyItem.TAG_ID) ? KeyMoldItem.createFromKey(keyStack) : Items.CLAY_BALL.getDefaultInstance();
        }

        return ItemStack.EMPTY;
    }

    public ArrayList<ItemStack> getAllKeysInGrid(@NotNull CraftingContainer inv) {
        ArrayList<ItemStack> keysInGrid = new ArrayList<ItemStack>(0);

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack checkingStack = inv.getItem(i);
            if (!checkingStack.isEmpty())
                if (checkingStack.is(ModItems.KEY) || checkingStack.is(ModItems.KEY_MOLD) || checkingStack.is(Items.CLAY_BALL)) {
                    Item item = checkingStack.getItem();
                    if (item == ModItems.KEY)
                        keysInGrid.add(checkingStack);
                }
        }

        return keysInGrid;
    }
    public ArrayList<ItemStack> getAllValidItemsInGrid(@NotNull CraftingContainer inv) {
        ArrayList<ItemStack> validInGrid = new ArrayList<ItemStack>(0);

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack checkingStack = inv.getItem(i);
            if (!checkingStack.isEmpty())
                if (checkingStack.is(ModItems.KEY) || checkingStack.is(ModItems.KEY_MOLD) || checkingStack.is(Items.CLAY_BALL))
                    validInGrid.add(checkingStack);       
        }

        return validInGrid;
    }

    public ItemStack getKeyStackFromGrid(@NotNull CraftingContainer inv) {
        ArrayList<ItemStack> keysInGrid = getAllKeysInGrid(inv);
        return keysInGrid.get(0);
    }

    public boolean isGridValid(@NotNull CraftingContainer inv) {
        int numOfClayOrMolds = 0;
        int numOfKeys = 0;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack checkingStack = inv.getItem(i);
            if (!checkingStack.isEmpty())
                if (checkingStack.is(ModItems.KEY) && checkingStack.getOrCreateTag().contains(KeyItem.TAG_ID))
                    numOfKeys++;
                else if(checkingStack.is(ModItems.KEY_MOLD) || checkingStack.is(Items.CLAY_BALL))
                    numOfClayOrMolds++;
                else
                    return false;
        }

        // there must be only 1 key and only 1 clay/molds
        return numOfKeys == 1 && numOfClayOrMolds == 1;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.KEY_MOLD_COPY;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(@NotNull CraftingContainer container) {
        NonNullList<ItemStack> nonnulllist = NonNullList.withSize(container.getContainerSize(), ItemStack.EMPTY);
    
        for(int i = 0; i < nonnulllist.size(); ++i) {
            ItemStack item = container.getItem(i);
            if (item.is(ModItems.KEY))
                nonnulllist.set(i, item.copy());
        }
    
        return nonnulllist;
    }
}
