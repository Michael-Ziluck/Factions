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
     * A {@link Faction} that does not engage in pvp.
     */
    PEACEFUL,
    /**
     * A {@link Faction} that does not require an invitation to join.
     */
    OPEN,
    /**
     * A {@link Faction} that expires.
     */
    TEMPORARY,
    /**
     * A {@link Faction} that is owned by the server, not a player.
     */
    SYSTEM;

}
