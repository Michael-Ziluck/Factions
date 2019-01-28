package com.ziluck.factions.base.struct;

/**
 * Represents the role that a User takes in a Faction.
 *
 * @author Michael Ziluck
 */
public enum Role
{

    /**
     * The leader of a Faction.
     */
    LEADER(100),
    /**
     * One rank under Leader, admins can do almost everything the Leader can do.
     */
    ADMIN(80),
    /**
     * Those in charge of moderating the regular members.
     */
    MODERATOR(60),
    /**
     * A regular member of a Faction.
     */
    MEMBER(40),
    /**
     * A member whose membership is either temporary or preliminary.
     */
    TRIAL(20),
    /**
     * The Role that those without a Faction have.
     */
    FACTIONLESS(0);

    private int value;

    Role(int value)
    {
        this.value = value;
    }

    /**
     * The value or power level of each Role. This allows it to be much easier to keep track of the hierarchy in the
     * rank tiers.
     *
     * @return the value of the rank.
     */
    public int getValue()
    {
        return value;
    }

    /**
     * Checks if the given Role is a superior of this Role. If the Roles are the same it will still return
     * {@code false}.
     *
     * @param role the Role to check.
     *
     * @return {@code true} if the given Role outranks this Role.
     */
    public boolean superior(Role role)
    {
        return role.getValue() > getValue();
    }

    /**
     * Checks if the given Role is an inferior of this Role. If the Roles are the same it will still return
     * {@code false}.
     *
     * @param role the Role to check.
     *
     * @return {@code true} if the given Role below this Role.
     */
    public boolean inferior(Role role)
    {
        return role.getValue() < getValue();
    }

}
