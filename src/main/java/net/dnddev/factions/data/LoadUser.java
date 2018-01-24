package net.dnddev.factions.data;

import java.util.Collection;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.dnddev.factions.base.Faction;
import net.dnddev.factions.base.User;
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

    private long id;

    private UUID uuid;

    private String name;

    private boolean online;

    private String title;

    private Role factionRole;

    private Faction faction;

    private LazyLocation lastLocation;

    @Override
    public long getId()
    {
        return id;
    }

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
    public Faction getFaction()
    {
        return faction;
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
        else
        {
            getFaction().addAnnouncements(array, this);
        }
    }

}
