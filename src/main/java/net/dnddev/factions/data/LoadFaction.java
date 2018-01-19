package net.dnddev.factions.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.Validate;

import com.google.common.collect.Multimap;

import net.dnddev.factions.base.Faction;
import net.dnddev.factions.base.User;
import net.dnddev.factions.base.Warp;
import net.dnddev.factions.base.struct.Flag;
import net.dnddev.factions.base.struct.Role;
import net.dnddev.factions.spatial.LazyLocation;

/**
 * The in-memory representation of a Faction.
 * <p>
 * To help reduce clutter and duplicate code, Factions have an additional middle step between the interface and the
 * implementation that saves to the database.
 * </p>
 * <p>
 * This should only ever be used if you are making major changes to Factions. Many checks and a lot of logic is done
 * within this class, so be careful with what exactly is touched. When in doubt, reference the original source code.
 * </p>
 * 
 * @author Michael Ziluck
 */
public abstract class LoadFaction implements Faction
{

    protected UUID uuid;

    protected String name;

    protected String description;

    protected String motd;

    protected Type type;

    protected Multimap<UUID, String> announcements;

    protected Map<String, Warp> warps;

    protected LazyLocation home;

    protected User leader;

    protected List<User> members;

    protected List<UUID> invites;

    protected Set<Flag> flags;
    
    protected boolean loaded;

    /**
     * An empty constructor for ORMs to use.
     */
    protected LoadFaction()
    {
        loaded = true;
    }

    /**
     * Constructs a new LoadFaction with the given values.
     * @param uuid the uuid of the Faction.
     * @param name the name of the Faction.
     * @param leader the one who created the Faction.
     * @param type the type of Faction being created.
     */
    public LoadFaction(UUID uuid, String name, User leader, Type type)
    {
        this();
        this.uuid = uuid;
        this.name = name;
        this.leader = leader;
        this.type = type;
    }

    @Override
    public UUID getUniqueId()
    {
        if (uuid == null)
        {
            // TODO load uuid
        }
        return uuid;
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
        Validate.notNull(user, "User can't be null.");
        Validate.notNull(message, "Message can't be null.");

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
        Validate.notNull(user, "User can't be null.");

        getAnnouncements().removeAll(user);
    }

    @Override
    public void addInvite(User user)
    {
        Validate.notNull(user, "User can't be null.");

        getInvites().add(user.getUniqueId());
    }

    @Override
    public void removeInvite(User user)
    {
        Validate.notNull(user, "User can't be null.");

        getInvites().remove(user.getUniqueId());
    }

    @Override
    public boolean hasInvite(User user)
    {
        Validate.notNull(user, "User can't be null.");

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
        if (warps == null)
        {
            // TODO load warps
        }
        return warps;
    }

    @Override
    public Collection<Warp> getWarps()
    {
        return getWarpsMap().values();
    }

    @Override
    public Warp getWarp(String name)
    {
        Validate.notNull(name, "Name can't be null.");

        return getWarpsMap().get(name);
    }

    @Override
    public boolean isWarp(String name)
    {
        Validate.notNull(name, "Name can't be null.");

        return getWarp(name) != null;
    }

    @Override
    public Warp setWarp(String name, LazyLocation location)
    {
        Validate.notNull(name, "Name can't be null.");
        Validate.notNull(location, "Location can't be null.");

        return setWarp(name, location, null);
    }

    @Override
    public Warp setWarp(String name, LazyLocation location, String password)
    {
        Validate.notNull(name, "Name can't be null.");
        Validate.notNull(location, "Location can't be null.");

        Warp warp = createWarp(name, location, password);
        getWarpsMap().put(warp.getStub(), warp);
        return warp;
    }

    @Override
    public boolean removeWarp(String name)
    {
        Validate.notNull(name, "Name can't be null.");

        return getWarpsMap().remove(name.toLowerCase()) != null;
    }

    @Override
    public void clearWarps()
    {
        getWarpsMap().clear();
    }

    @Override
    public LazyLocation getHome()
    {
        if (home == null)
        {
            // TODO load home
        }
        return home;
    }

    @Override
    public void setHome(LazyLocation home)
    {
        this.home = home;
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
    public User getLeader()
    {
        if (leader == null)
        {
            // TODO load leader
        }
        return leader;
    }

    @Override
    public List<User> getMembers()
    {
        if (members == null)
        {
            // TODO load members
        }
        return members;
    }

    @Override
    public List<User> getAdmins()
    {
        List<User> admins = new ArrayList<>(getMembers());
        admins.removeIf(u -> u.getFactionRole() != Role.ADMIN);
        return admins;
    }

    @Override
    public List<User> getModerators()
    {
        List<User> moderators = new ArrayList<>(getMembers());
        moderators.removeIf(u -> u.getFactionRole() != Role.MODERATOR);
        return moderators;
    }

    @Override
    public List<User> getTrialMembers()
    {
        List<User> trials = new ArrayList<>(getMembers());
        trials.removeIf(u -> u.getFactionRole() != Role.TRIAL);
        return trials;
    }

    @Override
    public List<User> getMembers(Role role)
    {
        Validate.notNull(role, "Role can't be null.");

        List<User> members = new ArrayList<>(getMembers());
        members.removeIf(u -> u.getFactionRole() != role);
        return members;
    }

    @Override
    public void sendMessage(String message)
    {
        Validate.notNull(message, "Message can't be null.");

        addAnnouncement(message);
    }

    @Override
    public void sendMessage(Collection<String> messages)
    {
        Validate.notNull(messages, "Messages can't be null.");

        for (String message : messages)
        {
            addAnnouncement(message);
        }
    }

    @Override
    public void sendMessage(String[] messages)
    {
        Validate.notNull(messages, "Messages can't be null.");

        for (String message : messages)
        {
            addAnnouncement(message);
        }
    }

    @Override
    public boolean isPeaceful()
    {
        if (flags == null)
        {
            // TODO load flags
        }
        return flags.contains(Flag.PEACEFUL);
    }

    @Override
    public boolean isPermanent()
    {
        if (flags == null)
        {
            // TODO load flags
        }
        return !flags.contains(Flag.TEMPORARY);
    }

    @Override
    public int hashCode()
    {
        return getUniqueId().hashCode();
    }

}
