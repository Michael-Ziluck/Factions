package net.dnddev.factions.base;

import net.dnddev.factions.spatial.LazyLocation;

public interface Warp
{

    /**
     * Retrieves the {@link Faction} this warp is attached to.
     * 
     * @return the faction.
     */
    public Faction getFaction();

    /**
     * Retrieves the name of the warp. This is case-sensitive so it should not be used for lookup values. If look up
     * functionality is needed, use {@link #getStub()}.
     * 
     * @return the warp name.
     */
    public String getName();

    /**
     * Retrieves the lower-case name of the warp. This is the optimal way to look up a warp.
     * 
     * @return the warp stub.
     */
    public String getStub();

    /**
     * Retrieves the location of the warp. It is provided in lazy form as there are times when a world is not yet loaded
     * or should not be loaded for whatever reason.
     * 
     * @return the location of the warp.
     */
    public LazyLocation getLocation();

    /**
     * Retrieves the MD5 encrypted version of the password for this warp. A newer and less crackable encryption method
     * is not used to ensure that too much space is not used as data security is not incredibly important with basic
     * warps.
     * 
     * @return the encrypted password.
     */
    public String getPassword();

    /**
     * @return {@code true} if the Warp has a password set.
     */
    public boolean hasPassword();

    /**
     * Encrypts the passed String and checks if it matches the version stored in the data.
     * 
     * @param check the String to check.
     * @return {@code true} if the passed variable is the correct password.
     */
    public boolean isPassword(String check);

    /**
     * Sets a new password for the Warp.
     * 
     * @param password the new password for the Warp.
     */
    public void setPassword(String password);

}
