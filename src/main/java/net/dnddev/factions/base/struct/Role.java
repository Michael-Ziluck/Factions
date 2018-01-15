package net.dnddev.factions.base.struct;

public enum Role
{

    LEADER(100),
    ADMIN(80),
    MODERATOR(60),
    MEMBER(40),
    TRIAL(20),
    FACTIONLESS(0);

    private int value;

    private Role(int value)
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
     * @return {@code true} if the given Role below this Role.
     */
    public boolean inferior(Role role)
    {
        return role.getValue() < getValue();
    }

}
