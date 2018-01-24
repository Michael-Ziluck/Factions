package net.dnddev.factions.data;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

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

    protected HashMap<UUID, User> onlineUsersMap;
    protected List<User> onlineUsersList;

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

}
