package net.dnddev.factions.spatial;

import com.conversantmedia.util.collection.spatial.HyperRect;

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
