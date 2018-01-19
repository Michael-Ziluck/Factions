package net.dnddev.factions.data.mongodb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import com.conversantmedia.util.collection.spatial.RTree;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import net.dnddev.factions.base.Faction;
import net.dnddev.factions.base.User;
import net.dnddev.factions.base.claims.Claim;
import net.dnddev.factions.configuration.Config;
import net.dnddev.factions.configuration.struct.Optimization;
import net.dnddev.factions.data.LoadFactionStore;
import net.dnddev.factions.spatial.BlockColumn;
import net.dnddev.factions.spatial.BoundedArea;
import net.dnddev.factions.spatial.LazyLocation;

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

    private final Faction WILDERNESS;

    private ServerAddress addr;
    private MongoCredential creds;
    private MongoClient mc;
    private DB db;
    private Jongo jongo;
    private MongoCollection store;

    /**
     * Construct a new MongoFactionStore. This will grab the information from the config file.
     */
    @SuppressWarnings("deprecation")
    public MongoFactionStore()
    {
        WILDERNESS = new MongoFaction();
        addr = new ServerAddress(Config.DATABASE_HOSTNAME.getValue(), Config.DATABASE_PORT.intValue());

        creds = MongoCredential.createCredential(Config.DATABASE_USERNAME.getValue(), Config.DATABASE_DATABASE.getValue(), Config.DATABASE_PASSWORD.getValue().toCharArray());

        mc = new MongoClient(addr, creds, MongoClientOptions.builder().description("Factions MongoDB Connection").build());

        db = mc.getDB(Config.DATABASE_DATABASE.getValue());

        jongo = new Jongo(db);

        store = jongo.getCollection("factions");

        int count = Math.toIntExact(store.count());

        if (Config.OPTIMIZATION.getValue() == Optimization.MEMORY)
        {
            factionsList = new ArrayList<>(count);
        }
        else if (Config.OPTIMIZATION.getValue() == Optimization.PROCESS)
        {
            factionsByName = new HashMap<>(count);
        }
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
        Faction faction = null;
        if (Config.OPTIMIZATION.getValue() == Optimization.MEMORY)
        {
            faction = searchList(f -> f.getUniqueId().equals(uuid));
        }
        else if (Config.OPTIMIZATION.getValue() == Optimization.PROCESS)
        {
            faction = searchMap(f -> f.getUniqueId().equals(uuid));
        }
        return faction == null ? WILDERNESS : faction;
    }

    @Override
    public Faction getFaction(Location location)
    {
        RTree<Claim> tree = claims.get(location.getWorld().getUID());
        if (tree == null)
        {
            claims.put(location.getWorld().getUID(), new RTree<Claim>(null, 0, 0, null));
            return null;
        }
    }

    @Override
    public Faction getFaction(LazyLocation location)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Faction getFaction(BlockColumn column)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Faction> getFactions(BoundedArea area)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Faction getFaction(User user)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Faction getFaction(Player player)
    {
        // TODO Auto-generated method stub
        return null;
    }

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
