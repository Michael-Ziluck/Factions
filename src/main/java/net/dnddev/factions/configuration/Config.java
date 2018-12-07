package net.dnddev.factions.configuration;

import org.apache.commons.lang.mutable.Mutable;
import org.apache.commons.lang.mutable.MutableBoolean;
import org.apache.commons.lang.mutable.MutableDouble;
import org.apache.commons.lang.mutable.MutableInt;
import org.bukkit.configuration.file.FileConfiguration;

import net.dnddev.factions.Factions;
import net.dnddev.factions.base.struct.Role;
import net.dnddev.factions.configuration.struct.Optimization;
import net.dnddev.factions.configuration.struct.Storage;
import net.dnddev.factions.utils.MutableEnum;
import net.dnddev.factions.utils.MutableString;

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
    public static final MutableEnum<Optimization> OPTIMIZATION = new MutableEnum<>(Optimization.PROCESS);

    /**
     * The type of Storage the system uses.
     */
    public static final MutableEnum<Storage> STORAGE_TYPE = new MutableEnum<>(Storage.MONGODB);

    /**
     * The hostname of the database if one is used.
     */
    public static final MutableString DATABASE_HOSTNAME = new MutableString("localhost");

    /**
     * The port of the database if one is used.
     */
    public static final MutableInt DATABASE_PORT = new MutableInt(27017);

    /**
     * The username of the user for the database if one is used.
     */
    public static final MutableString DATABASE_USERNAME = new MutableString("root");

    /**
     * The password of the database user if one is used.
     */
    public static final MutableString DATABASE_PASSWORD = new MutableString("password");

    /**
     * The database to use in the database if one is used.
     */
    public static final MutableString DATABASE_DATABASE = new MutableString("factions");

    /**
     * How long the system should wait when trying to connect to a database.
     */
    public static final MutableInt DATABASE_TIMEOUT = new MutableInt(100);

    /**
     * The description given to a connection if the DBMS supports that feature.
     */
    public static final MutableString DATABASE_CONNECTION_DESCRIPTION = new MutableString("Factions Connection");

    /**
     * The maximum amount of Factions allowed on the server.
     */
    public static final MutableInt FACTION_LIMIT = new MutableInt(-1);

    /**
     * The cost to create a Faction. -1 or 0 refers to no cost.
     */
    public static final MutableDouble CREATE_COST = new MutableDouble(0);

    /**
     * Whether or not the create event can be cancelled.
     */
    public static final MutableBoolean CREATE_CANCELLABLE = new MutableBoolean(true);

    /**
     * When a create event is cancelled, this determines if they are given a message from Factions.
     */
    public static final MutableBoolean CREATE_SILENT_CANCEL = new MutableBoolean(false);

    /**
     * Whether or not to broadcast when a faction is created.
     */
    public static final MutableBoolean CREATE_BROADCAST = new MutableBoolean(true);

    /**
     * The default role that Factions have when they are first created.
     */
    public static final MutableEnum<Role> FACTION_DEFAULT_ROLE = new MutableEnum<>(Role.MEMBER);

    /**
     * Whether or not Factions are able to change the default role.
     */
    public static final MutableBoolean FACTION_DEFAULT_ROLE_CHANGEABLE = new MutableBoolean(true);

    /**
     * The name to reference the faction type "Warzone"
     */
    public static final MutableString FACTION_NAME_WARZONE = new MutableString("Warzone");

    /**
     * The name to reference the faction type "Safezone"
     */
    public static final MutableString FACTION_NAME_SAFEZONE = new MutableString("Safezone");

    /**
     * The name to reference the faction type "Wilderness"
     */
    public static final MutableString FACTION_NAME_WILDERNESS = new MutableString("Wilderness");

    /**
     * The name to reference the faction type "Normal"
     */
    public static final MutableString FACTION_NAME_NORMAL = new MutableString("Normal");

    /**
     * If Faction names should tab-complete
     */
    public static final MutableBoolean FACTION_TAB_COMPLETE = new MutableBoolean(true);

    /**
     * The minimum character length to enable tab-complete
     */
    public static final MutableInt FACTION_TAB_COMPLETE_MIN = new MutableInt(0);

    /**
     * Update the values from the database.
     */
    public static void update()
    {
        FileConfiguration config = Factions.getInstance().getConfig();

        MutableBoolean save = new MutableBoolean(false);

        // how to optimize operations
        updateValue(config, save, "optimization", OPTIMIZATION);

        // the storage type to be used internally
        updateValue(config, save, "storage.type", STORAGE_TYPE);

        // database connection informatin
        updateValue(config, save, "storage.database.hostname", DATABASE_HOSTNAME);
        updateValue(config, save, "storage.database.port", DATABASE_PORT);
        updateValue(config, save, "storage.database.username", DATABASE_USERNAME);
        updateValue(config, save, "storage.database.password", DATABASE_PASSWORD);
        updateValue(config, save, "storage.database.database", DATABASE_DATABASE);
        updateValue(config, save, "storage.database.timeout", DATABASE_TIMEOUT);
        updateValue(config, save, "storage.database.description", DATABASE_CONNECTION_DESCRIPTION);

        // faction settings
        updateValue(config, save, "max-factions", FACTION_LIMIT);
        updateValue(config, save, "create.cost", CREATE_COST);
        updateValue(config, save, "create.cancellable.enabled", CREATE_CANCELLABLE);
        updateValue(config, save, "create.cancellable.silent", CREATE_SILENT_CANCEL);
        updateValue(config, save, "create.broadcast", CREATE_BROADCAST);
        updateValue(config, save, "factions.defaults.default-role.role", FACTION_DEFAULT_ROLE);
        updateValue(config, save, "factions.defaults.default-role.changeable", FACTION_DEFAULT_ROLE_CHANGEABLE);
        updateValue(config, save, "factions.names.warzone", FACTION_NAME_WARZONE);
        updateValue(config, save, "factions.names.safezone", FACTION_NAME_SAFEZONE);
        updateValue(config, save, "factions.names.wilderness", FACTION_NAME_WILDERNESS);
        updateValue(config, save, "factions.names.normal", FACTION_NAME_NORMAL);

        if (save.booleanValue())
        {
            Factions.getInstance().saveConfig();
        }
    }

    /**
     * Updates the configuration with the given information. If the value fails to load from the config because it does
     * not exist or it is in an invalid format, the system will notify the console.
     *
     * @param config   the config file to load/update.
     * @param location the location in the config.
     * @param mutable  the mutable value to update.
     */
    private static <T extends Enum<T>> void updateValue(FileConfiguration config, MutableBoolean save, String location, MutableEnum<T> mutable)
    {
        if (!config.isSet(location) || !successful(() -> mutable.setValue(Enum.valueOf(mutable.getType(), config.getString(location).toUpperCase()))))
        {
            config.set(location, mutable.getValue().toString());
            error(location);
            if (!save.booleanValue())
            {
                save.setValue(true);
            }
        }
    }

    /**
     * Updates the configuration with the given information. If the value fails to load from the config because it does
     * not exist or it is in an invalid format, the system will notify the console.
     *
     * @param config   the config file to load/update.
     * @param location the location in the config.
     * @param mutable  the mutable value to update.
     */
    private static void updateValue(FileConfiguration config, MutableBoolean save, String location, MutableString mutable)
    {
        if (!config.isSet(location) || !successful(() -> mutable.setValue(config.getString(location))))
        {
            config.set(location, mutable.getValue());
            error(location);
            if (!save.booleanValue())
            {
                save.setValue(true);
            }
        }
    }

    /**
     * Updates the configuration with the given information. If the value fails to load from the config because it does
     * not exist or it is in an invalid format, the system will notify the console.
     *
     * @param config   the config file to load/update.
     * @param location the location in the config.
     * @param mutable  the mutable value to update.
     */
    private static void updateValue(FileConfiguration config, MutableBoolean save, String location, MutableInt mutable)
    {
        if (!config.isSet(location) || !successful(() -> mutable.setValue(config.getInt(location))))
        {
            config.set(location, mutable.intValue());
            error(location);
            if (!save.booleanValue())
            {
                save.setValue(true);
            }
        }
    }

    /**
     * Updates the configuration with the given information. If the value fails to load from the config because it does
     * not exist or it is in an invalid format, the system will notify the console.
     *
     * @param config   the config file to load/update.
     * @param location the location in the config.
     * @param mutable  the mutable value to update.
     */
    private static void updateValue(FileConfiguration config, MutableBoolean save, String location, MutableDouble mutable)
    {
        if (!config.isSet(location) || !successful(() -> mutable.setValue(config.getDouble(location))))
        {
            config.set(location, mutable.doubleValue());
            error(location);
            if (!save.booleanValue())
            {
                save.setValue(true);
            }
        }
    }

    /**
     * Updates the configuration with the given information. If the value fails to load from the config because it does
     * not exist or it is in an invalid format, the system will notify the console.
     *
     * @param config   the config file to load/update.
     * @param location the location in the config.
     * @param mutable  the mutable value to update.
     */
    private static void updateValue(FileConfiguration config, MutableBoolean save, String location, MutableBoolean mutable)
    {
        if (!config.isSet(location) || !successful(() -> mutable.setValue(config.getBoolean(location))))
        {
            config.set(location, mutable.booleanValue());
            error(location);
            if (!save.booleanValue())
            {
                save.setValue(true);
            }
        }
    }

    /**
     * Used to check if an operation throws an exception with ease.
     *
     * @param runnable the operation to run.
     *
     * @return {@code true} if the operation does NOT throw an exception.<br>
     * {@code false} if the operation DOES throw an exception.
     */
    private static boolean successful(Runnable runnable)
    {
        try
        {
            runnable.run();
        }
        catch (Exception ex)
        {
            return false;
        }
        return true;
    }

    /**
     * Alerts the console that there was an error loading a config value.
     *
     * @param location the location that caused an error.
     */
    private static void error(String location)
    {
        Factions.getInstance().getLogger().severe("Error loading the config value '" + location + "'. Reverted it to default.");
    }

}
