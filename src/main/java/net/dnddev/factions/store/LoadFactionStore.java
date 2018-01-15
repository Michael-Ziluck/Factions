package net.dnddev.factions.store;

import java.util.Map;
import java.util.UUID;

import org.bukkit.World;

import com.conversantmedia.util.collection.spatial.RTree;

import net.dnddev.factions.base.Claim;
import net.dnddev.factions.base.FactionStore;

public abstract class LoadFactionStore implements FactionStore
{

    /**
     * The Map of the claims in each {@link World}. The {@link World}'s UUID is used to speed of retrieval efficiency.
     */
    protected Map<UUID, RTree<Claim>> claims;

}
