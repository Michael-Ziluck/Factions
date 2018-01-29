package net.dnddev.factions.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import net.dnddev.factions.base.User;

/**
 * Fired when a player tries to look up a Faction by it's name.
 * 
 * @author Michael Ziluck
 */
public class FactionLookupEvent extends Event implements Cancellable
{

    protected static HandlerList handlers;

    protected boolean cancelled;

    protected User user;

    protected String name;

    /**
     * Constructs a new FactionLookupEvent with the given variables.
     * 
     * @param user the user looking up a faction.
     * @param name the name being looked up.
     */
    public FactionLookupEvent(User user, String name)
    {
        this.user = user;
        this.name = name;
    }

    /**
     * Returns the name a player is looking up.
     * 
     * @return the name a player is looking up.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the user that is looking up a faction.
     * 
     * @return the user that is looking up a faction.
     */
    public User getUser()
    {
        return user;
    }

    @Override
    public boolean isCancelled()
    {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel)
    {
        this.cancelled = cancel;
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
