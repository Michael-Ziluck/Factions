package net.dnddev.factions.events;

import org.bukkit.event.HandlerList;

import net.dnddev.factions.base.Faction;
import net.dnddev.factions.base.User;

/**
 * When a Faction is created for the first time.
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
