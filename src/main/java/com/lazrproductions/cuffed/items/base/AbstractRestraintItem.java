package com.lazrproductions.cuffed.items.base;

import com.lazrproductions.cuffed.api.CuffedAPI;
import com.lazrproductions.cuffed.cap.RestrainableCapability;
import com.lazrproductions.cuffed.cap.base.IRestrainableCapability;
import com.lazrproductions.cuffed.init.ModEnchantments;
import com.lazrproductions.cuffed.restraints.RestraintAPI;
import com.lazrproductions.cuffed.restraints.base.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class AbstractRestraintItem extends Item {
    public AbstractRestraintItem(Properties p) {
        super(p);
    }

    @Override
    public boolean canBeEnchantedWith(ItemStack stack, Holder<Enchantment> enchantment, EnchantingContext context) {
        if (context == EnchantingContext.PRIMARY) {
            return enchantment.is(Enchantments.UNBREAKING) || enchantment.is(Enchantments.BINDING_CURSE)
                || enchantment.is(ModEnchantments.IMBUE) || enchantment.is(ModEnchantments.FAMINE)
                || enchantment.is(ModEnchantments.SHROUD) || enchantment.is(ModEnchantments.EXHAUST);
        }

        return super.canBeEnchantedWith(stack, enchantment, context);
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return true;
    }

    @Override
    public int getEnchantmentValue() {
        return 1;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        Client.showExtendedInfo(tooltipComponents);
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
    
    
    public static boolean dispenseRestraint(BlockSource source, ItemStack stack) {
        BlockPos blockpos = source.pos().relative(source.state().getValue(DispenserBlock.FACING));
        
        
        Predicate<Entity> restraintSelector = new PlayerCanEquipArmRestraintEntitySelector(stack);
        RestraintType typeToEquip = RestraintType.ARM;
        boolean isAmbiguousRestraint = false;

        if(stack.getItem() instanceof AbstractHeadRestraintItem) {
            restraintSelector = new PlayerCanEquipHeadRestraintEntitySelector(stack);
            typeToEquip = RestraintType.HEAD;
        } else if(stack.getItem() instanceof AbstractArmRestraintItem) {
            restraintSelector = new PlayerCanEquipArmRestraintEntitySelector(stack);
            typeToEquip = RestraintType.ARM;
        } else if(stack.getItem() instanceof AbstractLegRestraintItem) {
            restraintSelector = new PlayerCanEquipLegRestraintEntitySelector(stack);
            typeToEquip = RestraintType.LEG;
        } else if(stack.getItem() instanceof AbstractRestraintItem) {
            restraintSelector = new PlayerEntitySelector(stack);
            isAmbiguousRestraint = true;
        } else if(stack.is(Items.BUNDLE) && BundleItem.getFullnessDisplay(stack) <= 0) {
            restraintSelector = new PlayerCanEquipHeadRestraintEntitySelector(stack);
            typeToEquip = RestraintType.HEAD;
        }


        List<Player> list = source.level().getEntitiesOfClass(Player.class, new AABB(blockpos),
                EntitySelector.NO_SPECTATORS.and(restraintSelector));
        if (list.isEmpty()) {
            return false;
        } else {
            ServerPlayer player = (ServerPlayer)list.get(0);
            RestrainableCapability entity = (RestrainableCapability)CuffedAPI.Capabilities.getRestrainableCapability(player);
            ItemStack itemstack = stack.copyWithCount(1);

            if(isAmbiguousRestraint) {
                if((player.position().y() + 1d) > (double)blockpos.getY())  {
                    // dispenser is lower than player's waist
                    typeToEquip = RestraintType.LEG;
                } else {
                    // dispenser is higher than player's waist
                    typeToEquip = RestraintType.ARM;
                }

                AbstractRestraint restraint = RestraintAPI.getRestraintFromStack(itemstack, typeToEquip, player, player);

                if(typeToEquip == RestraintType.ARM && restraint instanceof AbstractArmRestraint && !entity.armsRestrained())
                    return entity.TryEquipRestraint(player, player, (AbstractArmRestraint)restraint);
                else if(typeToEquip == RestraintType.LEG && restraint instanceof AbstractLegRestraint && !entity.legsRestrained())
                    return entity.TryEquipRestraint(player, player, (AbstractLegRestraint)restraint);

                return false;
            }
            else if(typeToEquip == RestraintType.ARM && !entity.armsRestrained()) {
                AbstractRestraint restraint = RestraintAPI.getRestraintFromStack(itemstack, typeToEquip, player, player);
                return entity.TryEquipRestraint(player, player, (AbstractArmRestraint)restraint);
            }
            else if(typeToEquip == RestraintType.LEG && !entity.legsRestrained()) {
                AbstractRestraint restraint = RestraintAPI.getRestraintFromStack(itemstack, typeToEquip, player, player);
                return entity.TryEquipRestraint(player, player, (AbstractLegRestraint)restraint);
            }
            else if(typeToEquip == RestraintType.HEAD && !entity.headRestrained()) {
                AbstractRestraint restraint = RestraintAPI.getRestraintFromStack(itemstack, typeToEquip, player, player);
                return entity.TryEquipRestraint(player, player, (AbstractHeadRestraint)restraint);
            }
            else return false;
        }
    }

    public static class PlayerEntitySelector implements Predicate<Entity> {
        private final ItemStack itemStack;

        public PlayerEntitySelector(ItemStack stack) {
            this.itemStack = stack;
        }

        public boolean test(@Nullable Entity entity) {
            if(entity == null){
                return false;
            } else if (!entity.isAlive()) {
                return false;
            } else if (!(itemStack.getItem() instanceof AbstractRestraintItem)) {
                return false;
            } else if (entity instanceof ServerPlayer a) {
                IRestrainableCapability cap = CuffedAPI.Capabilities.getRestrainableCapability(a);
                return cap != null;
            } else
                return false;
        }
    }
    public static class PlayerCanEquipArmRestraintEntitySelector implements Predicate<Entity> {
        private final ItemStack itemStack;

        public PlayerCanEquipArmRestraintEntitySelector(ItemStack stack) {
            this.itemStack = stack;
        }

        public boolean test(@Nullable Entity entity) {
            if(entity == null){
                return false;
            } else if (!entity.isAlive()) {
                return false;
            } else if (!(itemStack.getItem() instanceof AbstractArmRestraintItem)) {
                return false;
            } else if (entity instanceof ServerPlayer a) {
                IRestrainableCapability cap = CuffedAPI.Capabilities.getRestrainableCapability(a);
                return !cap.armsRestrained();
            } else
                return false;
        }
    }
    public static class PlayerCanEquipLegRestraintEntitySelector implements Predicate<Entity> {
        private final ItemStack itemStack;

        public PlayerCanEquipLegRestraintEntitySelector(ItemStack stack) {
            this.itemStack = stack;
        }

        public boolean test(@Nullable Entity entity) {
            if(entity == null) {
                return false;
            } else if (!entity.isAlive()) {
                return false;
            } else if (!(itemStack.getItem() instanceof AbstractLegRestraintItem)) {
                return false;
            } else if (entity instanceof ServerPlayer a) {
                IRestrainableCapability cap = CuffedAPI.Capabilities.getRestrainableCapability(a);
                return !cap.legsRestrained();
            } else
                return false;
        }
    }
    public static class PlayerCanEquipHeadRestraintEntitySelector implements Predicate<Entity> {
        private final ItemStack itemStack;

        public PlayerCanEquipHeadRestraintEntitySelector(ItemStack stack) {
            this.itemStack = stack;
        }

        public boolean test(@Nullable Entity entity) {
            if(entity == null) {
                return false;
            } else if (!entity.isAlive()) {
                return false;
            } else if ((!(itemStack.getItem() instanceof AbstractHeadRestraintItem)) && !(itemStack.is(Items.BUNDLE) && BundleItem.getFullnessDisplay(itemStack) <= 0)) {
                return false;
            } else if (entity instanceof ServerPlayer a) {
                IRestrainableCapability cap = CuffedAPI.Capabilities.getRestrainableCapability(a);
                return !cap.headRestrained();
            } else
                return false;
        }
    }


    public static class Client {
        @Environment(EnvType.CLIENT)
        public static void showExtendedInfo(@NotNull List<Component> components) {
            components.add(Component.empty());
            if(Screen.hasShiftDown())
                components.add(Component.translatable("info.cuffed.restraint_type.extra").withStyle(ChatFormatting.WHITE));
            else
                components.add(Component.translatable("info.cuffed.restraint_type.showextra").withStyle(ChatFormatting.GRAY));
        }
    }
}
