package com.lazrproductions.cuffed.entity.base;

import net.minecraft.resources.ResourceLocation;

public interface IRestrainableEntity {
    /** Get whether or not this entity is restrained. */
    boolean isRestrained();
    
    /** Get the restraint code for this entity. */
    int getRestraintCode();
    /** Get the arm restraint's id for this entity, cooresponding with each restraint item */
    ResourceLocation getArmRestraintId();
    /** Get the leg restraint's id for this entity, cooresponding with each restraint item */
    ResourceLocation getLegRestraintId();
    /** Get the head restraint's id for this entity, cooresponding with each restraint item */
    ResourceLocation getHeadRestraintId();

    /** Get whether or not the arm restraint is enchanted. */
    boolean getArmsAreEnchanted();
    /** Get whether or not the leg restraint is enchanted. */
    boolean getLegsAreEnchanted();
    /** Get whether or not the head restraint is enchanted. */
    boolean getHeadIsEnchanted();

    /** Set and automatically sync the restraint code to all clients and server. */
    void setRestraintCode(int v);
    /** Set and automatically sync the arm restraint id to all clients and server. */
    void setArmRestraintId(ResourceLocation v);
    /** Set and automatically sync the leg restraint id to all clients and server. */
    void setLegRestraintId(ResourceLocation v);
    /** Set and automatically sync the head restraint id to all clients and server. */
    void setHeadRestraintId(ResourceLocation v);
    
    /** Set and automatically sync whether or not the arms restraints are enchanted clients and server */
    void setArmsEnchanted(boolean v);
    /** Set and automatically sync whether or not the legs restraints are enchanted clients and server */
    void setLegsEnchanted(boolean v);
    /** Set and automatically sync whether or not the head restraints are enchanted clients and server */
    void setHeadEnchanted(boolean v);
}
