package com.ziluck.factions.data.nitrite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.ziluck.factions.Factions;
import com.ziluck.factions.base.User;
import com.ziluck.factions.configuration.Config;
import com.ziluck.factions.configuration.struct.Optimization;
import com.ziluck.factions.data.LoadUserStore;
import com.ziluck.factions.data.mongodb.MongoUser;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.dizitart.no2.FindOptions;
import org.dizitart.no2.IndexOptions;
import org.dizitart.no2.IndexType;
import org.dizitart.no2.SortOrder;
import org.dizitart.no2.objects.ObjectRepository;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;

public class NitriteUserStore extends LoadUserStore
{
    private ObjectRepository<MongoUser> repository;

    private long nextId;

    public NitriteUserStore()
    {
        // create the repository
        repository = NitriteWrapper.getInstance().getNitrite().getRepository(MongoUser.class);

        // create index on id
        if (!repository.hasIndex("_id"))
        {
            repository.createIndex("_id", IndexOptions.indexOptions(IndexType.Unique, true));
        }
        // create index on UUID
        if (!repository.hasIndex("uid"))
        {
            repository.createIndex("uid", IndexOptions.indexOptions(IndexType.Unique, true));
        }
        // create non-unique index on name
        if (!repository.hasIndex("name"))
        {
            repository.createIndex("name", IndexOptions.indexOptions(IndexType.NonUnique, true));
        }

        if (Config.OPTIMIZATION.getValue() == Optimization.MEMORY)
        {
            onlineUsersList = new ArrayList<>();
        }
        else if (Config.OPTIMIZATION.getValue() == Optimization.PROCESS)
        {
            onlineUsersMap = new HashMap<>();
        }

        MongoUser user = repository.find(FindOptions.sort("_id", SortOrder.Descending)).firstOrDefault();
        if (user != null)
        {
            nextId = user.getId() + 1;
        }
        else
        {
            nextId = 0;
        }

        for (Player player : Bukkit.getOnlinePlayers())
        {
            loadUser(player);
        }

        initialize();
    }


    @Override
    protected void createConsole()
    {
        this.console = new MongoUser(-1, Factions.consoleUuid, "CONSOLE");
        this.console.save();
    }

    @Override
    public User getUser(UUID uuid, boolean includeOffline)
    {
        return getUser("uid", uuid, includeOffline, getUser(uuid));
    }

    @Override
    public User getUser(String name, boolean includeOffline)
    {
        return getUser("name", name, includeOffline, getUser(name));
    }

    @Override
    public User getUser(long id, boolean includeOffline)
    {
        return getUser("_id", id, includeOffline, getUser(id));
    }

    @Override
    public User getUser(CommandSender sender, boolean includeOffline)
    {
        User user = getUser(sender);
        if (user == null && sender instanceof Player)
        {
            return getUser(((Player) sender).getUniqueId(), includeOffline);
        }
        return user;
    }

    private User getUser(String fieldName, Object obj, boolean includeOffline, User user)
    {
        if (includeOffline && user == null)
        {
            user = repository.find(eq(fieldName, obj)).firstOrDefault();
        }
        return user;
    }

    @Override
    public User createUser(Player player)
    {
        MongoUser user = new MongoUser(nextId, player.getUniqueId(), player.getName());
        user.save();
        return user;
    }

    @Override
    public void save(User user)
    {
        if (!(user instanceof MongoUser))
        {
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(Factions.getInstance(), () -> repository.update((MongoUser) user, true));
    }
}
