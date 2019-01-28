package com.ziluck.factions.data;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.ziluck.factions.base.Faction;
import com.ziluck.factions.base.User;
import com.ziluck.factions.base.UserStore;
import com.ziluck.factions.base.struct.Role;
import com.ziluck.factions.spatial.LazyLocation;
import com.ziluck.factions.utils.CollectionUtils;

/**
 * The in-memory representation of a User.
 * <p>
 * To help reduce clutter and duplicate code, Users have an additional middle step between the interface and the
 * implementation that loads information to the database.
 * </p>
 *
 * @author Michael Ziluck
 */
public abstract class LoadUser implements User
{
    protected String name;

    protected boolean online;

    protected String title;

    protected Role factionRole;

    protected Faction faction;

    protected LazyLocation lastLocation;

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public String getTitle()
    {
        return title;
    }

    @Override
    public Player getPlayer()
    {
        return isOnline() ? Bukkit.getPlayer(getUniqueId()) : null;
    }

    @Override
    public void setOnline(boolean online)
    {
        this.online = online;
    }

    @Override
    public boolean isOnline()
    {
        return online;
    }

    @Override
    public boolean isOffline()
    {
        return !online;
    }

    @Override
    public Role getFactionRole()
    {
        return factionRole;
    }

    @Override
    public void setFactionRole(Role role)
    {
        this.factionRole = role;
    }

    @Override
    public boolean hasFaction()
    {
        return !getFaction().isWilderness();
    }

    @Override
    public long getFactionId()
    {
        return getFaction().getId();
    }

    @Override
    public LazyLocation getLastLocation()
    {
        return lastLocation;
    }

    @Override
    public void sendMessage(String... messages)
    {
        if (isOnline())
        {
            getPlayer().sendMessage(messages);
        }
        else if (isConsole())
        {
            Bukkit.getConsoleSender().sendMessage(messages);
        }
        else
        {
            getFaction().addAnnouncements(messages, this);
        }
    }

    @Override
    public void sendMessage(Collection<String> messages)
    {
        sendMessage(CollectionUtils.toArray(messages));
    }

    @Override
    public boolean hasPermission(String permission)
    {
        return isConsole() || (isOnline() && getPlayer().hasPermission(permission));
    }

    @Override
    public boolean isConsole()
    {
        return UserStore.getInstance().getConsole().getId() == getId();
    }

}
