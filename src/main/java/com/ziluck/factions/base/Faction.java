package com.ziluck.factions.base;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.google.common.collect.Multimap;
import com.ziluck.factions.base.claims.Claim;
import com.ziluck.factions.base.struct.FactionType;
import com.ziluck.factions.base.struct.Flag;
import com.ziluck.factions.base.struct.Role;
import com.ziluck.factions.spatial.LazyLocation;

/**
 * The base entity that this API is based around.
 * <p>
 * Factions are groups of {@link User Users} that work together to play within a Minecraft server. Factions can claim
 * land, have a home, set warps, create outposts, and much more.
 * </p>
 *
 * @author Michael Ziluck
 */
public interface Faction extends Messageable, Nameable
{

    /**
     * Retrieves the id of the Faction. This will auto-increment in the database. The Wilderness will have an id of -1.
     *
     * @return the id of the Faction.
     */
    public long getId();

    /**
     * Retrieves the description for the Faction. Colors for this can be toggled on and off in the config.yml.
     *
     * @return the description of the Faction.
     */
    public String getDescription();

    /**
     * Retrieves the message of the day for the Faction. This is what is sent to players each time they connect to the
     * server. Sending this every time they connect or once per 24-hours can be set in the config. Colors for this can
     * be toggled on and off in the config.yml.
     *
     * @return the MOTD of the Faction.
     */
    public String getMOTD();

    /**
     * Gets the default Role of a new member.
     *
     * @return the default Role.
     */
    public Role getDefaultRole();

    /**
     * Sets the Role that new members are set to when they join a Faction.
     *
     * @param role the new default role.
     */
    public void setDefaultRole(Role role);

    /**
     * Returns the Unix timestamp from when this Faction was made.
     *
     * @return the Unix timestamp from when this Faction was made.
     */
    public long getFounded();

    /**
     * Sets when the Faction was created.
     *
     * @param created the Unix timestamp from when this Faction was made.
     */
    public void setFounded(long created);

    /**
     * Retrieves all announcements for each player. Announcements are messages stored with the data that are going to be
     * sent to the players the next time they connect to the server. Players that are currently connected to the server
     * should not have any pending announcements as any new announcement added for a player is sent to them immediately.
     *
     * @return the pending announcements.
     */
    public Multimap<UUID, String> getAnnouncements();

    /**
     * Adds a new announcement for the Faction to be sent to the specified Faction members.<br>
     * <p>
     * This will automatically send the message to all online targeted members of the Faction and store it for all
     * offline targeted members for the next time they join.
     * </p>
     *
     * @param message the message to be announced.
     * @param users   the users to receive the message.
     */
    public void addAnnouncement(String message, Collection<User> users);

    /**
     * Adds a new announcement for the Faction to be sent to the specified Faction member.<br>
     * <p>
     * This will automatically send the message to the User if they are online, or store it if they are offline for the
     * next time they join.
     * </p>
     *
     * @param message the message to be announced.
     * @param user    the users to receive the message.
     */
    public void addAnnouncement(String message, User user);

    /**
     * Adds a new announcement for the Faction to be sent to all members.<br>
     * <br>
     * This will automatically send the message to all online members of the Faction and store it for all offline
     * members for the next time they join.
     *
     * @param message the message to be announced.
     */
    public void addAnnouncement(String message);

    /**
     * Adds a new announcement for the Faction to be sent to all members with the specified rank. Important note: this
     * will not send it to the rank's superiors as well. To also send to superiors, use
     * {@link #addAnnouncement(String, Role, boolean)}. <br>
     * <br>
     * This will automatically send the message to all online targeted members of the Faction and store it for all
     * offline targeted members for the next time they join.
     *
     * @param message the message to be announced.
     * @param role    the role to receive the message.
     */
    public void addAnnouncement(String message, Role role);

    /**
     * Adds a new announcement for the Faction to be sent to all members with the specified rank with the ability to
     * include superiors. This will automatically send the message to all online targeted members of the Faction and
     * store it for all offline targeted members for the next time they join.
     *
     * @param message   the message to be announced.
     * @param role      the to receive message.
     * @param superiors whether or not to also send to the role's superiors.
     */
    public void addAnnouncement(String message, Role role, boolean superiors);

    /**
     * Adds new announcements for the Faction to be sent to the specified Faction members.<br>
     * <p>
     * This will automatically send the messages to all online targeted members of the Faction and store it for all
     * offline targeted members for the next time they join.
     * </p>
     *
     * @param messages the message to be announced.
     * @param users    the users to receive the message.
     */
    public void addAnnouncements(String[] messages, Collection<User> users);

    /**
     * Adds new announcements for the Faction to be sent to the specified Faction member.<br>
     * <p>
     * This will automatically send the messages to the User if they are online, or store it if they are offline for the
     * next time they join.
     * </p>
     *
     * @param messages the messages to be announced.
     * @param user     the users to receive the message.
     */
    public void addAnnouncements(String[] messages, User user);

    /**
     * Adds new announcements for the Faction to be sent to all members.<br>
     * <br>
     * This will automatically send the messages to all online members of the Faction and store it for all offline
     * members for the next time they join.
     *
     * @param messages the messages to be announced.
     */
    public void addAnnouncements(String[] messages);

    /**
     * Adds new announcements for the Faction to be sent to all members with the specified rank. Important note: this
     * will not send it to the rank's superiors as well. To also send to superiors, use
     * {@link #addAnnouncement(String, Role, boolean)}. <br>
     * <br>
     * This will automatically send the message to all online targeted members of the Faction and store it for all
     * offline targeted members for the next time they join.
     *
     * @param messages the messages to be announced.
     * @param role     the role to receive the message.
     */
    public void addAnnouncements(String[] messages, Role role);

    /**
     * Adds new announcement for the Faction to be sent to all members with the specified rank with the ability to
     * include superiors. This will automatically send the messages to all online targeted members of the Faction and
     * store it for all offline targeted members for the next time they join.
     *
     * @param messages  the messages to be announced.
     * @param role      the to receive message.
     * @param superiors whether or not to also send to the role's superiors.
     */
    public void addAnnouncements(String[] messages, Role role, boolean superiors);

    /**
     * Remove all announcements for the specified user. This will have no effect on online users as they won't have any
     * pending announcements.
     *
     * @param user the user to clear.
     */
    public void removeAnnouncements(User user);

    /**
     * Retrieves all the warps that this Faction has saved. Each warp has a name that is case insensitive, but should
     * still maintain case when stored in the system.
     *
     * @return all Faction warps.
     */
    public Collection<Warp> getWarps();

    /**
     * Gets a warp by the given name. This method is case-insensitive. If there is no warp found by the given name,
     * returns null.
     *
     * @param name the name to search for.
     *
     * @return the warp.
     */
    public Warp getWarp(String name);

    /**
     * Checks if there is a warp by the given name. This method is case-insensitive and uses {@link Warp#getStub()}.
     * Most cases this method is not necessary to be called as performing a null check with the method
     * {@link #getWarp(String)} should give the exact same behavior.
     *
     * @param name the name to search for.
     *
     * @return {@code true} if the warp exists.
     */
    public boolean isWarp(String name);

    /**
     * Creates a new warp for the Faction with the given name, location, and password. If the password is null it simply
     * is not stored.
     *
     * @param name     the name of the warp.
     * @param location the location of the warp.
     * @param password the password for the warp.
     *
     * @return the newly created warp.
     */
    public Warp setWarp(String name, LazyLocation location, String password);

    /**
     * Creates a new password-less warp for the Faction with the given name and location.
     *
     * @param name     the name of the warp.
     * @param location the location of the warp.
     *
     * @return the newly created warp.
     */
    public Warp setWarp(String name, LazyLocation location);

    /**
     * Removes the warp referenced by the given name. This method is case-insensitive and uses {@link Warp#getStub()}.
     * This action is permanent so be sure that all necessary checks are done first.
     *
     * @param name the name to search for.
     *
     * @return {@code true} if there was a Faction to remove.
     */
    public boolean removeWarp(String name);

    /**
     * Removes all the warps from the Faction. This action is permanent so be sure that all necessary checks are done
     * first.
     */
    public void clearWarps();

    /**
     * Checks if this Faction has a Home set.
     *
     * @return {@code true} if the home is set.
     */
    public boolean hasHome();

    /**
     * Get the LazyLocation for the Faction's home.
     *
     * @return the home.
     */
    public LazyLocation getHome();

    /**
     * Sets a new LazyLocation for the Faction's home. The home is not allowed to be null. If you want to clear the
     * Faction home, use {@link #clearHome()}.
     *
     * @param home the new home.
     */
    public void setHome(LazyLocation home);

    /**
     * Removes the home the Faction has set.
     */
    public void clearHome();

    /**
     * Retrieves the leader of the Faction. This is the only member-related method that does not search through the
     * members to find who we are looking for.
     *
     * @return the leader of the Faction.
     */
    public User getLeader();

    /**
     * Retrieves all the admins of the Faction. This is a shorthand method for retrieving all members and filtering out
     * all non-admins. If there are no admins, this will return an empty list. Important note: this will return only the
     * admins, not any member who is an admin or higher.
     *
     * @return all admins of the Faction.
     */
    public Set<User> getAdmins();

    /**
     * Retrieves all the moderators of the Faction. This is a shorthand method for retrieving all members and filtering
     * out all non-moderators. If there are no moderators, this will return an empty list. Important note: this will
     * return only the moderators, not any member who is a moderator or higher.
     *
     * @return all moderators of the Faction.
     */
    public Set<User> getModerators();

    /**
     * Add a new member to the Faction.
     *
     * @param user the user to add.
     */
    public void addMember(User user);

    /**
     * Retrieves all the members of the Faction. This includes the leader.<br>
     * <br>
     * This method is lazy loading by default, meaning that it will only load the members when it is called for the
     * first time rather than when it is started. This ensures that unnecessary memory is not used up to ensure it is as
     * efficient as possible. This feature can be turned off in the config, but it is advisable to be left on.
     *
     * @return all members of the Faction.
     */
    public Set<User> getMembers();

    /**
     * Retrieves all the members who have the specific rank. This is a shorthand method for retrieving all members and
     * filter out all members who aren't the specified role. If there are no members with that role, this will return an
     * empty list. Important note: this will return only the members with the specified role, not those at or above the
     * role.
     *
     * @param role the role to look for.
     *
     * @return the members with the given faction role.
     */
    public Set<User> getMembers(Role role);

    /**
     * Retrieves all the trials members of the Faction. This is a shorthand method for retrieving all members and
     * filtering out all non-trial-members. If there are no trial members, this will return an empty list. Important
     * note: this will return only trial-members, not any member who is trial or higher.
     *
     * @return the trial members.
     */
    public Set<User> getTrialMembers();

    /**
     * Retrieves the {@link FactionType} of the Faction.
     *
     * @return the Faction Type.
     */
    public FactionType getType();

    /**
     * Returns all the areas claimed by the Faction.
     *
     * @return all the areas claimed by the Faction.
     */
    public Collection<Claim> getClaims();

    /**
     * Checks if this is the Wilderness. The Wilderness will always have an id of -1.
     *
     * @return {@code true} if this is the Wilderness.
     */
    public boolean isWilderness();

    /**
     * Checks if this is a Normal Faction. This means that it is a Faction created by a User. Normal Factions be
     * permanent or non-permanent, as well as peaceful and non-peaceful.
     *
     * @return {@code true} if this is a Normal Faction.
     */
    public boolean isNormal();

    /**
     * Checks if this is a Warzone. This means it is a system Faction created by an administrator. Warzones will not
     * have a leader or members.
     *
     * @return {@code true} if this is a Warzone Faction.
     */
    public boolean isWarzone();

    /**
     * Checks if this is a Safezone. This means it is a system Faction created by an administrator. Safezones will not
     * have a leader or members.
     *
     * @return {@code true} if this is a Safezone Faction.
     */
    public boolean isSafezone();

    /**
     * Checks if this Faction is permanent. Non-permanent Factions have an expiration date and will be disbanded once
     * that date is reached.
     *
     * @return {@code true} if this Faction is permanent.
     */
    public boolean isPermanent();

    /**
     * Checks if this Faction is peaceful. Peaceful Factions are not able to partake in PVP. Additionally peaceful
     * Factions are able to use several more flags that they are not able to normally use.
     *
     * @return {@code true} if this Faction is peaceful.
     */
    public boolean isPeaceful();

    /**
     * Checks if this Faction is open. Open Factions do not require an invitation in order to join them.
     *
     * @return {@code true} if this Faction is open.
     */
    public boolean isOpen();

    /**
     * Add an invite for the given User. For non-open factions, adding an invite for a User is the only way to allow
     * players to join a Faction.
     *
     * @param user the user to add an invite for.
     */
    public void addInvite(User user);

    /**
     * Returns all the flags that the Faction has.
     *
     * @return all the flags that the Faction has.
     */
    public Set<Flag> getFlags();

    /**
     * Remove an existing invite for the given User.
     *
     * @param user the user whose invite to remove.
     */
    public void removeInvite(User user);

    /**
     * Checks if the given User has an existing invite.
     *
     * @param user the user to check.
     *
     * @return {@code true} if the User has an invite.
     */
    public boolean hasInvite(User user);

    /**
     * Retrieves all existing invites for this Faction. This list should not be modified directly as it can cause
     * unexpected side effects. Use {@link #addInvite(User)} and {@link #removeInvite(User)} instead.
     *
     * @return the invites for this faction.
     */
    public List<UUID> getInvites();

    /**
     * Returns the current balance this {@link Faction} has.
     *
     * @return the current balance this {@link Faction} has.
     */
    public double getBalance();


    /**
     * Deposits the given amount into this {@link Faction}'s balance.
     *
     * @param user   the {@link User} that made the transaction.
     * @param amount the amount to deposit.
     */
    public void deposit(User user, double amount);

    /**
     * Withdraws the given amount from this {@link Faction}'s balance.
     *
     * @param user   the {@link User} that made the transaction.
     * @param amount the amount to withdraw.
     */
    public void withdraw(User user, double amount);

    /**
     * Returns a history of all transactions made by this {@link Faction}.
     *
     * @return a history of all transactions made by this {@link Faction}.
     */
    public List<Transaction> getTransactionHistory();

    /**
     * Saves this Faction instance to the designated form of storage. This method will run asynchronously to ensure that
     * it does not cause hesitation in the main thread. This means that saving very often causes no issues except
     * traffic either to the database or the file system itself. Regardless, it should still be used sparingly to ensure
     * as much efficiency as possible.
     */
    public void save();

    /**
     * Loads all the Users that are a member of this Faction. This is not called when the Faction starts to reduce the
     * number of Users that need to be kept loaded at a given time.
     */
    public void loadMembers();

}
