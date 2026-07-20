package com.lazrproductions.cuffed;

import com.lazrproductions.cuffed.api.CuffedAPI;
import com.lazrproductions.cuffed.blocks.base.PosterType;
import com.lazrproductions.cuffed.blocks.entity.renderer.GuillotineBlockEntityRenderer;
import com.lazrproductions.cuffed.blocks.entity.renderer.TrayBlockEntityRenderer;
import com.lazrproductions.cuffed.client.gui.screen.FriskingScreen;
import com.lazrproductions.cuffed.command.CuffedDebugCommand;
import com.lazrproductions.cuffed.command.HandcuffCommand;
import com.lazrproductions.cuffed.command.argument.RestraintTypeArgument;
import com.lazrproductions.cuffed.compat.BetterCombatCompat;
import com.lazrproductions.cuffed.compat.SimpleVoiceChatCompat;
import com.lazrproductions.cuffed.compat.TacZCompat;
import com.lazrproductions.cuffed.component.CuffedDataComponents;
import com.lazrproductions.cuffed.config.CuffedServerConfig;
import com.lazrproductions.cuffed.entity.renderer.ChainKnotEntityRenderer;
import com.lazrproductions.cuffed.entity.renderer.CrumblingBlockRenderer;
import com.lazrproductions.cuffed.entity.renderer.PadlockEntityRenderer;
import com.lazrproductions.cuffed.entity.renderer.WeightedAnchorEntityRenderer;
import com.lazrproductions.cuffed.event.ModClientEvents;
import com.lazrproductions.cuffed.event.ModServerEvents;
import com.lazrproductions.cuffed.init.ModBlockEntities;
import com.lazrproductions.cuffed.init.ModBlocks;
import com.lazrproductions.cuffed.init.ModCreativeTabs;
import com.lazrproductions.cuffed.init.ModEffects;
import com.lazrproductions.cuffed.init.ModEnchantments;
import com.lazrproductions.cuffed.init.ModEntityTypes;
import com.lazrproductions.cuffed.init.ModItems;
import com.lazrproductions.cuffed.init.ModLockpickableTypes;
import com.lazrproductions.cuffed.init.ModMenuTypes;
import com.lazrproductions.cuffed.init.ModModelLayers;
import com.lazrproductions.cuffed.init.ModParticleTypes;
import com.lazrproductions.cuffed.init.ModRecipes;
import com.lazrproductions.cuffed.init.ModRestraints;
import com.lazrproductions.cuffed.init.ModSounds;
import com.lazrproductions.cuffed.init.ModStatistics;
import com.lazrproductions.cuffed.inventory.tooltip.PossessionsBoxTooltip;
import com.lazrproductions.cuffed.inventory.tooltip.TrayTooltip;
import com.lazrproductions.cuffed.items.TrayItem;
import com.lazrproductions.cuffed.items.base.AbstractRestraintItem;
import com.lazrproductions.cuffed.network.CuffedServerNetworking;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.DispenserBlock;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;

public class CuffedMod implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger(CuffedMod.MODID);
    public static final String MODID = "cuffed";

    public static final CuffedServerConfig SERVER_CONFIG = new CuffedServerConfig();

    public static boolean BetterCombatInstalled = false;
    public static boolean EpicFightInstalled = false;
    public static boolean ParcoolInstalled = false;
    public static boolean ElenaiDodge2Installed = false;
    public static boolean IronsSpellsnSpellbooksInstalled = false;
    public static boolean ManaAndArtificeInstalled = false;
    public static boolean KnightsOfBritanniaInstalled = false;
    public static boolean ArsNouveauInstalled = false;
    public static boolean PlayerReviveInstalled = false;
    public static boolean VoiceChatInstalled = false;

    public static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(MODID, name);
    }

    @Override
    public void onInitialize() {
        this.commonSetup();

        SERVER_CONFIG.registerConfig();

        ModEntityTypes.register();
        ModBlocks.register();
        ModBlockEntities.register();
        ModItems.register();
        ModParticleTypes.register();
        ModEnchantments.register();
        ModCreativeTabs.register();
        ModRecipes.register();
        ModEffects.register();
        ModStatistics.register();
        ModMenuTypes.register();
        ModRestraints.register();
        ModLockpickableTypes.register();

        ArgumentTypeRegistry.registerArgumentType(id("restraint_type"), RestraintTypeArgument.class, SingletonArgumentInfo.contextFree(RestraintTypeArgument::restraintType));

        CuffedServerNetworking.init();

//        this.registerCaps();
        ModSounds.register();
        registerCommands();
        ModEntityTypes.registerAttributes();

        if (FabricLoader.getInstance().isModLoaded("bettercombat")) {
            BetterCombatInstalled = true;
            BetterCombatCompat.load();
        }
//        if (FabricLoader.getInstance().isModLoaded("epicfight")) {
//            EpicFightInstalled = true;
//            EpicFightCompat.load();
//        }
//        if (FabricLoader.getInstance().isModLoaded("parcool")) {
//            ParcoolInstalled = true;
//            ParcoolCompat.load();
//        }
//        if (FabricLoader.getInstance().isModLoaded("elenaidodge2")) {
//            ElenaiDodge2Installed = true;
//            ElenaiDodge2Compat.load();
//        }
//        if (FabricLoader.getInstance().isModLoaded("irons_spellbooks")) {
//            IronsSpellsnSpellbooksInstalled = true;
//            IronsSpellsnSpellbooksCompat.load();
//        }
//        if (FabricLoader.getInstance().isModLoaded("ars_nouveau")) {
//            ArsNouveauInstalled = true;
//            ArsNouveauCompat.load();
//        }
//        if (FabricLoader.getInstance().isModLoaded("mna")) {
//            ManaAndArtificeInstalled = true;
//            ManaAndArtificeCompat.load();
//        }
        // if (FabricLoader.getInstance().isModLoaded("knights_of_britannia")) {
        //     KnightsOfBritanniaInstalled = true;
        //     KnightsOfBritanniaCompat.load();
        // }
//        if (FabricLoader.getInstance().isModLoaded("playerrevive")) {
//            PlayerReviveInstalled = true;
//            PlayerReviveCompat.load();
//        }
        if (FabricLoader.getInstance().isModLoaded("voicechat")) {
            VoiceChatInstalled = true;
            SimpleVoiceChatCompat.load();
        }
        if (FabricLoader.getInstance().isModLoaded("tacz")) {
//            VoiceChatInstalled = true;
            TacZCompat.load();
        }
    }

    private void commonSetup() {
        LOGGER.info("Running commmon setup for Cuffed");

        CuffedAPI.Networking.registerPackets();

        ModStatistics.setup();

        new ModServerEvents();

        // Register Dispenser
        DispenseItemBehavior dispenseitembehavior = new OptionalDispenseItemBehavior() {
            @Override
            protected ItemStack execute(@NotNull BlockSource source, @NotNull ItemStack stack) {
                this.setSuccess(AbstractRestraintItem.dispenseRestraint(source, stack));
                if (this.isSuccess()) {
                    stack.shrink(1);
                }
                return stack;
            }
        };
        DispenserBlock.registerBehavior(ModItems.HANDCUFFS, dispenseitembehavior);
        DispenserBlock.registerBehavior(ModItems.FUZZY_HANDCUFFS, dispenseitembehavior);
        DispenserBlock.registerBehavior(ModItems.SHACKLES, dispenseitembehavior);
        DispenserBlock.registerBehavior(Items.BUNDLE, dispenseitembehavior);
    }

    public void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            new HandcuffCommand(dispatcher, registryAccess);
            new CuffedDebugCommand(dispatcher, registryAccess);

//            ConfigCommand.register(event.getDispatcher());
        });
    }

    public static class ClientModEvents {
        public static void onClientSetup() {
            LOGGER.info("Running client setup for Cuffed");

            ItemProperties.register(ModItems.KEY_RING, ResourceLocation.fromNamespaceAndPath(MODID, "keys"), (stack, level, living, id) ->
                stack.getOrDefault(CuffedDataComponents.KEY_COUNT, 0)
            );

            ItemProperties.register(ModItems.POSSESSIONSBOX, ResourceLocation.fromNamespaceAndPath(MODID, "filled"), (stack, level, living, id) -> {
                var items = stack.get(CuffedDataComponents.POSSESSIONS_ITEMS);
                if (items == null)
                    return 0;

                return items.size();
            });

            ItemProperties.register(ModItems.TRAY, ResourceLocation.fromNamespaceAndPath(MODID, "filled"), (stack, level, living, id) ->
                TrayItem.trayHasFoodItem(stack) || TrayItem.trayHasSpoon(stack)
                    || TrayItem.trayHasFork(stack) || TrayItem.trayHasKnife(stack) ? 1 : 0
            );

            ItemProperties.register(ModItems.POSTER_ITEM, ResourceLocation.fromNamespaceAndPath(MODID, "poster"), (stack, level, living, id) ->
                PosterType.getfromItem(stack).toInt()
            );

            MenuScreens.register(ModMenuTypes.FRISKING_MENU, FriskingScreen::new);

            new ModClientEvents();
        }

        static {
            registerTooltip();
            onRegisterLayers();
            onRegisterParticles();
            onRegisterRenderers();
        }

        public static void registerTooltip() {
            TooltipComponentCallback.EVENT.register(data -> {
                if (data instanceof PossessionsBoxTooltip || data instanceof TrayTooltip) {
                    return (ClientTooltipComponent) data;
                }

                return null;
            });
        }

        public static void onRegisterLayers() {
            ModModelLayers.registerLayers();
        }

        public static void onRegisterParticles() {
            ModParticleTypes.registerSprites();
        }

        public static void onRegisterRenderers() {
            EntityRendererRegistry.register(ModEntityTypes.CHAIN_KNOT, ChainKnotEntityRenderer::new);
            EntityRendererRegistry.register(ModEntityTypes.PADLOCK, PadlockEntityRenderer::new);
            EntityRendererRegistry.register(ModEntityTypes.WEIGHTED_ANCHOR, WeightedAnchorEntityRenderer::new);
            EntityRendererRegistry.register(ModEntityTypes.CRUMBLING_BLOCK, CrumblingBlockRenderer::new);

            BlockEntityRenderers.register(ModBlockEntities.GUILLOTINE, GuillotineBlockEntityRenderer::new);
            BlockEntityRenderers.register(ModBlockEntities.TRAY, TrayBlockEntityRenderer::new);
            //event.registerBlockEntityRenderer(ModBlockEntities.TOILET, ToiletBlockEntityRenderer::new);
        }
    }
}
