package net.dnddev.factions.base;

import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.dnddev.factions.Factions;
import net.dnddev.factions.base.claims.Claim;
import net.dnddev.factions.spatial.BlockColumn;
import net.dnddev.factions.spatial.BoundedArea;
import net.dnddev.factions.spatial.LazyLocation;

/**
 * The base system to store Factions and the areas that they have claimed.
 * 
 * @author Michael Ziluck
 */
public interface FactionStore
{

    /**
     * @return the singleton instance stored in the Factions main class.
     */
    public static FactionStore getInstance()
    {
        return Factions.getInstance().getFactionStore();
    }

    /**
     * Gets a Faction referenced by its name. If none is found this will return Wilderness, not null. Also, this method
     * is not case-sensitive. For case sensitive name lookup, use {@link #getCasedFaction(String)}.
     * 
     * @param name the name of the Faction.
     * @return the Faction if one exists.
     */
    public Faction getFaction(String name);

    /**
     * Same as {@link #getFaction(String)}, but it is case-sensitive.
     * 
     * @param name the name of the Faction.
     * @return the Faction if one exists.
     */
    public Faction getCasedFaction(String name);

    /**
     * Gets a Faction referenced by a player's UUID. If none is found this will return Wilderness, not null.
     * 
     * @param uuid the uuid of the Player.
     * @return the Faction if one exists.
     */
    public Faction getFaction(UUID uuid);

    /**
     * Gets a Faction that has a claim at a particular Location. If none is found this will return Wilderness, not null.
     * 
     * @param location the location of the faction.
     * @return the Faction if one exists.
     */
    public Faction getFaction(Location location);

    /**
     * Gets a Faction that has a claim at a particular LazyLocation. If none is found this will return Wilderness, not
     * null.
     * 
     * @param location the location of the faction.
     * @return the Faction if one exists.
     */
    public Faction getFaction(LazyLocation location);

    /**
     * Gets a Faction that has a claim at a particular BlockColumn. If none is found this will return Wilderness, not
     * null.
     * 
     * @param column the BlockColumn of the faction.
     * @return the Faction if one exists.
     */
    public Faction getFaction(BlockColumn column);

    /**
     * Gets all Factions that have claims within the given bounded area. If none are found, this returns an <b>EMPTY</b>
     * List; it will not return a List with the Wilderness in it. This method will never return null.
     * 
     * @param area the area to search within.
     * @return all Factions if any exist.
     */
    public List<Faction> getFactions(BoundedArea area);

    /**
     * Gets all Claims within the given bounded area. If none are found, this returns an <b>EMPTY</b> List. It will
     * <i>not</i> return a List with the Wilderness in it. This method will never return null.
     * 
     * @param area the area to search within.
     * @return all Factions if any exist.
     */
    public List<Claim> getClaims(BoundedArea area);

    /**
     * Gets the Faction of the given User. If none is found this will return Wilderness, not null.
     * 
     * @param user the user.
     * @return the Faction if one exists.
     */
    public Faction getFaction(User user);

    /**
     * Gets the Faction of the given Player. If none is found this will return Wilderness, not null.
     * 
     * @param player the player.
     * @return the Faction if one exists.
     */
    public Faction getFaction(Player player);

    /**
     * Load the Factions from the database to run time.
     */
    public void loadFactions();

    /**
     * Saves the given Faction to the database.
     * 
     * @param faction the faction to save.
     */
    public void save(Faction faction);

}
