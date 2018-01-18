package net.dnddev.factions.data.mongodb;

import java.util.UUID;

import org.jongo.marshall.jackson.oid.MongoId;

import net.dnddev.factions.base.User;
import net.dnddev.factions.base.Warp;
import net.dnddev.factions.base.Faction.Type;
import net.dnddev.factions.data.LoadFaction;
import net.dnddev.factions.spatial.LazyLocation;

/**
 * Faction implementation for saving to MongoDB.
 * 
 * @author Michael Ziluck
 */
public class MongoFaction extends LoadFaction
{

    @MongoId
    protected UUID uuid;

    public MongoFaction()
    {
        // TODO Auto-generated constructor stub
    }
    
    public MongoFaction(UUID uuid, String name, User leader, Type type)
    {
        
    }
    
    @Override
    public void save()
    {

    }

    @Override
    protected Warp createWarp(String name, LazyLocation location, String password)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
