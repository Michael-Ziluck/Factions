package net.dnddev.factions.store;

import java.util.Map;

import net.dnddev.factions.base.Faction;

public abstract class ProcessFactionStore extends LoadFactionStore
{

    /**
     * A map of all the factions, referenced by their UUID.
     */
    protected Map<String, Faction> factions;


}
