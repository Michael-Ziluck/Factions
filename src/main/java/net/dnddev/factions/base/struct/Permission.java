package net.dnddev.factions.base.struct;

/**
 * Permissions that Users must have in order to interact with the plugin.
 * 
 * @author Michael Ziluck
 */
public enum Permission
{

    /**
     * The base permission.
     */
    BASE("factions", true),
    /**
     * Create a new Faction.
     */
    CREATE("create"),
    /**
     * Lookup another Faction.
     */
    LOOKUP("faction.other"),
    /**
     * Lookup your own Faction.
     */
    LOOKUP_SELF("faction.self");

    private String permission;

    private boolean excludePrefix;

    private Permission(String permission)
    {
        this.permission = permission;
    }

    private Permission(String permission, boolean excludePrefix)
    {
        this.permission = permission;
        this.excludePrefix = excludePrefix;
    }

    /**
     * @return the Permission's String representation
     */
    public String getPermission()
    {
        return (excludePrefix ? "" : BASE.getPermission()) + permission;
    }

}