package net.dnddev.factions.data;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import net.dnddev.factions.base.User;
import net.dnddev.factions.base.UserStore;
import net.dnddev.factions.configuration.Config;
import net.dnddev.factions.configuration.struct.Optimization;

/**
 * The in-memory implementation of a UserStore
 * 
 * @author Michael Ziluck
 */
public abstract class LoadUserStore implements UserStore
{

    protected User console;

    protected HashMap<UUID, User> onlineUsersMap;
    protected List<User> onlineUsersList;

    protected abstract void createConsole();

    @Override
    public User getUser(UUID uuid)
    {
        if (Config.OPTIMIZATION.getValue() == Optimization.PROCESS)
        {
            return onlineUsersMap.get(uuid);
        }
        else
        {
            return searchList(user -> user.getUniqueId().equals(uuid));
        }
    }

    @Override
    public User getUser(String name)
    {
        Predicate<User> predicate = user -> user.getName().equalsIgnoreCase(name);
        if (Config.OPTIMIZATION.getValue() == Optimization.PROCESS)
        {
            return searchMap(predicate);
        }
        else
        {
            return searchList(predicate);
        }
    }

    @Override
    public User getUser(long id)
    {
        Predicate<User> predicate = user -> user.getId() == id;
        if (Config.OPTIMIZATION.getValue() == Optimization.PROCESS)
        {
            return searchMap(predicate);
        }
        else
        {
            return searchList(predicate);
        }
    }

    @Override
    public User getUser(CommandSender sender)
    {
        if (sender instanceof ConsoleCommandSender)
        {
            return getConsole();
        }
        else if (sender instanceof Player)
        {
            Predicate<User> predicate = user -> user.getUniqueId() == ((Player) sender).getUniqueId();
            if (Config.OPTIMIZATION.getValue() == Optimization.PROCESS)
            {
                return searchMap(predicate);
            }
            else
            {
                return searchList(predicate);
            }
        }
        else
        {
            return null;
        }
    }

    // TODO add javadoc
    private User searchMap(Predicate<User> predicate)
    {
        for (User user : onlineUsersMap.values())
        {
            if (predicate.test(user))
            {
                return user;
            }
        }
        return null;
    }

    // TODO add javadoc
    private User searchList(Predicate<User> predicate)
    {
        for (User user : onlineUsersList)
        {
            if (predicate.test(user))
            {
                return user;
            }
        }
        return null;
    }

    @Override
    public User getConsole()
    {
        return console;
    }

    @Override
    public User loadUser(Player player)
    {
        User user = getUser(player, true);
        if (user == null)
        {
            user = createUser(player);
        }

        if (Config.OPTIMIZATION.getValue() == Optimization.PROCESS)
        {
            onlineUsersMap.put(player.getUniqueId(), user);
        }
        else
        {
            onlineUsersList.add(user);
        }

        return user;
    }

    @Override
    public User loadUser(long id)
    {
        if (id == -1)
        {
            return console;
        }
        User user = getUser(id, true);
        if (user == null)
        {
            throw new IllegalArgumentException("No User by that id exists.");
        }

        if (Config.OPTIMIZATION.getValue() == Optimization.PROCESS)
        {
            onlineUsersMap.put(user.getUniqueId(), user);
        }
        else
        {
            onlineUsersList.add(user);
        }

        return user;
    }

    @Override
    public void initialize()
    {
        createConsole();
    }

}
