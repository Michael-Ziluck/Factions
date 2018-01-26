package net.dnddev.factions.base;

import java.util.Collection;
import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
     * Gets an online User based on the Bukkit CommandSender.
     * 
     * @param sender the Bukkit CommandSender.
     * @return the User if one is found.
     */
    public User getUser(CommandSender sender);

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

    /**
     * Gets an online User based on the Bukkit CommandSender. If inlucdeOffline is sset to true, it will also retrieve
     * offline Users.
     * 
     * @param sender the Bukkit CommandSender.
     * @param includeOffline whether to include offline Users or not.
     * @return the User if one is found.
     */
    public User getUser(CommandSender sender, boolean includeOffline);

    /**
     * Returns the User wrapper for the console.
     * 
     * @return the User wrapper for the console.
     */
    public User getConsole();

    /**
     * Load the User for the given Player. If it is a new Player, it will also create a new User.
     * 
     * @param player the payer to load.
     * @return the loaded User.
     */
    public User loadUser(Player player);

    /**
     * Creates a new User for the given Player.
     * <p>
     * Important to know that this method does <strong><i>not</i></strong> check if the Player already has a User so use
     * with caution.
     * </p>
     * 
     * @param player the player for whom to create a User.
     * @return the newly created User.
     */
    public User createUser(Player player);

    /**
     * Saves the given User to the database.
     * 
     * @param user the User to save.
     */
    public void save(User user);

    /**
     * Initialize UserStores with whatever they need to do when they are constructed.
     */
    public void initialize();

}
