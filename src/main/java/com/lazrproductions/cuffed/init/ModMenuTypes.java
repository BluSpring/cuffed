package com.lazrproductions.cuffed.init;

import com.lazrproductions.cuffed.CuffedMod;
import com.lazrproductions.cuffed.inventory.FriskingMenu;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class ModMenuTypes {
    public static final MenuType<FriskingMenu> FRISKING_MENU = register("frisking_menu",
        new MenuType<>(FriskingMenu::new, FeatureFlags.REGISTRY.allFlags()));

    private static <T extends AbstractContainerMenu> MenuType<T> register(String name, MenuType<T> type) {
        return Registry.register(BuiltInRegistries.MENU, CuffedMod.id(name), type);
    }

    public static void register() {
    }
}
