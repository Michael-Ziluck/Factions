package net.dnddev.factions.store;

import java.util.List;

import net.dnddev.factions.base.Faction;

/**
 * The FactionStore designed to reduce memory usage.
 * 
 * @author Michael Ziluck
 */
public abstract class MemoryFactionStore extends LoadFactionStore
{

    /**
     * A list of all the factions.
     */
    protected List<Faction> factions;

}
