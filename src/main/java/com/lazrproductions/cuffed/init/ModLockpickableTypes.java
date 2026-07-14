package com.lazrproductions.cuffed.init;

import com.lazrproductions.cuffed.CuffedMod;
import com.lazrproductions.cuffed.api.lock.Lockpickable;
import com.lazrproductions.cuffed.api.lock.LockpickableType;
import com.lazrproductions.cuffed.lock.CellDoorLockpickable;
import com.lazrproductions.cuffed.lock.LockLockpickable;
import com.lazrproductions.cuffed.lock.RestraintLockpickable;
import net.minecraft.core.Registry;

public class ModLockpickableTypes {
    public static final LockpickableType<CellDoorLockpickable> CELL_DOOR = register("cell_door", CellDoorLockpickable.TYPE);
    public static final LockpickableType<LockLockpickable> LOCK = register("lock", LockLockpickable.TYPE);
    public static final LockpickableType<RestraintLockpickable> RESTRAINT = register("restraint", RestraintLockpickable.TYPE);

    private static <T extends Lockpickable> LockpickableType<T> register(String name, LockpickableType<T> type) {
        return Registry.register(LockpickableType.REGISTRY, CuffedMod.id(name), type);
    }

    public static void register() {}
}
