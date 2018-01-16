package net.dnddev.factions.store.mongodb;

import net.dnddev.factions.base.Warp;
import net.dnddev.factions.spatial.LazyLocation;
import net.dnddev.factions.store.LoadFaction;

/**
 * Faction implementation for saving to MongoDB.
 * 
 * @author Michael Ziluck
 */
public class MongoFaction extends LoadFaction
{

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
