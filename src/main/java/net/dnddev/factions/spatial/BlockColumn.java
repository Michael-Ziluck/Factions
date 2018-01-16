package net.dnddev.factions.spatial;

import java.util.Set;

import org.bukkit.World;
import org.bukkit.block.Block;

import com.conversantmedia.util.collection.spatial.HyperRect;

/**
 * A two-dimensional location within a World. Similar to a LazyLocation, it allows storage of a location without
 * requiring the World to also be loaded. The major difference is that it only uses the x and z values of the location
 * in the World.
 * 
 * @author Michael Ziluck
 */
public interface BlockColumn extends HyperRect<BlockColumn>, Comparable<BlockColumn>
{

    /**
     * @return the x-coordinate.
     */
    public double getX();

    /**
     * @return the z-coordinate.
     */
    public double getZ();

    /**
     * @return the World this BlockColumn exists in.
     */
    public World getWorld();

    /**
     * @return all of the {@link Block Blocks} that make up this BlockColumn.
     */
    public Set<Block> getBlocks();

}
