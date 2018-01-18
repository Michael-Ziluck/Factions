package net.dnddev.factions.data.mongodb;

import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.dnddev.factions.base.Faction;
import net.dnddev.factions.base.User;
import net.dnddev.factions.data.MemoryFactionStore;
import net.dnddev.factions.spatial.BlockColumn;
import net.dnddev.factions.spatial.BoundedArea;
import net.dnddev.factions.spatial.LazyLocation;

/**
 * Faction implementation for processing Factions from MongoDB.
 * 
 * @author Michael Ziluck
 */
public class MongoFactionStore extends MemoryFactionStore
{

    @Override
    public Faction getFaction(String name)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Faction getFaction(UUID uuid)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Faction getFaction(Location location)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Faction getFaction(LazyLocation location)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Faction getFaction(BlockColumn column)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Faction> getFactions(BoundedArea area)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Faction getFaction(User user)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Faction getFaction(Player player)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
