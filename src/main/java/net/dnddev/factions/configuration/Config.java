package net.dnddev.factions.configuration;

import org.apache.commons.lang.mutable.MutableDouble;
import org.apache.commons.lang.mutable.MutableInt;
import org.bukkit.configuration.file.FileConfiguration;

import net.dnddev.factions.Factions;
import net.dnddev.factions.configuration.struct.Optimization;
import net.dnddev.factions.utils.MutableEnum;

/**
 * The manager for all configuration settings.
 * <p>
 * This plugin is designed to be as configurable as possible in order to make it usable for almost everyone who wants a
 * way to group players together. Almost every single feature can be turned on and off, and this manager allows that to
 * happen.
 * </p>
 * <p>
 * One of the biggest things that this plugin allows servers to decide is whether things should be optimized to reduce
 * memory usage or processing power. Any time a new structure is used, this manager helps make the decision of whether
 * or not to optimize it to use more memory or processing power.
 * </p>
 * <p>
 * Larger servers (more than ~40 people) should always choose to use memory over processing power, and unless you are on
 * a system with EXTREMELY low (less than 1 GB) RAM, the processing optimizer should be used.
 * </p>
 * <p>
 * Most numerical values that are set to -1 will disable the feature it relates to. Not all features can be disabled
 * however.
 * </p>
 * 
 * @author Michael Ziluck
 */
public final class Config
{

    /**
     * What the plugin is currently optimizing for.
     */
    public static MutableEnum<Optimization> OPTIMIZATION = new MutableEnum<Optimization>(Optimization.PROCESS);

    /**
     * The cost to create a Faction. -1 or 0 refers to no cost.
     */
    public static MutableDouble CREATE_COST = new MutableDouble(0);

    /**
     * Update the values from the database.
     */
    public static void update()
    {
        FileConfiguration config = Factions.getInstance().getConfig();
        if (config.isSet("optimization"))
        {
            try
            {
                OPTIMIZATION.setValue(Optimization.valueOf(config.getString("optimization")));
            }
            catch (Exception ex)
            {
                config.set("optimization", OPTIMIZATION.getValue().name());
            }
        }
        else
        {
            config.set("optimization", OPTIMIZATION.getValue().name());
        }

        if (config.isSet("create.cost"))
        {
            try
            {
                CREATE_COST = new MutableDouble(config.getDouble("create.cost"));
            }
            catch (Exception ex)
            {
                config.set("cretae.cost", CREATE_COST.doubleValue());
            }
        }
        else
        {
            config.set("cretae.cost", CREATE_COST.doubleValue());
        }
    }

    private static <T extends Enum<T>> void updateValue(FileConfiguration config, String location, MutableEnum<T> mutable)
    {
        if (!config.isSet(location) || !successful(() -> mutable.setValue(Enum.valueOf(mutable.getType(), config.getString(location)))))
        {
            config.set(location, mutable.getValue());
        }
    }

    private static void updateValue(FileConfiguration config, String location, MutableInt mutable)
    {
        if (!config.isSet(location) || !successful(() -> mutable.setValue(config.getInt(location))))
        {
            config.set(location, mutable.intValue());
        }
    }

    private static void updateValue(FileConfiguration config, String location, MutableDouble mutable)
    {
        if (!config.isSet(location) || !successful(() -> mutable.setValue(config.getDouble(location))))
        {
            config.set(location, mutable.doubleValue());
        }
    }

    private static boolean successful(Runnable runnable)
    {
        try
        {
            runnable.run();
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

}
