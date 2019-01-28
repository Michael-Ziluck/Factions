package com.ziluck.factions.listeners;

import com.ziluck.factions.base.User;
import com.ziluck.factions.base.UserStore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

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
        User user = UserStore.getInstance().loadUser(event.getPlayer());
        user.setOnline(true);
    }

}
