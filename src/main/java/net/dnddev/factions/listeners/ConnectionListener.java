package net.dnddev.factions.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.dnddev.factions.base.UserStore;

/**
 * Processes when Players join or leave the server.
 * 
 * @author Michael Ziluck
 */
public class ConnectionListener implements Listener
{

    /**
     * Listens for when a player joins the server.
     * 
     * @param event the Bukkit event that is fired.
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event)
    {
        UserStore.getInstance().loadUser(event.getPlayer());
    }

}
