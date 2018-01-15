package net.dnddev.factions.spatial;

import org.bukkit.World;

import com.conversantmedia.util.collection.spatial.HyperRect;

public interface LazyLocation extends HyperRect<LazyLocation>, Comparable<LazyLocation>
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
