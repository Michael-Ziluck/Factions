package net.dnddev.factions.base.struct;

/**
 * Represents where a User is talking to.
 *
 * @author Michael Ziluck
 */
public enum ChatMode
{

    /**
     * When anyone can hear a User.
     */
    GLOBAL,
    /**
     * When only a User's Faction can hear them.
     */
    FACTION,
    /**
     * When a User's Faction's allies can hear them.
     */
    ALLY,
    /**
     * When nearby Users can hear a User.
     */
    LOCAL

}
