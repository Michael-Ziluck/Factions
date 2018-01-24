package net.dnddev.factions.base;

import java.util.Collection;
import java.util.UUID;

import net.dnddev.factions.Factions;

/**
 * The base system to store and load Users.
 * 
 * @author Michael Ziluck
 */
public interface UserStore
{

    /**
     * @return the singleton instance stored in the Factions main class.
     */
    public static UserStore getInstance()
    {
        return Factions.getInstance().getUserStore();
    }

    /**
     * @return all online users.
     */
    public Collection<User> getOnlineUsers();

    /**
     * Gets an online User based on the given UUID.
     * 
     * @param uuid the uuid to look for.
     * @return the User if one is found.
     */
    public User getUser(UUID uuid);

    /**
     * Gets an online User based on their name.
     * 
     * @param name the name of the User.
     * @return the User if one is found.
     */
    public User getUser(String name);

    /**
     * Gets an online User based on their internal id.
     * 
     * @param id the id of the User.
     * @return the User if one is found.
     */
    public User getUser(long id);

    /**
     * Gets a User based on their uuid. If includeOffline is set to true, it will also retrieve offline Users.
     * 
     * @param uuid the uuid of the User.
     * @param includeOffline whether to include offline Users or not.
     * @return the User if they have ever connected.
     */
    public User getUser(UUID uuid, boolean includeOffline);

    /**
     * Gets a User based on their name. If includeOffline is set to true, it will also retrieve offline Users.
     * 
     * @param name the name of the User.
     * @param includeOffline whether to include offline Users or not.
     * @return the User if they have ever connected.
     */
    public User getUser(String name, boolean includeOffline);

    /**
     * Gets a User based on their internal id. If includeOffline is set to true, it will also retrieve offline Users.
     * 
     * @param id the id of the User.
     * @param includeOffline whether to include offline Users or not.
     * @return the User if they have ever connected.
     */
    public User getUser(long id, boolean includeOffline);

}
