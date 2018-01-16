package net.dnddev.factions;

import org.bukkit.plugin.java.JavaPlugin;

import net.dnddev.factions.base.FactionStore;

/**
 * The base plugin for Factions.
 * <p>
 * This class has a ton of convenience methods for retrieving things from the default FactionStore. Although it is not
 * required, it is simpler to just use the static convenience methods within here.
 * </p>
 * 
 * @author Michael Ziluck
 */
public class Factions extends JavaPlugin
{

    private static Factions instance;

    private FactionStore factionStore;

    public void onEnable()
    {
        instance = this;
    }

    /**
     * @return the currently used FactionStore.
     */
    public FactionStore getFactionStore()
    {
        return factionStore;
    }

    /**
     * Retrieves the current singleton instance of Factions. It is important to note that this is set in the
     * {@link #onEnable()}, so all other plugins must have a dependency or soft-dependency for Factions for this to be
     * reliable.
     * 
     * @return the current singleton instance of Factions.
     */
    public static Factions getInstance()
    {
        return instance;
    }

}
