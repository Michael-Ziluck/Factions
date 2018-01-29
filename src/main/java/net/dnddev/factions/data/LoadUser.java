package net.dnddev.factions.data;

import java.util.Collection;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.dnddev.factions.base.Faction;
import net.dnddev.factions.base.User;
import net.dnddev.factions.base.UserStore;
import net.dnddev.factions.base.struct.Role;
import net.dnddev.factions.spatial.LazyLocation;
import net.dnddev.factions.utils.CollectionUtils;

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

    protected UUID uuid;

    protected String name;

    protected boolean online;

    protected String title;

    protected Role factionRole;

    protected Faction faction;

    protected LazyLocation lastLocation;

    @Override
    public UUID getUniqueId()
    {
        return uuid;
    }

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
        return isOnline() ? Bukkit.getPlayer(uuid) : null;
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
    public void sendMessage(String message)
    {
        if (isOnline())
        {
            getPlayer().sendMessage(message);
        }
        else if (isConsole())
        {
            Bukkit.getConsoleSender().sendMessage(message);
        }
        else
        {
            getFaction().addAnnouncement(message, this);
        }
    }

    @Override
    public void sendMessage(String[] messages)
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
        String[] array = CollectionUtils.toArray(messages);
        if (isOnline())
        {
            getPlayer().sendMessage(array);
        }
        else if (isConsole())
        {
            Bukkit.getConsoleSender().sendMessage(messages.toArray(new String[messages.size()]));
        }
        else
        {
            getFaction().addAnnouncements(array, this);
        }
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
