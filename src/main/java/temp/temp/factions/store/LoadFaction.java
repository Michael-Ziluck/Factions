package temp.temp.factions.store;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.Validate;

import com.google.common.collect.Multimap;

import temp.temp.factions.base.Faction;
import temp.temp.factions.base.User;
import temp.temp.factions.base.Warp;
import temp.temp.factions.base.struct.Role;

public abstract class LoadFaction implements Faction
{

    protected UUID uniqueId;

    protected String name;

    protected String description;

    protected String motd;

    protected Multimap<UUID, String> announcements;

    protected List<Warp> warps;

    protected User leader;

    protected List<User> members;

    @Override
    public UUID getUniqueId()
    {
        return uniqueId;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public String getStub()
    {
        return name.toLowerCase();
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
    public Multimap<UUID, String> getAnnouncements()
    {
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
        announcements.removeAll(user);
    }

    @Override
    public int hashCode()
    {
        return getUniqueId().hashCode();
    }

}
