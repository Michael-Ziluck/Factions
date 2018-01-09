package temp.temp.factions.base;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import temp.temp.factions.util.NamedLocation;

public interface Faction
{

    /**
     * Retrieves all announcements for each player. Announcements are messages stored with the data that are going to be
     * sent to the players the next time they connect to the server. Players that are currently connected to the server
     * should not have any pending announcements as any new announcement added for a player is sent to them immediately.
     * 
     * @return the pending announcements.
     */
    public Map<UUID, List<String>> getAnnouncements();

    /**
     * Retrieves all the warps that this faction has saved. Each warp has a name that is case insensitive, but should
     * still maintain case when stored in the system.
     * 
     * @return all faction warps.
     */
    public Set<NamedLocation> getWarps();

    /**
     * Gets a warp by the specific name. This method is case-insensitive. If there is no warp found by the given name,
     * null is returned.
     * 
     * @param name the name to search for.
     * @return the warp.
     */
    public NamedLocation getWarp(String name);

}
