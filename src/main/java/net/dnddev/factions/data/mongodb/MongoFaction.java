package net.dnddev.factions.data.mongodb;

import java.util.HashSet;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.jongo.marshall.jackson.oid.MongoId;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import net.dnddev.factions.Factions;
import net.dnddev.factions.base.FactionStore;
import net.dnddev.factions.base.User;
import net.dnddev.factions.base.UserStore;
import net.dnddev.factions.base.Warp;
import net.dnddev.factions.data.LoadFaction;
import net.dnddev.factions.spatial.LazyLocation;

/**
 * Faction implementation for saving to MongoDB.
 * 
 * @author Michael Ziluck
 */
@JsonIgnoreProperties({ "stub", "announcements", "loaded", "members", "leader" })
public class MongoFaction extends LoadFaction
{

    @MongoId
    protected long id;

    protected long leaderId;

    protected long[] memberIds;

    /**
     * Empty constructor for the ORM to use.
     */
    public MongoFaction()
    {
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
        this.id = id;
        this.name = name;
        this.leader = leader;
        this.leaderId = leader.getId();
        this.type = type;
    }

    @Override
    public void save()
    {
        Bukkit.getScheduler().runTaskAsynchronously(Factions.getInstance(), () -> FactionStore.getInstance().save(this));
    }

    @Override
    protected Warp createWarp(String name, LazyLocation location, String password)
    {
        MongoWarp warp = new MongoWarp(this, name, location, password != null, DigestUtils.md5Hex(password));

        getWarpsMap().put(warp.getStub(), warp);

        save();

        return warp;
    }

    @Override
    public void loadMembers()
    {
        members = new HashSet<>(memberIds.length);
        for (long id : memberIds)
        {
            members.add(UserStore.getInstance().getUser(id));
        }
    }

    @Override
    protected void processNewMember(User user)
    {
        memberIds = ArrayUtils.add(memberIds, user.getId());
    }

    @Override
    public long getId()
    {
        return id;
    }

    @Override
    public void loadLeader()
    {
        this.leader = UserStore.getInstance().getUser(leaderId);
    }

}
