package net.dnddev.factions.events;

import org.bukkit.event.Cancellable;

import net.dnddev.factions.base.Faction;

/**
 * A FactionEvent that is able to be cancelled.
 * 
 * @author Michael Ziluck
 */
public abstract class CancellableFactionEvent extends FactionEvent implements Cancellable
{

    protected boolean cancelled;

    /**
     * Constructs a new CancellableFactionEvent for the given Faction.
     * 
     * @param faction the Faction to which the event is related.
     */
    public CancellableFactionEvent(Faction faction)
    {
        super(faction);
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

}
