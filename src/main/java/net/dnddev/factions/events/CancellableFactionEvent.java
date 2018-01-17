package net.dnddev.factions.events;

import org.bukkit.event.Cancellable;

/**
 * A FactionEvent that is able to be cancelled.
 * 
 * @author Michael Ziluck
 */
public abstract class CancellableFactionEvent extends FactionEvent implements Cancellable
{

    protected boolean cancelled;

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

}
