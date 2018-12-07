package net.dnddev.factions.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import net.dnddev.factions.base.struct.FactionType;
import org.apache.commons.lang.Validate;

import com.google.common.collect.Multimap;

import net.dnddev.factions.base.Faction;
import net.dnddev.factions.base.User;
import net.dnddev.factions.base.Warp;
import net.dnddev.factions.base.claims.Claim;
import net.dnddev.factions.base.struct.Flag;
import net.dnddev.factions.base.struct.Role;
import net.dnddev.factions.configuration.Config;
import net.dnddev.factions.configuration.struct.Optimization;
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
 * <p>
 * Saving is done in the following places:
 * <ul>
 * <li>{@link #createWarp(String, LazyLocation, String)}</li>
 * </ul>
 * </p>
 *
 * @author Michael Ziluck
 */
public abstract class LoadFaction implements Faction
{

    protected String name;

    protected String stub;

    protected String description;

    protected String motd;

    protected FactionType type;

    protected Role defaultRole;

    protected long founded;

    protected Multimap<UUID, String> announcements;

    protected Map<String, Warp> warps;

    protected LazyLocation home;

    protected User leader;

    protected Set<User> members;

    protected List<UUID> invites;

    protected Set<Flag> flags;

    protected List<Claim> claims;

    protected boolean loaded;

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public String getStub()
    {
        if (Config.OPTIMIZATION.getValue() == Optimization.MEMORY)
        {
            return getName().toLowerCase();
        }
        else if (stub == null)
        {
            stub = getName().toLowerCase();
        }

        return stub;
    }

    @Override
    public String getDescription()
    {
        return description;
    }

    @Override
    public String getMOTD()
    {
        return motd;
    }

    @Override
    public Role getDefaultRole()
    {
        return defaultRole;
    }

    @Override
    public void setDefaultRole(Role role)
    {
        this.defaultRole = role;
    }

    @Override
    public long getFounded()
    {
        return founded;
    }

    @Override
    public void setFounded(long founded)
    {
        this.founded = founded;
    }

    @Override
    public Multimap<UUID, String> getAnnouncements()
    {
        return announcements;
    }

    /**
     * Performs the check to see if the User is online. If they are this method will send the announcement immediately.
     * Otherwise it will add it to the pending announcements.
     *
     * @param user    the user to check.
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

    /**
     * Performs the check to see if the User is online. If they are this method will send the announcement immediately.
     * Otherwise it will add it to the pending announcements.
     *
     * @param user     the user to check.
     * @param messages the message to process.
     */
    protected void processAnnouncement(User user, String[] messages)
    {
        Validate.notNull(user, "User can't be null.");
        Validate.notNull(messages, "Message can't be null.");

        if (user.isOnline())
        {
            user.sendMessage(messages);
        }
        else
        {
            getAnnouncements().putAll(user.getUniqueId(), Arrays.asList(messages));
        }
    }

    /**
     * Performs the check to see if the User is online. If they are this method will send the announcement immediately.
     * Otherwise it will add it to the pending announcements.
     *
     * @param user     the user to check.
     * @param messages the message to process.
     */
    protected void processAnnouncement(User user, Collection<String> messages)
    {
        Validate.notNull(user, "User can't be null.");
        Validate.notNull(messages, "Message can't be null.");

        if (user.isOnline())
        {
            user.sendMessage(messages);
        }
        else
        {
            getAnnouncements().putAll(user.getUniqueId(), messages);
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
    public void addAnnouncement(String message, User user)
    {
        Validate.notNull(message, "Message can't be null.");
        Validate.notNull(user, "User can't be null.");

        processAnnouncement(user, message);
    }

    @Override
    public void addAnnouncement(String message, Collection<User> users)
    {
        Validate.notNull(message, "Message can't be null.");
        Validate.notNull(users, "Users can't be null.");

        for (User user : users)
        {
            processAnnouncement(user, message);
        }
    }

    @Override
    public void addAnnouncements(String[] messages)
    {
        Validate.notNull(messages, "Messages can't be null.");

        for (User user : getMembers())
        {
            processAnnouncement(user, messages);
        }
    }

    @Override
    public void addAnnouncements(String[] messages, Role role)
    {
        Validate.notNull(messages, "Messages can't be null.");
        Validate.notNull(role, "Role can't be null.");

        List<User> users = new LinkedList<>(getMembers());
        users.removeIf(u -> u.getFactionRole() != role);
        addAnnouncements(messages, users);
    }

    @Override
    public void addAnnouncements(String[] messages, Role role, boolean superiors)
    {
        if (!superiors)
        {
            addAnnouncements(messages, role);
        }
        else
        {
            Validate.notNull(messages, "Messages can't be null.");
            Validate.notNull(role, "Role can't be null.");

            List<User> users = new LinkedList<>(getMembers());
            users.removeIf(u -> u.getFactionRole().inferior(role));
            addAnnouncements(messages, users);
        }
    }

    @Override
    public void addAnnouncements(String[] messages, User user)
    {
        Validate.notNull(messages, "Messages can't be null.");
        Validate.notNull(user, "User can't be null.");

        processAnnouncement(user, messages);
    }

    @Override
    public void addAnnouncements(String[] messages, Collection<User> users)
    {
        Validate.notNull(messages, "Messages can't be null.");
        Validate.notNull(users, "Users can't be null.");

        for (User user : users)
        {
            processAnnouncement(user, messages);
        }
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
            invites = new ArrayList<>();
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
            warps = new HashMap<>();
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
    public boolean hasHome()
    {
        return getHome() != null;
    }

    @Override
    public LazyLocation getHome()
    {
        return home;
    }

    @Override
    public void setHome(LazyLocation home)
    {
        Validate.notNull(home);

        this.home = home;
    }

    @Override
    public void clearHome()
    {
        this.home = null;
    }

    /**
     * Creates a new Warp object using the proper database implementation.
     *
     * @param name     the name of the warp.
     * @param location the location of the warp.
     * @param password the password for the warp.
     *
     * @return the newly created warp.
     */
    protected abstract Warp createWarp(String name, LazyLocation location, String password);

    @Override
    public FactionType getType()
    {
        return type;
    }

    @Override
    public boolean isNormal()
    {
        return getType() == FactionType.NORMAL;
    }

    @Override
    public boolean isSafezone()
    {
        return getType() == FactionType.SAFEZONE;
    }

    @Override
    public boolean isWarzone()
    {
        return getType() == FactionType.WARZONE;
    }

    @Override
    public boolean isWilderness()
    {
        return getType() == FactionType.WILDERNESS;
    }

    @Override
    public boolean isOpen()
    {
        return getFlags().contains(Flag.OPEN);
    }

    @Override
    public User getLeader()
    {
        if (leader == null)
        {
            loadMembers();
        }
        return leader;
    }

    @Override
    public void addMember(User user)
    {
        getMembers().add(user);
        user.setFactionRole(getDefaultRole());
        processNewMember(user);
    }

    /**
     * Handle adding a new member to the database. Users are a special case within the system as the entire chunk of
     * information for a user is not also stored in a Faction. As such, this method allows each individual database set
     * up to handle it manually.
     *
     * @param user the new user to process
     */
    protected abstract void processNewMember(User user);

    @Override
    public Set<User> getMembers()
    {
        if (members == null)
        {
            loadMembers();
        }
        return members;
    }

    @Override
    public Set<User> getAdmins()
    {
        Set<User> admins = new HashSet<>(getMembers());
        admins.removeIf(u -> u.getFactionRole() != Role.ADMIN);
        return admins;
    }

    @Override
    public Set<User> getModerators()
    {
        Set<User> moderators = new HashSet<>(getMembers());
        moderators.removeIf(u -> u.getFactionRole() != Role.MODERATOR);
        return moderators;
    }

    @Override
    public Set<User> getTrialMembers()
    {
        Set<User> trials = new HashSet<>(getMembers());
        trials.removeIf(u -> u.getFactionRole() != Role.TRIAL);
        return trials;
    }

    @Override
    public Set<User> getMembers(Role role)
    {
        Validate.notNull(role, "Role can't be null.");

        Set<User> members = new HashSet<>(getMembers());
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
    public Collection<Claim> getClaims()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isPeaceful()
    {
        return getFlags().contains(Flag.PEACEFUL);
    }

    @Override
    public boolean isPermanent()
    {
        assertFlags();

        return !getFlags().contains(Flag.TEMPORARY);
    }

    @Override
    public Set<Flag> getFlags()
    {
        assertFlags();

        return flags;
    }

    protected void assertFlags()
    {
        if (flags == null)
        {
            flags = EnumSet.noneOf(Flag.class);
        }
    }

    /**
     * Load the leader of the Faction from it's stored id.
     */
    public abstract void loadLeader();

    @Override
    public int hashCode()
    {
        return Long.hashCode(getId());
    }

}
