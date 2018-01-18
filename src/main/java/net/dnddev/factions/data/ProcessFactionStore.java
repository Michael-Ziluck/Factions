package net.dnddev.factions.data;

import java.util.Map;

import net.dnddev.factions.base.Faction;

/**
 * The FactionStore designed to reduce processing power.
 * 
 * @author Michael Ziluck
 */
public abstract class ProcessFactionStore extends LoadFactionStore
{

    /**
     * A map of all the factions, referenced by their UUID.
     */
    protected Map<String, Faction> factions;

}
