package net.dnddev.factions.spatial;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * A lazy-loaded Location object. This allows for the system to keep and store Locations without needing the referenced
 * World to be loaded.
 * 
 * @author Michael Ziluck
 */
public class LazyLocation
{

    protected float x;

    protected float y;

    protected float z;

    protected String world;

    protected World parsedWorld;

    /**
     * Empty constructor for an ORM.
     */
    public LazyLocation()
    {

    }

    /**
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @param z the z coordinate.
     * @param world the world.
     */
    public LazyLocation(float x, float y, float z, World world)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.parsedWorld = world;
        this.world = world.getName();
    }

    /**
     * Returns the X coordinate.
     * 
     * @return the X coordinate.
     */
    public float getX()
    {
        return x;
    }

    /**
     * Returns the Y coordinate.
     * 
     * @return the Y coordinate.
     */
    public float getY()
    {
        return y;
    }

    /**
     * Returns the Z coordinate.
     * 
     * @return the Z coordinate.
     */
    public float getZ()
    {
        return z;
    }

    /**
     * @return the World.
     */
    public World getWorld()
    {
        if (parsedWorld == null)
        {
            parsedWorld = Bukkit.getWorld(world);
        }
        return parsedWorld;
    }

    /**
     * Converts this LazyLocation into a Bukkit location.
     * 
     * @return the new location.
     */
    public Location asBukkitLocation()
    {
        return new Location(getWorld(), x, y, z);
    }

}
