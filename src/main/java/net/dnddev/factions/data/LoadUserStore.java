package net.dnddev.factions.data;

import java.util.Collection;
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
            Predicate<User> predicate = user -> user.getUniqueId().equals(((Player) sender).getUniqueId());
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

    /**
     * Searches through the online user map and finds one that matches the given predicate. If one
     * is not found, this returns null.
     *
     * @param predicate the predicate to match.
     * @return the found user if one exists.
     */
    private User searchMap(Predicate<User> predicate)
    {
        return onlineUsersMap.values().stream().filter(predicate).findFirst().orElse(null);
    }

    /**
     * Searches through the online user list and finds one that matches the given predicate. If one
     * is not found, this returns null.
     *
     * @param predicate the predicate to match.
     * @return the found user if one exists.
     */
    private User searchList(Predicate<User> predicate)
    {
        return onlineUsersList.stream().filter(predicate).findFirst().orElse(null);
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

        optimizeUserLoad(user);

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

        optimizeUserLoad(user);

        return user;
    }

    /**
     * Add the user to the appropriate data structure depending on which optimization method is chosen.
     *
     * @param user the user to added.
     */
    private void optimizeUserLoad(User user)
    {
        if (Config.OPTIMIZATION.getValue() == Optimization.PROCESS)
        {
            onlineUsersMap.put(user.getUniqueId(), user);
        }
        else
        {
            onlineUsersList.add(user);
        }
    }

    @Override
    public void initialize()
    {
        createConsole();
    }

}
