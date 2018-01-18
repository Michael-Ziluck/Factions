package net.dnddev.factions.data.mongodb;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Preconditions;

import net.dnddev.factions.base.Faction;
import net.dnddev.factions.data.LoadWarp;
import net.dnddev.factions.spatial.LazyLocation;

/**
 * Warp implementation for saving to MongoDB.
 * 
 * @author Michael Ziluck
 */
@JsonIgnoreProperties({ "loaded", "faction", "stub" })
public final class MongoWarp extends LoadWarp
{

    protected UUID factionUuid;

    /**
     * An empty constructor that sets loaded to true. Jongo requires an empty constructor in order to load things
     * nicely.
     */
    public MongoWarp()
    {
        this.loaded = true;
    }

    /**
     * Creates a new Warp with the given paramters. If locked is set to true, a {@link NullPointerException} will be
     * thrown if the password is null.
     * 
     * @param faction the faction that created the warp.
     * @param name the name of the warp.
     * @param location the location of the warp.
     * @param locked whether this warp is password locked or not.
     * @param password the password for this warp.
     */
    public MongoWarp(Faction faction, String name, LazyLocation location, boolean locked, String password)
    {
        this();
        Preconditions.checkNotNull(faction, "Faction can't be null.");
        Preconditions.checkNotNull(faction, "Name can't be null.");
        Preconditions.checkNotNull(faction, "Location can't be null.");
        if (locked)
        {
            Preconditions.checkNotNull(password, "Password can't be null if the warp is locked.");
        }
        this.factionUuid = faction.getUniqueId();
        this.faction = faction;
        this.name = name;
        this.stub = name.toLowerCase();
        this.location = location;
        this.locked = locked;
        this.password = password;
    }

}
