package net.dnddev.factions.events;

import org.bukkit.event.HandlerList;

import net.dnddev.factions.base.Faction;
import net.dnddev.factions.base.User;
import net.dnddev.factions.base.struct.Role;

/**
 * When a Faction is created for the first time.
 * <p>
 * It is important to note that in order to see who is creating the Faction, you must use
 * <p>
 * For a Faction to finish being created, the {@link #complete()} method <i>must</i> be called. If it is not, the id
 * will not be incremented, the User will not be added to the Faction, and the Faction will not be saved to the
 * database.
 * </p>
 * 
 * @author Michael Ziluck
 */
public class FactionCreateEvent extends CancellableFactionEvent
{

    protected static HandlerList handlers;

    protected User user;

    protected double cost;

    /**
     * Constructs a new FactionCreateEvent for the given Faction and it's founder. This event will only fire if the User
     * definitely has the necessary balances.
     * 
     * @param faction the Faction to be created.
     * @param user the User creating the Faction.
     * @param cost the cost of creating the Faction.
     */
    public FactionCreateEvent(Faction faction, User user, double cost)
    {
        super(faction);

        this.user = user;
        this.cost = cost;
    }

    /**
     * Updates the User and the Faction with the proper values.
     */
    public void complete()
    {
        getCreator().setFaction(getFaction());
        getCreator().setFactionRole(Role.LEADER);
        getCreator().save();
        getFaction().save();
    }

    /**
     * Returns the user that is creating the Faction.
     * 
     * @return the user that is creating the Faction.
     */
    public User getCreator()
    {
        return user;
    }

    @Override
    public HandlerList getHandlers()
    {
        return getHandlerList();
    }

    /**
     * Retrieves the HandlerList for this event. If the HandlerList previously was not set, this method will construct a
     * new one.
     * 
     * @return the HandlerList for this event.
     */
    public static HandlerList getHandlerList()
    {
        if (handlers == null)
        {
            handlers = new HandlerList();
        }
        return handlers;
    }

}
