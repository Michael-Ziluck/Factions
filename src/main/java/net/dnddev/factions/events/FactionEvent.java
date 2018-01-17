package net.dnddev.factions.events;

import org.bukkit.event.Event;

import net.dnddev.factions.base.Faction;

/**
 * An event related to Factions.
 * 
 * @author Michael Ziluck
 */
public abstract class FactionEvent extends Event
{

    protected Faction faction;

    /**
     * Retrieves the Faction involved with this event.
     * 
     * @return the Faction involved with this event.
     */
    public Faction getFaction()
    {
        return faction;
    }

}
