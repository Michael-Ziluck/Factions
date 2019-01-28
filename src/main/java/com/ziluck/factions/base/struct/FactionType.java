package com.ziluck.factions.base.struct;

import com.ziluck.factions.configuration.Config;
import com.ziluck.factions.utils.MutableString;

/**
 * Represents the purpose for each Faction. Each enum has a description for the function of each Type.
 *
 * @author Michael Ziluck
 */
public enum FactionType
{
    /**
     * A normal Faction created and managed by a normal User.
     */
    NORMAL(Config.FACTION_NAME_NORMAL),

    /**
     * Area designed for peace. This is one of the two types of system Factions. A Safezone is set up so that
     * administrators can create an area that can not be claimed by Users, that also blocks combat.
     */
    SAFEZONE(Config.FACTION_NAME_SAFEZONE),

    /**
     * Area designed for combat. This is one of the two types of system Factions. A Warzone is set up so that
     * administrators can create an area that can not be claimed by Users, but also is able to have combat.
     */
    WARZONE(Config.FACTION_NAME_WARZONE),

    /**
     * Unincorporated land. All players without a Faction are part of the Wilderness, and all land that is not
     * claimed is also part of the Wilderness.
     */
    WILDERNESS(Config.FACTION_NAME_WILDERNESS);

    private MutableString name;

    FactionType(MutableString name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name.getValue();
    }
}
