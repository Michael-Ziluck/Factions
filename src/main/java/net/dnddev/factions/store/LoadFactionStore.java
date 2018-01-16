package net.dnddev.factions.store;

import java.util.Map;
import java.util.UUID;

import org.bukkit.World;

import com.conversantmedia.util.collection.spatial.RTree;

import net.dnddev.factions.base.Claim;
import net.dnddev.factions.base.FactionStore;

/**
 * The in-memory representation of a FactionStore.
 * <p>
 * To help reduce clutter and duplicate code, FactionStores have an additional middle step between the interface and the
 * implementation that loads information to the database.
 * </p>
 * 
 * @author Michael Ziluck
 */
public abstract class LoadFactionStore implements FactionStore
{

    /**
     * The Map of the claims in each {@link World}. The {@link World}'s UUID is used to speed of retrieval efficiency.
     */
    protected Map<UUID, RTree<Claim>> claims;

}
