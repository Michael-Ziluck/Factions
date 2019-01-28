package com.ziluck.factions.data.mongodb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Preconditions;
import com.ziluck.factions.base.Warp;
import com.ziluck.factions.data.LoadWarp;
import com.ziluck.factions.spatial.LazyLocation;

/**
 * Warp implementation for saving to MongoDB.
 *
 * @author Michael Ziluck
 */
@JsonIgnoreProperties({ "faction", "stub" })
public final class MongoWarp extends LoadWarp
{
    /**
     * An empty constructor. Jongo requires an empty constructor in order to load things.
     * nicely.
     */
    public MongoWarp()
    {
    }

    /**
     * Creates a new {@link Warp} with the given parameters. If locked is set to true, a {@link NullPointerException} will be
     * thrown if the password is null.
     *
     * @param name     the name of the warp.
     * @param location the location of the warp.
     * @param locked   whether this warp is password locked or not.
     * @param password the password for this warp.
     */
    public MongoWarp(String name, LazyLocation location, boolean locked, String password)
    {
        Preconditions.checkNotNull(name, "Name can't be null.");
        Preconditions.checkNotNull(location, "Location can't be null.");
        if (locked)
        {
            Preconditions.checkNotNull(password, "Password can't be null if the warp is locked.");
        }

        this.name = name;
        this.stub = name.toLowerCase();
        this.location = location;
        this.locked = locked;
        this.password = password;
    }

}
