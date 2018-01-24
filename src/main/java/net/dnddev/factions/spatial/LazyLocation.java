package net.dnddev.factions.spatial;

import org.bukkit.World;

/**
 * A lazy-loaded Location object. This allows for the system to keep and store Locations without needing the referenced
 * World to be loaded.
 * 
 * @author Michael Ziluck
 */
public interface LazyLocation
{

    /**
     * @return the X coordinate.
     */
    public float getX();

    /**
     * @return the Y coordinate.
     */
    public float getY();

    /**
     * @return the Z coordinate.
     */
    public float getZ();

    /**
     * @return the World.
     */
    public World getWorld();

}
