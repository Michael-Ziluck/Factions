package net.dnddev.factions.base;

import java.util.UUID;

import net.dnddev.factions.base.struct.Role;
import net.dnddev.factions.chat.Messageable;

public interface User extends Messageable
{

    /**
     * Checks whether this User is currently online. This is most often done by doing a custom
     * 
     * @return {@code true} if this User is currently online.
     */
    public boolean isOnline();

    /**
     * Retrieves the UUID of the User. This is the same as the UUID of the related Minecraft Player.
     * 
     * @return the UUID of the User.
     */
    public UUID getUniqueId();

    /**
     * Retrieves the Role this User has in their Faction. If this User does not have a Faction, this method will always
     * return {@link Role#FACTIONLESS}.
     * 
     * @return the Faction Role of this User.
     */
    public Role getFactionRole();

}
