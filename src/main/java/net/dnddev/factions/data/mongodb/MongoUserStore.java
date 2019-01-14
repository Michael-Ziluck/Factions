package net.dnddev.factions.data.mongodb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import net.dnddev.factions.Factions;
import net.dnddev.factions.base.User;
import net.dnddev.factions.configuration.Config;
import net.dnddev.factions.configuration.struct.Optimization;
import net.dnddev.factions.data.LoadUserStore;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jongo.MongoCollection;

/**
 * The UserStore for interacting with MongoDB.
 *
 * @author Michael Ziluck
 */
public class MongoUserStore extends LoadUserStore
{

    protected MongoCollection store;

    protected long nextId;

    /**
     * Create a new MongoUserStore
     */
    public MongoUserStore()
    {

        store = MongoWrapper.getInstance().getJongo().getCollection("users");

        if (Config.OPTIMIZATION.getValue() == Optimization.MEMORY)
        {
            onlineUsersList = new ArrayList<>();
        }
        else if (Config.OPTIMIZATION.getValue() == Optimization.PROCESS)
        {
            onlineUsersMap = new HashMap<>();
        }

        MongoUser user = store.findOne().orderBy("{_id: -1}").as(MongoUser.class);
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
    public User getUser(UUID uuid, boolean includeOffline)
    {
        User user = getUser(uuid);
        if (includeOffline && user == null)
        {
            user = store.findOne("{uid: '" + uuid.toString() + "'}").as(MongoUser.class);
        }
        return user;
    }

    @Override
    public User getUser(String name, boolean includeOffline)
    {
        User user = getUser(name);
        if (includeOffline && user == null)
        {
            user = store.findOne("{name: '" + name + "'}").as(MongoUser.class);
        }
        return user;
    }

    @Override
    public User getUser(long id, boolean includeOffline)
    {
        if (id == -1)
        {
            return console;
        }
        User user = getUser(id);
        if (includeOffline && user == null)
        {
            user = store.findOne("{_id: " + id + "}").as(MongoUser.class);
        }
        return user;
    }

    @Override
    public User getUser(CommandSender sender, boolean includeOffline)
    {
        User user = getUser(sender);
        if (user == null)
        {
            if (sender instanceof ConsoleCommandSender)
            {
                user = getConsole();
            }
            else if (sender instanceof Player)
            {
                user = store.findOne("{uid: '" + ((Player) sender).getUniqueId() + "'}").as(MongoUser.class);
            }
        }
        return user;
    }

    @Override
    protected void createConsole()
    {
        long id = -1;
        this.console = new MongoUser(id, Factions.consoleUuid, "CONSOLE");
        this.console.save();
    }

    @Override
    public void save(User user)
    {
        if (!(user instanceof MongoUser))
        {
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(Factions.getInstance(), () -> store.save(user));
    }

    @Override
    public User createUser(Player player)
    {
        MongoUser user = new MongoUser(nextId, player.getUniqueId(), player.getName());
        user.save();
        return user;
    }

}
