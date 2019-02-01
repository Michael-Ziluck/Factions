package com.ziluck.factions.tests;

import java.util.Collections;

import com.ziluck.factions.Factions;
import com.ziluck.factions.base.struct.FactionType;
import com.ziluck.factions.data.mongodb.MongoFaction;
import com.ziluck.factions.data.mongodb.MongoUser;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Factions.class, Bukkit.class })
public class TransactionTest
{
    @Mock
    private Factions factions;

    private MongoFaction faction;

    private MongoUser[] users = new MongoUser[3];

    @Before
    public void setup()
    {
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

        users[0] = mock(MongoUser.class);
        users[1] = mock(MongoUser.class);
        users[2] = mock(MongoUser.class);

        faction = new MongoFaction(1, "Test1", users[0], FactionType.NORMAL);
    }

    @Test
    public void testTransactionList()
    {
        faction.deposit(users[0], 100);

        assertEquals(100, faction.getBalance(), 0);
        assertEquals(1, faction.getTransactionHistory().size());
    }

}
