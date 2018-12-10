package net.dnddev.factions.tests;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import net.dnddev.factions.Factions;
import net.dnddev.factions.base.UserStore;
import net.dnddev.factions.data.nitrite.NitriteUserStore;
import net.dnddev.factions.listeners.ConnectionListener;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitScheduler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(value = { PlayerJoinEvent.class, Factions.class, UserStore.class, Bukkit.class })
public class UserTest
{
    private static NitriteUserStore userStore;

    private static Factions factions;

    private static File outputFolder;

    private Player player;

    @Before
    public void setup()
    {
        outputFolder = new File("output/");
        if (!outputFolder.exists() && !outputFolder.mkdir())
        {
            Assert.fail("Could not create folder.");
        }

        // create factions object
        factions = PowerMockito.mock(Factions.class);

        // set up Bukkit
        BukkitScheduler scheduler = Mockito.mock(BukkitScheduler.class);

        // hijack the runTaskAsynchronously method to instead run it synchronously.
        when(scheduler.runTaskAsynchronously(ArgumentMatchers.same(factions), any(Runnable.class)))
                .thenAnswer(invocation ->
                            {
                                invocation.<Runnable>getArgument(1).run();
                                return null;
                            });
        PowerMockito.mockStatic(Bukkit.class);
        when(Bukkit.getOnlinePlayers()).thenReturn(Collections.emptyList());
        when(Bukkit.getScheduler()).thenReturn(scheduler);

        // set up factions data folder
        PowerMockito.when(factions.getDataFolder()).thenReturn(outputFolder);

        // set up factions getInstance
        PowerMockito.mockStatic(Factions.class);
        when(Factions.getInstance()).thenReturn(factions);

        // set up UserStore
        PowerMockito.mockStatic(UserStore.class);
        userStore = new NitriteUserStore();
        when(UserStore.getInstance()).thenReturn(userStore);

        // set up player
        player = Mockito.mock(Player.class);

        when(player.getName()).thenReturn("Doctor_Zee");
        when(player.getUniqueId()).thenReturn(UUID.fromString("a84a885c-7551-49c4-90c0-31d25f41e7f0"));
    }

    @Test
    public void testSetup()
    {
        assertEquals(0, Bukkit.getOnlinePlayers().size());
        assertEquals(factions, Factions.getInstance());
        assertEquals(userStore, UserStore.getInstance());
        assertEquals(outputFolder, Factions.getInstance().getDataFolder());

        assertNotNull(Factions.getInstance());
        assertNotNull(UserStore.getInstance());
        assertNotNull(Factions.getInstance().getDataFolder());
    }

    @Test
    public void testJoin()
    {
        PlayerJoinEvent event = PowerMockito.mock(PlayerJoinEvent.class);

        when(event.getPlayer()).thenReturn(player);

        ConnectionListener listener = new ConnectionListener();
        listener.onJoin(event);

        assertEquals(player, event.getPlayer());
        assertNotNull(UserStore.getInstance().getUser(player.getUniqueId()));
        assertTrue(UserStore.getInstance().getUser(player.getUniqueId()).isOnline());
    }

}
