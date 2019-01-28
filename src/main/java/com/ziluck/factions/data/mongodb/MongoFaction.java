package com.ziluck.factions.data.mongodb;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ziluck.factions.Factions;
import com.ziluck.factions.base.FactionStore;
import com.ziluck.factions.base.Transaction;
import com.ziluck.factions.base.User;
import com.ziluck.factions.base.UserStore;
import com.ziluck.factions.base.Warp;
import com.ziluck.factions.base.struct.FactionType;
import com.ziluck.factions.data.LoadFaction;
import com.ziluck.factions.spatial.LazyLocation;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.jongo.marshall.jackson.oid.MongoId;

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
     * @param id     the uuid of the Faction.
     * @param name   the name of the Faction.
     * @param leader the person making the new Faction.
     * @param type   the type of the Faction.
     */
    public MongoFaction(long id, String name, User leader, FactionType type)
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
        MongoWarp warp = new MongoWarp(name, location, password != null, DigestUtils.md5Hex(password));

        getWarpsMap().put(warp.getStub(), warp);

        save();

        return warp;
    }

    @Override
    public void withdraw(User user, double amount)
    {
        balance -= amount;
        getTransactionHistory().add(new MongoTransaction(user, new Date(), -amount));
        save();
    }

    @Override
    public void deposit(User user, double amount)
    {
        balance += amount;
        getTransactionHistory().add(new MongoTransaction(user, new Date(), amount));
        save();
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
