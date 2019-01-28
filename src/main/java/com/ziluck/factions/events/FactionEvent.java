package com.ziluck.factions.events;

import com.ziluck.factions.base.Faction;
import org.bukkit.event.Event;

/**
 * An event related to Factions.
 * 
 * @author Michael Ziluck
 */
public abstract class FactionEvent extends Event
{

    protected Faction faction;

    /**
     * Constructs a new FactionEvent for the given Faction.
     * 
     * @param faction the Faction to which the event is related.
     */
    public FactionEvent(Faction faction)
    {
        this.faction = faction;
    }

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
