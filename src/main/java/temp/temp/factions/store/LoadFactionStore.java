package temp.temp.factions.store;

import java.util.List;

import temp.temp.factions.base.Faction;
import temp.temp.factions.base.FactionStore;

public abstract class LoadFactionStore implements FactionStore
{

    protected List<Faction> factions;

    public List<Faction> getFactions()
    {
        return factions;
    }

}
