package net.dnddev.factions.configuration;

import net.dnddev.factions.configuration.structs.Optimization;

/**
 * The manager for all configuration settings.
 * <p>
 * This plugin is designed to be as configurable as possible in order to make it usable for almost everyone who wants a
 * way to group players together. Almost every single feature can be turned on and off, and this manager allows that to
 * happen.</p>
 * <p>
 * One of the biggest things that this plugin allows servers to decide is whether things should be optimized to reduce
 * memory usage or processing power. Any time a new structure is used, this manager helps make the decision of whether
 * or not to optimize it to use more memory or processing power.
 * </p>
 * <p>
 * Larger servers (more than ~40 people) should always choose to use memory over processing power, and unless you are on
 * a system with EXTREMELY low (less than 1 GB) RAM, the processing optimizer should be used.
 * </p>
 * 
 * @author Michael Ziluck
 */
public class ConfigurationManager
{

    private Optimization optimization;

    /**
     * Retrieves what the plugin is currently optimizing for.
     * 
     * @return the type of optimization.
     */
    public Optimization getOptimization()
    {
        return optimization;
    }

}
