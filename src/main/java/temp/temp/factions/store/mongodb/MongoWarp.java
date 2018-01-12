package temp.temp.factions.store.mongodb;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import temp.temp.factions.base.Faction;
import temp.temp.factions.spatial.LazyLocation;
import temp.temp.factions.store.LoadWarp;

@JsonIgnoreProperties({ "loaded", "faction", "stub" })
public final class MongoWarp extends LoadWarp
{

    protected UUID factionUuid;
    
    public MongoWarp()
    {
        this.loaded = true;
    }

    public MongoWarp(Faction faction, String name, LazyLocation location, boolean locked, String password)
    {
        this();
        this.factionUuid = faction.getUniqueId();
        this.faction = faction;
        this.name = name;
        this.stub = name.toLowerCase();
        this.location = location;
        this.locked = locked;
        this.password = password;
    }

}
