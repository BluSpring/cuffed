package com.lazrproductions.cuffed.entity.base;

import com.lazrproductions.cuffed.CuffedMod;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

public interface IPrivacyOperand {

    String RESTRAINING_RESTRICTION = CuffedMod.MODID + ":restraining";
    String NICKNAMING_RESTRICTION = CuffedMod.MODID + ":nicknaming";
    String ANCHORING_RESTRICTION = CuffedMod.MODID + ":anchoring";
    String DETAINING_RESTRICTION = CuffedMod.MODID + ":detaining";

    String TAG_RESTRICTIONS = "PrivacyRestrictions";
    String TAG_RESTRICTION_ID = "Id";
    String TAG_RESTRICTION = "RestrictionLevel";

    void setRestrainingRestrictions(PrivacyRestriction newRestriction);

    void setNicknamingRestrictions(PrivacyRestriction newRestriction);

    void setAnchoringRestrictions(PrivacyRestriction newRestriction);

    void setDetainingRestrictions(PrivacyRestriction newRestriction);

    void setRestriction(String key, PrivacyRestriction newRestriction);



    PrivacyRestriction getRestriction(String key);

    PrivacyRestriction getRestrainingRestrictions();

    PrivacyRestriction getNicknamingRestrictions();

    PrivacyRestriction getAnchoringRestrictions();

    PrivacyRestriction getDetainingRestrictions();

    ListTag serializeRestrictions();

    void deserializeRestrictions(CompoundTag tag);

    void writeDefaultRestrictions();

    enum PrivacyRestriction {
        ALWAYS,
        ASK,
        ONLY_WHEN_RESTRAINED,
        NEVER;

        public static PrivacyRestriction fromInteger(int integer) {
            switch (integer) {
                case 0:
                    return ALWAYS;
                case 1:
                    return ASK;
                case 2:
                    return ONLY_WHEN_RESTRAINED;
                default:
                    return NEVER;
            }
        }

    
        public int toInteger() {
            switch (this) {
                case ALWAYS:
                    return 0;
                case ASK:
                    return 1;
                case ONLY_WHEN_RESTRAINED:
                    return 2;
                default:
                    return 3;
            }
        }
    }
}
