package net.dnddev.factions.data.mongodb;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.jongo.marshall.jackson.oid.MongoId;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import net.dnddev.factions.Factions;
import net.dnddev.factions.base.Faction;
import net.dnddev.factions.base.FactionStore;
import net.dnddev.factions.base.UserStore;
import net.dnddev.factions.base.struct.Role;
import net.dnddev.factions.data.LoadUser;

/**
 * An implementation of a LoadUser that is stored in MongoDB.
 * 
 * @author Michael Ziluck
 */
@JsonIgnoreProperties({ "faction", "uuid", "online", "console", "offline" })
public class MongoUser extends LoadUser
{

    @MongoId
    protected long id;

    protected long factionId;

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
     * @param id the internal id of the user.
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
        if (faction == null && factionId > 0)
        {
            faction = FactionStore.getInstance().getFaction(factionId);
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
        Bukkit.getScheduler().runTaskAsynchronously(Factions.getInstance(), () -> UserStore.getInstance().save(this));
    }

}
