package net.dnddev.factions.data.mongodb;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.jongo.marshall.jackson.oid.MongoId;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties({ "faction" })
public class MongoUser extends LoadUser
{

    @MongoId
    protected long id;

    protected long factionId;

    /**
     * Empty constructor for ORMs
     */
    public MongoUser()
    {

    }

    /**
     * Creates a new MongoUser with the given values.
     * 
     * @param id the internal id of the user.
     * @param uuid the uuid of the user.
     * @param name the nme of the user.
     */
    public MongoUser(long id, UUID uuid, String name)
    {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.factionRole = Role.FACTIONLESS;
        this.faction = FactionStore.getInstance().getWilderness();
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
