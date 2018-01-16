package net.dnddev.factions.spatial;

import com.conversantmedia.util.collection.spatial.HyperRect;

/**
 * An area that is bounded by two points. The HyperRect is part of a system called an R-Tree which is a type of spatial
 * data structure to quickly and easily search for regions and points in a three-dimensional world.
 * 
 * @author Michael Ziluck
 */
public interface BoundedArea extends HyperRect<BoundedArea>, Comparable<BoundedArea>
{

    /**
     * Retrieves the minimum point for this BoundedArea.<br>
     * <br>
     * Word of caution. It is highly advisable to use the helper methods rather than writing different logic. Some
     * behaviors have to account for more things than expected.
     * 
     * @return the minimum point for this BoundedArea.
     */
    public LazyLocation getMinLocation();

    /**
     * Retrieves the maximum point for this BoundedArea.<br>
     * <br>
     * Word of caution. It is highly advisable to use the helper methods rather than writing different logic. Some
     * behaviors have to account for more things than expected.
     * 
     * @return the maximum point for this BoundedArea.
     */
    public LazyLocation getMaxLocation();

}
