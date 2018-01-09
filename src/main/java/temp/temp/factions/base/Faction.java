package temp.temp.factions.base;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import temp.temp.factions.chat.Messageable;
import temp.temp.factions.spatial.LazyLocation;

public interface Faction extends Messageable
{

    /**
     * Retrieves the unique id of the Faction. This is generated when the faction is created and will not be reused.
     * Wilderness will always have a UUID of ffffffff-ffff-ffff-ffff-ffffffffffff.
     * 
     * @return the uuid of the faction.
     */
    public UUID getUniqueId();

    /**
     * Retrieves the case-sensitive name of the faction. This should not be used for lookup as requiring
     * case-sensitivity is a pain for users. For lookup, use {@link #getStub()}.
     * 
     * @return the name of the faction.
     */
    public String getName();

    /**
     * Retrieves the lower cased name of the faction. This should be used primarily for lookup.
     * 
     * @return
     */
    public String getStub();

    /**
     * Retrieves the description for the faction. Colors for this can be toggled on and off in the config.yml.
     * 
     * @return the description of the faction.
     */
    public String getDescription();

    /**
     * Retrieves the message of the day for the faction. This is what is sent to players each time they connect to the
     * server. Sending this every time they connect or once per 24-hours can be set in the config. Colors for this can
     * be toggled on and off in the config.yml.
     * 
     * @return the motd of the faction.
     */
    public String getMOTD();

    /**
     * Retrieves all announcements for each player. Announcements are messages stored with the data that are going to be
     * sent to the players the next time they connect to the server. Players that are currently connected to the server
     * should not have any pending announcements as any new announcement added for a player is sent to them immediately.
     * 
     * @return the pending announcements.
     */
    public Map<UUID, List<String>> getAnnouncements();

    /**
     * Add a new announcement for the faction to be sent to the faction. If no users are passed it will send the
     * announcement to every member. This will automatically send the message to all online targeted members of the
     * Faction and store it for all offline targeted members for the next time they join.
     * 
     * @param message the message to be announced.
     * @param users the users to receive the message.
     */
    public void addAnnouncement(String message, User... users);

    /**
     * Remove all announcements for the specified user. This will have no effect on online users as they won't have any
     * pending announcements.
     * 
     * @param user the user to clear.
     */
    public void removeAnnouncements(User user);

    /**
     * Retrieves all the warps that this faction has saved. Each warp has a name that is case insensitive, but should
     * still maintain case when stored in the system.
     * 
     * @return all faction warps.
     */
    public Set<Warp> getWarps();

    /**
     * Gets a warp by the given name. This method is case-insensitive. If there is no warp found by the given name,
     * returns null.
     * 
     * @param name the name to search for.
     * @return the warp.
     */
    public Warp getWarp(String name);

    /**
     * Checks if there is a warp by the given name. This method is case-insensitive and uses {@link Warp#getStub()}.
     * Most cases this method is not necessary to be called as performing a null check with the method
     * {@link #getWarp(String)} should give the exact same behavior.
     * 
     * @param name the name to search for.
     * @return {@code true} if the warp exists.
     */
    public boolean isWarp(String name);

    /**
     * Creates a new warp for the faction with the given name, location, and password. If the password is null it simply
     * is not stored.
     * 
     * @param name the name of the warp.
     * @param lcoation the location of the warp.
     * @param password the password for the warp.
     * @return the newly created warp.
     */
    public Warp setWarp(String name, LazyLocation lcoation, String password);

    /**
     * Creates a new password-less warp for the faction with the given name and location.
     * 
     * @param name the name of the warp.
     * @param location the location of the warp.
     * @return the newly created warp.
     */
    public Warp setWarp(String name, LazyLocation location);

    /**
     * Removes the warp referenced by the given name. This method is case-insensitive and uses {@link Warp#getStub()}.
     * This action is permanent so be sure that all necessary checks are done first.
     * 
     * @param name the name to search for.
     */
    public void removeWarp(String name);

    /**
     * Removes all the warps from the Faction. This action is permanent so be sure that all necessary checks are done
     * first.
     */
    public void clearWarps();

}
