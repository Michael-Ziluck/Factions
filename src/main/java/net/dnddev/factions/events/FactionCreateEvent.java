package net.dnddev.factions.events;

import org.bukkit.event.HandlerList;

/**
 * When a Faction is created for the first time.
 * 
 * @author Michael Ziluck
 */
public class FactionCreateEvent extends CancellableFactionEvent
{

    protected static HandlerList handlers;

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
