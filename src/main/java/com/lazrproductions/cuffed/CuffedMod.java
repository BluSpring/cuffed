package com.lazrproductions.cuffed;

import java.util.function.Function;

import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lazrproductions.cuffed.api.CuffedAPI;
import com.lazrproductions.cuffed.blocks.base.PosterType;
import com.lazrproductions.cuffed.blocks.entity.renderer.GuillotineBlockEntityRenderer;
import com.lazrproductions.cuffed.blocks.entity.renderer.TrayBlockEntityRenderer;
import com.lazrproductions.cuffed.cap.RestrainableCapability;
import com.lazrproductions.cuffed.client.gui.screen.FriskingScreen;
import com.lazrproductions.cuffed.command.CuffedDebugCommand;
import com.lazrproductions.cuffed.command.HandcuffCommand;
import com.lazrproductions.cuffed.compat.ArsNouveauCompat;
import com.lazrproductions.cuffed.compat.BetterCombatCompat;
import com.lazrproductions.cuffed.compat.ElenaiDodge2Compat;
import com.lazrproductions.cuffed.compat.EpicFightCompat;
import com.lazrproductions.cuffed.compat.IronsSpellsnSpellbooksCompat;
import com.lazrproductions.cuffed.compat.KnightsOfBritanniaCompat;
import com.lazrproductions.cuffed.compat.ManaAndArtificeCompat;
import com.lazrproductions.cuffed.compat.ParcoolCompat;
import com.lazrproductions.cuffed.compat.PlayerReviveCompat;
import com.lazrproductions.cuffed.compat.SimpleVoiceChatCompat;
import com.lazrproductions.cuffed.compat.TacZCompat;
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
import com.lazrproductions.cuffed.init.ModMenuTypes;
import com.lazrproductions.cuffed.init.ModModelLayers;
import com.lazrproductions.cuffed.init.ModParticleTypes;
import com.lazrproductions.cuffed.init.ModRecipes;
import com.lazrproductions.cuffed.init.ModRestraints;
import com.lazrproductions.cuffed.init.ModSounds;
import com.lazrproductions.cuffed.init.ModStatistics;
import com.lazrproductions.cuffed.inventory.tooltip.PossessionsBoxTooltip;
import com.lazrproductions.cuffed.inventory.tooltip.TrayTooltip;
import com.lazrproductions.cuffed.items.KeyRingItem;
import com.lazrproductions.cuffed.items.PossessionsBox;
import com.lazrproductions.cuffed.items.TrayItem;
import com.lazrproductions.cuffed.items.base.AbstractRestraintItem;
import com.lazrproductions.cuffed.restraints.RestraintAPI;
import com.lazrproductions.cuffed.restraints.base.AbstractRestraint;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.DispenserBlock;
import org.jetbrains.annotations.NotNull;

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

//        this.registerCaps();
        this.registerSounds();
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
                if(this.isSuccess())
                    stack.shrink(1);
                return stack;
            }
        };
        DispenserBlock.registerBehavior(ModItems.HANDCUFFS, dispenseitembehavior);
        DispenserBlock.registerBehavior(ModItems.FUZZY_HANDCUFFS, dispenseitembehavior);
        DispenserBlock.registerBehavior(ModItems.SHACKLES, dispenseitembehavior);
        DispenserBlock.registerBehavior(Items.BUNDLE, dispenseitembehavior);
    }

    private void registerSounds() {
        LOGGER.info("Registering sound for Cuffed");
        ModSounds.register();

        // TODO
//        IForgeRegistry<?> r = event.getForgeRegistry();
//        if(r != null && r.getValues().size() > 0 && r.getValues().toArray()[0] instanceof AbstractRestraint) {
//            LOGGER.info("Cuffed has found a foreign restraint registry, registering with Restraint API");
//            RestraintAPI.Registries.register(r);
//        }
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

            ItemProperties.register(ModItems.KEY_RING.get(),
                    ResourceLocation.fromNamespaceAndPath(MODID, "keys"), (stack, level, living, id) -> {
                        var tag = stack.getTag();
                        if (tag != null && tag.contains(KeyRingItem.TAG_KEYS))
                            return tag.getInt(KeyRingItem.TAG_KEYS);
                        return 0;
                    });
            ItemProperties.register(ModItems.POSSESSIONSBOX.get(),
                    ResourceLocation.fromNamespaceAndPath(MODID, "filled"), (stack, level, living, id) -> {
                        CompoundTag compoundtag = stack.getOrCreateTag();
                        if (!compoundtag.contains(PossessionsBox.TAG_ITEMS)) {
                            return 0;
                        } else {
                            ListTag listtag = compoundtag.getList(PossessionsBox.TAG_ITEMS, 10);
                            return listtag.size() > 0 ? 1 : 0;
                        }
                    });
            ItemProperties.register(ModItems.TRAY.get(),
                    ResourceLocation.fromNamespaceAndPath(MODID, "filled"), (stack, level, living, id) -> {
                        return TrayItem.trayHasFoodItem(stack) || TrayItem.trayHasSpoon(stack)
                                || TrayItem.trayHasFork(stack) || TrayItem.trayHasKnife(stack) ? 1 : 0;
                    });
            ItemProperties.register(ModItems.POSTER_ITEM.get(),
                    ResourceLocation.fromNamespaceAndPath(MODID, "poster"), (stack, level, living, id) -> {
                        return PosterType.getfromItem(stack).toInt();
                    });

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
                if (data instanceof PossessionsBoxTooltip || data instanceof TrayTooltip)
                    return (ClientTooltipComponent) data;

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
            //event.registerBlockEntityRenderer(ModBlockEntities.TOILET.get(), ToiletBlockEntityRenderer::new);
        }
    }
}