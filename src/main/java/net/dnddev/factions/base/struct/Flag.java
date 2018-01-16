package net.dnddev.factions.base.struct;

import net.dnddev.factions.base.Faction;

/**
 * All flags that can be attributed to a Faction.
 * 
 * @author Michael Ziluck
 */
public enum Flag
{

    /**
     * Represents a {@link Faction} that does not engage in pvp.
     */
    PEACEFUL,
    /**
     * Represents a {@link Faction} that expires.
     */
    TEMPORARY,
    /**
     * Represents a {@link Faction} that is owned by the server, not a player.
     */
    SYSTEM;

}
