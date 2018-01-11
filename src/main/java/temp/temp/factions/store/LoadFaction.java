package temp.temp.factions.store;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.Validate;

import com.google.common.collect.Multimap;

import temp.temp.factions.base.Faction;
import temp.temp.factions.base.User;
import temp.temp.factions.base.Warp;
import temp.temp.factions.base.struct.Flag;
import temp.temp.factions.base.struct.Role;
import temp.temp.factions.spatial.LazyLocation;

public abstract class LoadFaction implements Faction
{

    protected UUID uniqueId;

    protected String name;

    protected String description;

    protected String motd;

    protected Type type;

    protected Multimap<UUID, String> announcements;

    protected Map<String, Warp> warps;

    protected User leader;

    protected List<User> members;

    protected List<UUID> invites;

    protected Set<Flag> flags;

    @Override
    public UUID getUniqueId()
    {
        if (uniqueId == null)
        {
            // TODO load uuid
        }
        return uniqueId;
    }

    @Override
    public String getName()
    {
        if (name == null)
        {
            // TODO load name
        }
        return name;
    }

    @Override
    public String getStub()
    {
        if (name == null)
        {
            // TODO load name
        }
        return name.toLowerCase();
    }

    @Override
    public String getDescription()
    {
        if (description == null)
        {
            // TODO load description
        }
        return description;
    }

    @Override
    public String getMOTD()
    {
        if (motd != null)
        {
            // TODO load motd
        }
        return motd;
    }

    @Override
    public Multimap<UUID, String> getAnnouncements()
    {
        if (announcements == null)
        {
            // TODO load announcements
        }
        return announcements;
    }

    /**
     * Performs the check to see if the User is online. If they are this method will send the announcement immediately.
     * Otherwise it will add it to the pending announcements.
     * 
     * @param user the user to check.
     * @param message the message to process.
     */
    protected void processAnnouncement(User user, String message)
    {
        if (user.isOnline())
        {
            user.sendMessage(message);
        }
        else
        {
            getAnnouncements().put(user.getUniqueId(), message);
        }
    }

    @Override
    public void addAnnouncement(String message, List<User> users)
    {
        Validate.notNull(message, "Message can't be null.");
        Validate.notNull(users, "Users can't be null.");

        for (User user : users)
        {
            processAnnouncement(user, message);
        }
    }

    @Override
    public void addAnnouncement(String message)
    {
        Validate.notNull(message, "Message can't be null.");

        for (User user : getMembers())
        {
            processAnnouncement(user, message);
        }
    }

    @Override
    public void addAnnouncement(String message, Role role)
    {
        Validate.notNull(message, "Message can't be null.");
        Validate.notNull(role, "Role can't be null.");

        List<User> users = new LinkedList<>(getMembers());
        users.removeIf(u -> u.getFactionRole() != role);
        addAnnouncement(message, users);
    }

    @Override
    public void addAnnouncement(String message, Role role, boolean superiors)
    {
        Validate.notNull(message, "Message can't be null.");
        Validate.notNull(role, "Role can't be null.");

        List<User> users = new LinkedList<>(getMembers());
        users.removeIf(u -> u.getFactionRole().inferior(role));
        addAnnouncement(message, users);
    }

    @Override
    public void removeAnnouncements(User user)
    {
        getAnnouncements().removeAll(user);
    }

    @Override
    public void addInvite(User user)
    {
        getInvites().add(user.getUniqueId());
    }

    @Override
    public void removeInvite(User user)
    {
        getInvites().remove(user.getUniqueId());
    }

    @Override
    public boolean hasInvite(User user)
    {
        return getInvites().contains(user.getUniqueId());
    }

    @Override
    public List<UUID> getInvites()
    {
        if (invites == null)
        {
            // TODO load invites
        }
        return invites;
    }

    /**
     * Retrieves the map of the Warps that exist. This is used almost exclusively for lookup and easy access to values.
     * 
     * @return the map of Warps and referenced by their stubs.
     */
    protected Map<String, Warp> getWarpsMap()
    {
        return warps;
    }

    @Override
    public Collection<Warp> getWarps()
    {
        if (warps == null)
        {
            // TODO load warps
        }
        return getWarpsMap().values();
    }

    @Override
    public Warp getWarp(String name)
    {
        return getWarpsMap().get(name);
    }

    @Override
    public boolean isWarp(String name)
    {
        return getWarp(name) != null;
    }

    @Override
    public Warp setWarp(String name, LazyLocation location)
    {
        return setWarp(name, location, null);
    }

    @Override
    public Warp setWarp(String name, LazyLocation location, String password)
    {
        Warp warp = createWarp(name, location, password);
    }

    /**
     * Creates a new Warp object using the proper database implementation.
     * 
     * @param name the name of the warp.
     * @param location the location of the warp.
     * @param password the password for the warp.
     * @return the newly created warp.
     */
    protected abstract Warp createWarp(String name, LazyLocation location, String password);

    @Override
    public Type getType()
    {
        if (type == null)
        {
            // TODO load type
        }
        return type;
    }

    @Override
    public boolean isNormal()
    {
        return getType() == Faction.Type.NORMAL;
    }

    @Override
    public boolean isSafezone()
    {
        return getType() == Faction.Type.SAFEZONE;
    }

    @Override
    public boolean isWarzone()
    {
        return getType() == Faction.Type.WARZONE;
    }

    @Override
    public boolean isWilderness()
    {
        return getType() == Faction.Type.WILDERNESS;
    }

    @Override
    public int hashCode()
    {
        return getUniqueId().hashCode();
    }

}
