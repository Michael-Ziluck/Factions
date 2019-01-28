package com.ziluck.factions.events;

import com.ziluck.factions.base.User;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * The event that is fired when a player attempts to create a new Faction, and the server checks if the name is used.
 * 
 * @author Michael Ziluck
 */
public class FactionUnusedNameCheckEvent extends Event implements Cancellable
{
    protected static HandlerList handlers;

    protected boolean cancelled;

    protected String name;

    protected User sender;

    /**
     * Constructs a new FactionUnusedNameCheckEvent with the given CommandSender and the name they attempted to use.
     * 
     * @param sender the sender of the command.
     * @param name the name they attempted to use.
     */
    public FactionUnusedNameCheckEvent(User sender, String name)
    {
        this.sender = sender;
        this.name = name;
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
