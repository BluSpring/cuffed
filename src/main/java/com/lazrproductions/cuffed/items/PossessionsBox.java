package com.lazrproductions.cuffed.items;

import com.lazrproductions.cuffed.component.CuffedDataComponents;
import com.lazrproductions.cuffed.init.ModMenuTypes;
import com.lazrproductions.cuffed.inventory.FriskingContainer;
import com.lazrproductions.cuffed.inventory.FriskingMenu;
import com.lazrproductions.cuffed.inventory.tooltip.PossessionsBoxTooltip;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PossessionsBox extends Item {

    public final static String TAG_ITEMS = "Items";

    public PossessionsBox(Properties properties) {
        super(properties);
    }

    @Override
    public boolean overrideStackedOnOther(@NotNull ItemStack thisStack, @NotNull Slot slot, @NotNull ClickAction click,
                                          @NotNull Player player) {
        if (thisStack.getCount() != 1 || click != ClickAction.SECONDARY) {
            return false;
        } else {
            ItemStack otherStack = slot.getItem();
            if (otherStack.isEmpty()) {
                this.playRemoveOneSound(player);
                removeOne(thisStack).ifPresent((p_150740_) -> {
                    add(thisStack, slot.safeInsert(p_150740_));
                });
            }

            return true;
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player,
                                                  @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (dropContents(itemstack, player) && player.isCrouching()) {
            this.playDropContentsSound(player);
            return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }

    public static ItemStack add(ItemStack stack, ItemStack stackToAdd) {
        if (!stackToAdd.isEmpty()) {
            var stacks = new ArrayList<>(stack.getOrDefault(CuffedDataComponents.POSSESSIONS_ITEMS, NonNullList.withSize(1, ItemStack.EMPTY)));

            int k = stackToAdd.getCount();
            if (k != 0)
                stacks.addFirst(stackToAdd);

            stack.set(CuffedDataComponents.POSSESSIONS_ITEMS, NonNullList.of(ItemStack.EMPTY, stacks.toArray(new ItemStack[0])));
        }

        return stackToAdd;
    }

    private static Optional<ItemStack> removeOne(ItemStack stack) {
        if (stack.isEmpty())
            return Optional.empty();

        var stacks = new ArrayList<>(stack.getOrDefault(CuffedDataComponents.POSSESSIONS_ITEMS, NonNullList.withSize(1, ItemStack.EMPTY)));
        stacks.remove(stack);
        stack.set(CuffedDataComponents.POSSESSIONS_ITEMS, NonNullList.of(ItemStack.EMPTY, stacks.toArray(new ItemStack[0])));

        return Optional.of(stack);
    }

    private static boolean dropContents(ItemStack stack, Player player) {
        boolean hasThrown = false;

        if (player.isCrouching()) {
            var stacks = stack.get(CuffedDataComponents.POSSESSIONS_ITEMS);
            if (stacks == null || stacks.isEmpty())
                return false;

            for (ItemStack itemStack : stacks) {
                if (itemStack.isEmpty()) continue;

                player.drop(itemStack, true);
                hasThrown = true;
            }
        }

        return hasThrown;
    }

    public static NonNullList<ItemStack> getContents(ItemStack stack) {
        var stacks = stack.get(CuffedDataComponents.POSSESSIONS_ITEMS);
        if (stacks == null || stacks.isEmpty())
            return NonNullList.create();

        return stacks;
    }

    @Override
    public Component getName(@NotNull ItemStack stack) {
        var stacks = stack.get(CuffedDataComponents.POSSESSIONS_ITEMS);
        if (stacks != null && !stacks.isEmpty())
            return Component.translatable(this.getDescriptionId(stack) + ".full");

        return Component.translatable(this.getDescriptionId(stack) + ".empty");
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(@NotNull ItemStack p_150775_) {
        var contents = getContents(p_150775_);
        if (!contents.isEmpty()) {
            return Optional.of(new PossessionsBoxTooltip(contents));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, TooltipContext context, @NotNull List<Component> component,
                                @NotNull TooltipFlag p_150752_) {
        var stacks = stack.get(CuffedDataComponents.POSSESSIONS_ITEMS);
        if (stacks == null || stacks.isEmpty()) {
            component.add(Component.translatable("item.cuffed.possessions_box.lore.empty").withStyle(ChatFormatting.GRAY));
        } else {
            component.add(Component.translatable("item.cuffed.possessions_box.lore.full", stacks.size())
                .withStyle(ChatFormatting.GRAY));
        }

    }

    @Override
    public void onDestroyed(@NotNull ItemEntity p_150728_) {
        ItemUtils.onContainerDestroyed(p_150728_, getContents(p_150728_.getItem()));
    }

    private void playRemoveOneSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    private void playDropContentsSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_DROP_CONTENTS, 0.8F, 0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    public static void frisk(@NotNull ServerPlayer frisker, @NotNull ServerPlayer player, @NotNull ItemStack boxStack) {
        frisker.openMenu(new MenuProvider() {
            @Override
            public Component getDisplayName() {
                return player.getDisplayName();
            }

            @Override
            public AbstractContainerMenu createMenu(int id, @NotNull Inventory playerInventory, @NotNull Player p) {
                return new FriskingMenu(ModMenuTypes.FRISKING_MENU, id, playerInventory, player.getId(), new FriskingContainer(player, boxStack), 5);
            }
        });
    }
}
