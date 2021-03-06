package com.ziluck.factions;

import java.util.List;
import java.util.UUID;

import com.ziluck.factions.api.commands.CommandHandler;
import com.ziluck.factions.base.Faction;
import com.ziluck.factions.base.FactionStore;
import com.ziluck.factions.base.User;
import com.ziluck.factions.base.UserStore;
import com.ziluck.factions.data.mongodb.MongoFactionStore;
import com.ziluck.factions.data.mongodb.MongoUserStore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.ziluck.factions.commands.FactionsBaseCommand;
import com.ziluck.factions.configuration.Config;
import com.ziluck.factions.configuration.struct.Storage;
import com.ziluck.factions.listeners.ConnectionListener;
import com.ziluck.factions.spatial.BlockColumn;
import com.ziluck.factions.spatial.BoundedArea;
import com.ziluck.factions.spatial.LazyLocation;

/**
 * The base plugin for Factions.
 * <p>
 * This class has a ton of convenience methods for retrieving things from the default FactionStore. Although it is not
 * required, it is simpler to just use the static convenience methods within here.
 * </p>
 *
 * @author Michael Ziluck
 */
public class Factions extends JavaPlugin
{

    /**
     * The UUID used to represent the console.
     */
    public static final UUID consoleUuid = UUID.fromString("10101010-1010-1010-1010-101010101010");

    private static Factions instance;

    private FactionStore factionStore;
    private UserStore    userStore;

    @Override
    public void onEnable()
    {
        instance = this;

        processFiles();

        // UserStores must be loaded first as when the Wilderness is created, it uses the console user.
        if (Config.STORAGE_TYPE.getValue() == Storage.MONGODB)
        {
            userStore = new MongoUserStore();
            factionStore = new MongoFactionStore();
        }

        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable()
    {

    }

    private void processFiles()
    {
        saveDefaultConfig();
        Config.update();
    }

    private void registerCommands()
    {
        CommandHandler.initialize();
        CommandHandler handler = CommandHandler.getInstance();

        handler.registerCommand(new FactionsBaseCommand(), this);
    }

    private void registerListeners()
    {
        Bukkit.getPluginManager().registerEvents(new ConnectionListener(), this);
    }

    /**
     * @return the currently used FactionStore.
     */
    public FactionStore getFactionStore()
    {
        return factionStore;
    }

    /**
     * @return the currently used UserStore.
     */
    public UserStore getUserStore()
    {
        return userStore;
    }

    /**
     * Retrieves the current singleton instance of {@link Factions}. It is important to note that this is set in the
     * {@link #onEnable()}, so all other plugins must have a dependency or soft-dependency for Factions for this to be
     * reliable.
     *
     * @return the current singleton instance of Factions.
     */
    public static Factions getInstance()
    {
        return instance;
    }

    /**
     * Gets a {@link Faction} referenced by its id. If one is not found, this will return null.
     *
     * @param id the id of the {@link Faction}.
     *
     * @return the {@link Faction} if one exists.
     */
    public static Faction getFaction(long id)
    {
        return getInstance().getFactionStore().getFaction(id);
    }

    /**
     * Gets a {@link Faction} referenced by its name. If one is not found, this will return null.
     *
     * @param name the name of the {@link Faction}.
     *
     * @return the {@link Faction} if one exists.
     */
    public static Faction getFaction(String name)
    {
        return getInstance().getFactionStore().getFaction(name);
    }

    /**
     * Gets a Faction referenced by a player's UUID. If the user is not found, this will return null. If the User has no
     * faction, this will return the Wilderness.
     *
     * @param uuid the uuid of the Player.
     *
     * @return the Faction if one exists.
     */
    public static Faction getFaction(UUID uuid)
    {
        return getInstance().getFactionStore().getFaction(uuid);
    }

    /**
     * Gets a Faction that has a claim at a particular Location. If one is not found, this will return Wilderness, not null.
     *
     * @param location the location of the faction.
     *
     * @return the Faction if one exists.
     */
    public static Faction getFaction(Location location)
    {
        return getInstance().getFactionStore().getFaction(location);
    }

    /**
     * Gets a Faction that has a claim at a particular LazyLocation. If one is not found, this will return Wilderness, not
     * null.
     *
     * @param location the location of the faction.
     *
     * @return the Faction if one exists.
     */
    public static Faction getFaction(LazyLocation location)
    {
        return getInstance().getFactionStore().getFaction(location);
    }

    /**
     * Gets a Faction that has a claim at a particular BlockColumn. If one is not found, this will return Wilderness, not
     * null.
     *
     * @param column the BlockColumn of the faction.
     *
     * @return the Faction if one exists.
     */
    public static Faction getFaction(BlockColumn column)
    {
        return getInstance().getFactionStore().getFaction(column);
    }

    /**
     * Gets all Factions that have claims within the given bounded area. If one is not found, this returns an <b>EMPTY</b>
     * List; it will not return a List with the Wilderness in it. This method will never return null.
     *
     * @param area the area to search within.
     *
     * @return all Factions if any exist.
     */
    public static List<Faction> getFactions(BoundedArea area)
    {
        return getInstance().getFactionStore().getFactions(area);
    }

    /**
     * Gets the Faction of the given User. If one is not found, this will return Wilderness, not null.
     *
     * @param user the user.
     *
     * @return the Faction if one exists.
     */
    public static Faction getFaction(User user)
    {
        return getInstance().getFactionStore().getFaction(user);
    }

    /**
     * Gets the Faction of the given Player. If one is not found, this will return Wilderness, not null.
     *
     * @param player the player.
     *
     * @return the Faction if one exists.
     */
    public static Faction getFaction(Player player)
    {
        return getInstance().getFactionStore().getFaction(player);
    }

    /**
     * Gets the {@link User} with the given {@link UUID}. If one is not found, this will return null.
     *
     * @param uuid the uuid of the {@link User}.
     *
     * @return the {@link User} with the given {@link UUID}.
     */
    public static User getUser(UUID uuid)
    {
        return getInstance().getUserStore().getUser(uuid);
    }

    /**
     * Broadcast a message to all players.
     * <p>
     * This is the same as calling {@link Bukkit#broadcastMessage(String)} for all the messages in the list.
     *
     * @param messages the messages
     */
    public static void broadcastMessage(String... messages)
    {
        for (String message : messages)
        {
            Bukkit.broadcastMessage(message);
        }
    }

}
