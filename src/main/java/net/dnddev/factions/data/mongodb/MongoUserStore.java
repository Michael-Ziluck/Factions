package net.dnddev.factions.data.mongodb;

import java.util.Collection;
import java.util.UUID;

import org.jongo.MongoCollection;

import net.dnddev.factions.base.User;
import net.dnddev.factions.configuration.Config;
import net.dnddev.factions.configuration.struct.Optimization;
import net.dnddev.factions.data.LoadUserStore;

/**
 * The UserStore for interacting with MongoDB.
 * 
 * @author Michael Ziluck
 */
public class MongoUserStore extends LoadUserStore
{

    private MongoCollection store;

    @Override
    public Collection<User> getOnlineUsers()
    {
        if (Config.OPTIMIZATION.getValue() == Optimization.PROCESS)
        {
            return this.onlineUsersMap.values();
        }
        else
        {
            return this.onlineUsersList;
        }
    }

    @Override
    public User getUser(UUID uuid, boolean includeOffline)
    {
        User user = getUser(uuid);
        if (includeOffline && user == null)
        {
            user = store.findOne("{uuid: '" + uuid.toString() + "'}").as(MongoUser.class);
        }
        return user;
    }

    @Override
    public User getUser(String name, boolean includeOffline)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
