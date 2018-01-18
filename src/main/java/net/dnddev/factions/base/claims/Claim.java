package net.dnddev.factions.base.claims;

import java.util.Collection;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.block.Block;

import net.dnddev.factions.base.Faction;
import net.dnddev.factions.base.Purchasable;
import net.dnddev.factions.base.User;

/**
 * Used to represent an area claimed by a {@link Faction}.
 * <p>
 * Claims are able to be both two-dimensional and three-dimensional. To better represent this, there are two additional
 * interfaces that are used. {@link Claim2D} and {@link Claim3D} are individually used depending on the settings in the
 * configuration.
 * </p>
 * <p>
 * Additionally, Claims are able to be owned by one or more {@link User Users}. Additional users can be added to Claims
 * by Faction officers.
 * </p>
 * 
 * @author Michael Ziluck
 */
public interface Claim extends Purchasable
{

    /**
     * Claims only exist if they are owned by a Faction. If an area is the Wilderness, it does not have a set Faction
     * and therefore it can't be a claim.
     * 
     * @return the faction this claim belongs to.
     */
    public Faction getFaction();

    /**
     * Utility method to get a {@link Collection} of all {@link User Users} that are currently physically inside this
     * claim.
     * 
     * @return all {@link User Users} within the claim.
     */
    public Collection<User> getWithin();

    /**
     * All users who have ownership of the Claim. If the Server does not have the option to allow owners of a Claim then
     * this method will always return null.
     * 
     * @return the {@link Claim} owners.
     */
    public Collection<User> getOwners();

    /**
     * Checks whether or not the given {@link User} is within this Claim. If the {@link User} is offline this method
     * will always return false.
     * 
     * @param user the User to check.
     * @return {@code true} if the User is within the Claim.
     */
    public boolean isWithin(User user);

    /**
     * Checks whether or not the given {@link Location} is within this Claim.
     * 
     * @param location the Location to check.
     * @return {@code true} if the Location is within the Claim.
     */
    public boolean isWithin(Location location);

    /**
     * Get the Blocks that make up the walls of this Claim. If the Claim is three dimensional, it will also return the
     * floors and roof.
     * 
     * @return the Blocks of the walls.
     */
    public Set<Block> getWalls();

    /**
     * Get the geometric center of the Claim. Important to note that this will most likely not be a whole number as it
     * gets the exact number of the center.
     * 
     * @return the center of the Claim.
     */
    public Location getCenter();

}
