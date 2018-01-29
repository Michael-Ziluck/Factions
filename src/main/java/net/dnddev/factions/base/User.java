package net.dnddev.factions.base;

import java.util.UUID;

import org.bukkit.entity.Player;

import net.dnddev.factions.base.struct.Role;
import net.dnddev.factions.spatial.LazyLocation;

/**
 * The base entity for anyone who interacts with the Faction system.
 * 
 * @author Michael Ziluck
 */
public interface User extends Messageable
{

    /**
     * Checks whether this User is currently online.
     * 
     * @return {@code true} if this User is currently online.
     */
    public boolean isOnline();

    /**
     * Checks whether this User is current offline.
     * 
     * @return {@code true} if this User is currently offline.
     */
    public boolean isOffline();

    /**
     * Sets whether the User is online or not.
     * 
     * @param value whether the User is online or not.
     */
    public void setOnline(boolean online);

    /**
     * Returns the internal id of the user.
     * 
     * @return the internal id of the user.
     */
    public long getId();

    /**
     * Retrieves the UUID of the User. This is the same as the UUID of the related Bukkit Player.
     * 
     * @return the UUID of the User.
     */
    public UUID getUniqueId();

    /**
     * Retrieves the name of the User. This is the same as the name of the related Bukkit Player.
     * 
     * @return the name of the User.
     */
    public String getName();

    /**
     * Retrieves the title of the User.
     * 
     * @return the title of this User.
     */
    public String getTitle();

    /**
     * Retrieves the Bukkit Player instance of this User. If a User is offline while this is called, the method will
     * throw an {@link IllegalStateException}.
     * 
     * @return the Player for this User.
     */
    public Player getPlayer();

    /**
     * Retrieves the Role this User has in their Faction. If this User does not have a Faction, this method will always
     * return {@link Role#FACTIONLESS}.
     * 
     * @return the Faction Role of this User.
     */
    public Role getFactionRole();

    /**
     * Sets the Role this User has in their Faction. If this USer does not have a Faction, the role must be
     * {@link Role#FACTIONLESS} or this method will throw an {@link IllegalArgumentException}.
     * 
     * @param role the new Role of the User.
     */
    public void setFactionRole(Role role);

    /**
     * Retrieves the current Faction that this User is in. If they do not have a Faction, this will return the
     * Wilderness.
     * 
     * @return the current Faction of the User.
     */
    public Faction getFaction();

    /**
     * Retrieves the id of the User's Faction. If they do not have a Faction, this will return the id for the
     * Wilderness.
     * 
     * @return the id of the User's current Faction.
     */
    public long getFactionId();

    /**
     * Sets the faction that this User currently is in.
     * 
     * @param faction the new faction.
     */
    public void setFaction(Faction faction);

    /**
     * Retrieves the last known Location of the User. If the player is connecting for the first time, this will most
     * likely return null.
     * 
     * @return the last known location of the User.
     */
    public LazyLocation getLastLocation();

    /**
     * Checks if this user has the given permission.
     * 
     * @param permission the permission to check for.
     * @return {@code true} if the User has the permission.
     */
    public boolean hasPermission(String permission);

    /**
     * Checks if this User is the User wrapper for the console.
     * 
     * @return {@code true} if this User is the console.
     */
    public boolean isConsole();

    // comment copied from Faction
    /**
     * Saves this User to the designed form of storage. This method will run asynchronously to ensure that it does not
     * cause hesitation in the main thread. This means that saving very often causes no issues except traffic either to
     * the database or the file system itself. Regardless, it should still be used sparingly to ensure as much
     * efficiency as possible.
     */
    public void save();

}
