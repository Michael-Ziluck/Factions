package net.dnddev.factions.data.mongodb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import org.bukkit.entity.Player;
import org.jongo.MongoCollection;

import net.dnddev.factions.base.Faction;
import net.dnddev.factions.base.User;
import net.dnddev.factions.base.UserStore;
import net.dnddev.factions.configuration.Config;
import net.dnddev.factions.configuration.struct.Optimization;
import net.dnddev.factions.data.LoadFactionStore;

/**
 * Faction implementation for processing Factions from MongoDB.
 * 
 * @author Michael Ziluck
 */
public class MongoFactionStore extends LoadFactionStore
{

    /**
     * Used if the system optimizes to reduce processing power.
     */
    private HashMap<String, Faction> factionsByName;

    /**
     * Used if the system optimizes to reduce memory usage.
     */
    private List<Faction> factionsList;

    private MongoCollection store;

    /**
     * Construct a new MongoFactionStore. This will grab the information from the config file.
     */
    public MongoFactionStore()
    {
        super();

        store = MongoWrapper.getInstance().getJongo().getCollection("factions");

        int count = Math.toIntExact(store.count());

        if (Config.OPTIMIZATION.getValue() == Optimization.MEMORY)
        {
            factionsList = new ArrayList<>(count);
        }
        else if (Config.OPTIMIZATION.getValue() == Optimization.PROCESS)
        {
            factionsByName = new HashMap<>(count);
        }

        // TODO load factions from the database.
    }

    @Override
    public Faction getFaction(final String name)
    {
        Faction faction = null;
        if (Config.OPTIMIZATION.getValue() == Optimization.MEMORY)
        {
            faction = searchList(f -> f.getStub().equals(name.toLowerCase()));
        }
        else if (Config.OPTIMIZATION.getValue() == Optimization.PROCESS)
        {
            faction = factionsByName.get(name.toLowerCase());
        }
        return faction == null ? WILDERNESS : faction;
    }

    @Override
    public Faction getCasedFaction(String name)
    {
        Faction faction = null;
        if (Config.OPTIMIZATION.getValue() == Optimization.MEMORY)
        {
            faction = searchList(f -> f.getStub().equals(name.toLowerCase()));
        }
        else if (Config.OPTIMIZATION.getValue() == Optimization.PROCESS)
        {
            faction = factionsByName.get(name.toLowerCase());
            if (faction != null && !faction.getName().equals(name))
            {
                faction = null;
            }
        }
        return faction == null ? WILDERNESS : faction;
    }

    @Override
    public Faction getFaction(UUID uuid)
    {
        User user = UserStore.getInstance().getUser(uuid);
        if (user == null)
        {
            return null;
        }
        return user.getFaction();
    }

    @Override
    public Faction getFaction(User user)
    {
        return user.getFaction();
    }

    @Override
    public Faction getFaction(Player player)
    {
        return UserStore.getInstance().getUser(player.getUniqueId(), true).getFaction();
    }

    // TODO add javadoc
    private Faction searchList(Predicate<Faction> predicate)
    {
        for (Faction faction : factionsList)
        {
            if (predicate.test(faction))
            {
                return faction;
            }
        }
        return null;
    }

    // TODO add javadoc
    @SuppressWarnings("unused")
    private Faction searchMap(Predicate<Faction> predicate)
    {
        for (Faction faction : factionsByName.values())
        {
            if (predicate.test(faction))
            {
                return faction;
            }
        }
        return null;
    }

    @Override
    public void loadFactions()
    {

    }

}
