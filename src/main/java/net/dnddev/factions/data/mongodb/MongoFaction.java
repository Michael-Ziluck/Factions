package net.dnddev.factions.data.mongodb;

import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.jongo.marshall.jackson.oid.MongoId;

import net.dnddev.factions.base.User;
import net.dnddev.factions.base.Warp;
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

    /**
     * Empty constructor for the ORM to use.
     */
    public MongoFaction()
    {
        super();
    }

    /**
     * Create a new Faction with the given information. This should only be used when a new Faction is made, not to load
     * an already existing Faction.
     * 
     * @param id the uuid of the Faction.
     * @param name the name of the Faction.
     * @param leader the person making the new Faction.
     * @param type the type of the Faction.
     */
    public MongoFaction(long id, String name, User leader, Type type)
    {
        super(id, name, leader, type);
    }

    @Override
    public void save()
    {
        // TODO finish implementation
    }

    @Override
    protected Warp createWarp(String name, LazyLocation location, String password)
    {
        MongoWarp warp = new MongoWarp(this, name, location, password != null, DigestUtils.md5Hex(password));

        getWarpsMap().put(warp.getStub(), warp);

        save();

        return warp;
    }

}
