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
    BASE("factions"),
    /**
     * Create a new Faction.
     */
    CREATE("create");

    private String permission;

    private Permission(String permission)
    {
        this.permission = permission;
    }

    /**
     * @return the Permission's String representation
     */
    public String getPermission()
    {
        return permission;
    }

}