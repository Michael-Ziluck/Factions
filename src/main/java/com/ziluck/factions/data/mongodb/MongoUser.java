package com.ziluck.factions.data.mongodb;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ziluck.factions.base.Faction;
import com.ziluck.factions.base.FactionStore;
import com.ziluck.factions.base.UserStore;
import com.ziluck.factions.base.struct.Role;
import com.ziluck.factions.data.LoadUser;
import org.dizitart.no2.objects.Id;
import org.jongo.marshall.jackson.oid.MongoId;

/**
 * An implementation of a LoadUser that is stored in MongoDB.
 *
 * @author Michael Ziluck
 */
@JsonIgnoreProperties({ "faction", "uuid", "online", "console", "offline" })
public class MongoUser extends LoadUser
{
    @MongoId
    @Id
    protected long id;

    @JsonProperty("faction_id")
    protected long factionId;

    @JsonProperty("faction_role")
    protected Role factionRole;

    protected UUID uuid;

    @JsonProperty(value = "uid")
    protected String uuidString;

    /**
     * Empty constructor for ORMs
     */
    public MongoUser()
    {
        this.factionId = -1;
    }

    /**
     * Creates a new MongoUser with the given values.
     *
     * @param id   the internal id of the user.
     * @param uuid the uuid of the user.
     * @param name the name of the user.
     */
    public MongoUser(long id, UUID uuid, String name)
    {
        this.id = id;
        this.uuid = uuid;
        this.uuidString = uuid.toString();
        this.name = name;
        this.factionId = -1;
        this.factionRole = Role.FACTIONLESS;
    }

    @Override
    public long getId()
    {
        return id;
    }

    @Override
    public UUID getUniqueId()
    {
        if (uuid == null)
        {
            uuid = UUID.fromString(uuidString);
        }
        return uuid;
    }

    @Override
    public Faction getFaction()
    {
        if (faction == null && factionId >= 0)
        {
            faction = FactionStore.getInstance().getFaction(factionId);
        }
        else if (factionId == -1)
        {
            faction = FactionStore.getInstance().getWilderness();
        }
        return faction;
    }

    @Override
    public void setFaction(Faction faction)
    {
        this.faction = faction;
        this.factionId = faction.getId();
    }

    @Override
    public void save()
    {
        UserStore.getInstance().save(this);
    }

}
