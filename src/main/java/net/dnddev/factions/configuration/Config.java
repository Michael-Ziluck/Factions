package net.dnddev.factions.configuration;

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
    public static final MutableEnum<Optimization> OPTIMIZATION = new MutableEnum<Optimization>(Optimization.PROCESS);

    /**
     * The type of Storage the system uses.
     */
    public static final MutableEnum<Storage> STORAGE_TYPE = new MutableEnum<Storage>(Storage.MONGODB);

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
    public static final MutableBoolean FACTION_DEFAULT_ROLE_CHANGABLE = new MutableBoolean(true);

    /**
     * Update the values from the database.
     */
    public static void update()
    {
        FileConfiguration config = Factions.getInstance().getConfig();

        updateValue(config, "optimization", OPTIMIZATION);
        updateValue(config, "faction-limit", FACTION_LIMIT);
        updateValue(config, "create.cost", CREATE_COST);
        updateValue(config, "create.cancellable.enabled", CREATE_CANCELLABLE);
        updateValue(config, "create.cancellable.silent", CREATE_SILENT_CANCEL);
        updateValue(config, "create.broadcast", CREATE_BROADCAST);
        updateValue(config, "storage.type", STORAGE_TYPE);
        updateValue(config, "storage.database.hostname", DATABASE_HOSTNAME);
        updateValue(config, "storage.database.port", DATABASE_PORT);
        updateValue(config, "storage.database.username", DATABASE_USERNAME);
        updateValue(config, "storage.database.password", DATABASE_PASSWORD);
        updateValue(config, "storage.database.database", DATABASE_DATABASE);
    }

    /**
     * Updates the configuration with the given information. If the value fails to load from the config because it does
     * not exist or it is in an invalid format, the system will notify the console.
     * 
     * @param config the config file to load/update.
     * @param location the location in the config.
     * @param mutable the mutable value to update.
     * @return {@code true} if the value loads from the config properly<br>
     *         {@code false} if the value did not exist in the config or it had an error loading.
     */
    private static <T extends Enum<T>> boolean updateValue(FileConfiguration config, String location, MutableEnum<T> mutable)
    {
        if (!config.isSet(location) || !successful(() -> mutable.setValue(Enum.valueOf(mutable.getType(), config.getString(location)))))
        {
            config.set(location, mutable.getValue());
            error(location);
            return false;
        }
        return true;
    }

    /**
     * Updates the configuration with the given information. If the value fails to load from the config because it does
     * not exist or it is in an invalid format, the system will notify the console.
     * 
     * @param config the config file to load/update.
     * @param location the location in the config.
     * @param mutable the mutable value to update.
     * @return {@code true} if the value loads from the config properly<br>
     *         {@code false} if the value did not exist in the config or it had an error loading.
     */
    private static <T extends Enum<T>> boolean updateValue(FileConfiguration config, String location, MutableString mutable)
    {
        if (!config.isSet(location) || !successful(() -> mutable.setValue(config.getString(location))))
        {
            config.set(location, mutable.getValue());
            error(location);
            return false;
        }
        return true;
    }

    /**
     * Updates the configuration with the given information. If the value fails to load from the config because it does
     * not exist or it is in an invalid format, the system will notify the console.
     * 
     * @param config the config file to load/update.
     * @param location the location in the config.
     * @param mutable the mutable value to update.
     * @return {@code true} if the value loads from the config properly<br>
     *         {@code false} if the value did not exist in the config or it had an error loading.
     */
    private static boolean updateValue(FileConfiguration config, String location, MutableInt mutable)
    {
        if (!config.isSet(location) || !successful(() -> mutable.setValue(config.getInt(location))))
        {
            config.set(location, mutable.intValue());
            error(location);
            return false;
        }
        return true;
    }

    /**
     * Updates the configuration with the given information. If the value fails to load from the config because it does
     * not exist or it is in an invalid format, the system will notify the console.
     * 
     * @param config the config file to load/update.
     * @param location the location in the config.
     * @param mutable the mutable value to update.
     * @return {@code true} if the value loads from the config properly<br>
     *         {@code false} if the value did not exist in the config or it had an error loading.
     */
    private static boolean updateValue(FileConfiguration config, String location, MutableDouble mutable)
    {
        if (!config.isSet(location) || !successful(() -> mutable.setValue(config.getDouble(location))))
        {
            config.set(location, mutable.doubleValue());
            error(location);
            return false;
        }
        return true;
    }

    /**
     * Updates the configuration with the given information. If the value fails to load from the config because it does
     * not exist or it is in an invalid format, the system will notify the console.
     * 
     * @param config the config file to load/update.
     * @param location the location in the config.
     * @param mutable the mutable value to update.
     * @return {@code true} if the value loads from the config properly<br>
     *         {@code false} if the value did not exist in the config or it had an error loading.
     */
    private static boolean updateValue(FileConfiguration config, String location, MutableBoolean mutable)
    {
        if (!config.isSet(location) || !successful(() -> mutable.setValue(config.getBoolean(location))))
        {
            config.set(location, mutable.booleanValue());
            error(location);
            return false;
        }
        return true;
    }

    /**
     * Used to check if an operation throws an exception with ease.
     * 
     * @param runnable the operation to run.
     * @return {@code true} if the operation does NOT throw an exception.<br>
     *         {@code false} if the operation DOES throw an exception.
     */
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
