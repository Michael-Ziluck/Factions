package net.dnddev.factions.data.mongodb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import org.bukkit.entity.Player;
import org.jongo.MongoCollection;

import net.dnddev.factions.base.Faction;
import net.dnddev.factions.base.Faction.Type;
import net.dnddev.factions.base.User;
import net.dnddev.factions.base.UserStore;
import net.dnddev.factions.configuration.Config;
import net.dnddev.factions.configuration.struct.Optimization;
import net.dnddev.factions.data.LoadFactionStore;
import net.dnddev.factions.events.FactionCreateEvent;

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

        int count = Math.toIntExact(store.count() + 5);

        if (Config.OPTIMIZATION.getValue() == Optimization.MEMORY)
        {
            factionsList = new ArrayList<>(count);
        }
        else if (Config.OPTIMIZATION.getValue() == Optimization.PROCESS)
        {
            factionsByName = new HashMap<>(count);
        }

        loadFactions();
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
        return faction == null ? wilderness : faction;
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
        return faction == null ? wilderness : faction;
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
        for (MongoFaction faction : store.find().as(MongoFaction.class))
        {
            if (faction.getId() == -1)
            {
                wilderness = faction;
            }
            if (Config.OPTIMIZATION.getValue() == Optimization.PROCESS)
            {
                factionsByName.put(faction.getStub(), faction);
            }
            else
            {
                factionsList.add(faction);
            }
            // TODO load claims
        }
        if (wilderness == null)
        {
            wilderness = new MongoFaction(-1, "WILDERNESS", UserStore.getInstance().getConsole(), Type.WILDERNESS);
            wilderness.save();
        }

        MongoUser user = store.findOne().orderBy("{_id: -1}").as(MongoUser.class);
        if (user == null)
        {
            nextId = 0;
        }
        else
        {
            nextId = user.getId() + 1;
        }
    }

    @Override
    public void save(Faction faction)
    {
        store.save(faction);
    }

    @Override
    public synchronized FactionCreateEvent createFaction(User creator, String name, Type type)
    {
        MongoFaction faction = new MongoFaction(nextId, name, creator, type);

        FactionCreateEvent event = new FactionCreateEvent(faction, creator, Config.CREATE_COST.doubleValue());

        return event;
    }

}
