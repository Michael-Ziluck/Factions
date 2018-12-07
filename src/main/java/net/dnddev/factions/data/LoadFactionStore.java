package net.dnddev.factions.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.bukkit.Location;
import org.bukkit.World;

import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.RTree;

import net.dnddev.factions.base.Faction;
import net.dnddev.factions.base.FactionStore;
import net.dnddev.factions.base.claims.Claim;
import net.dnddev.factions.base.claims.Claim2D;
import net.dnddev.factions.spatial.BlockColumn;
import net.dnddev.factions.spatial.BoundedArea;
import net.dnddev.factions.spatial.LazyLocation;

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
    protected Map<String, RTree<Faction, Claim2D>> claims;

    /**
     * {@code true} once the FactionStore has been loaded from the database.
     */
    protected boolean loaded;

    /**
     * The Wilderness.
     */
    protected Faction wilderness;

    protected long nextId;

    /**
     * Construct a new LoadFactionStore.
     */
    public LoadFactionStore()
    {

    }

    @Override
    public Faction getFaction(Location location)
    {
        RTree<Faction, Claim2D> tree = claims.get(location.getWorld().getName());
        if (tree == null)
        {
            claims.put(location.getWorld().getName(), RTree.create());
            return wilderness;
        }

        BlockColumn blockColumn = new BlockColumn(location.getBlockX(), location.getBlockZ(), location.getWorld());

        try
        {
            return tree.search(blockColumn).toBlocking().toFuture().get().value();
        }
        catch (InterruptedException | ExecutionException ex)
        {
            return wilderness;
        }
    }

    @Override
    public Faction getFaction(LazyLocation location)
    {
        RTree<Faction, Claim2D> tree = claims.get(location.getWorld().getName());
        if (tree == null)
        {
            claims.put(location.getWorld().getName(), RTree.create());
            return wilderness;
        }

        BlockColumn blockColumn = new BlockColumn((int) location.getX(), (int) location.getZ(), location.getWorld());

        try
        {
            return tree.search(blockColumn).toBlocking().toFuture().get().value();
        }
        catch (InterruptedException | ExecutionException ex)
        {
            return wilderness;
        }
    }

    @Override
    public Faction getFaction(BlockColumn column)
    {
        RTree<Faction, Claim2D> tree = claims.get(column.getWorld().getName());
        if (tree == null)
        {
            claims.put(column.getWorld().getName(), RTree.create());
            return wilderness;
        }

        try
        {
            return tree.search(column).toBlocking().toFuture().get().value();
        }
        catch (InterruptedException | ExecutionException ex)
        {
            return wilderness;
        }
    }

    @Override
    public List<Faction> getFactions(BoundedArea area)
    {
        RTree<Faction, Claim2D> tree = claims.get(area.getWorld().getName());
        if (tree == null)
        {
            claims.put(area.getWorld().getName(), RTree.create());
            return Collections.singletonList(wilderness);
        }

        ArrayList<Faction> values = new ArrayList<>();
        for (Entry<Faction, Claim2D> claim : tree.search(area).toBlocking().toIterable())
        {
            values.add(claim.value());
        }
        return values;
    }

    @Override
    public List<Claim> getClaims(BoundedArea area)
    {
        RTree<Faction, Claim2D> tree = claims.get(area.getWorld().getName());
        if (tree == null)
        {
            claims.put(area.getWorld().getName(), RTree.create());
            return Collections.emptyList();
        }

        ArrayList<Claim> values = new ArrayList<>();
        for (Entry<Faction, Claim2D> claim : tree.search(area).toBlocking().toIterable())
        {
            values.add(claim.geometry());
        }
        return values;
    }

    @Override
    public void incrementNextId()
    {
        nextId++;
    }

    @Override
    public Faction getWilderness()
    {
        return wilderness;
    }

}
