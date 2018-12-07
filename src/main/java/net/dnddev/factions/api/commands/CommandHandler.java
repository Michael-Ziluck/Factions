package net.dnddev.factions.api.commands;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.dnddev.factions.Factions;
import net.dnddev.factions.base.User;
import net.dnddev.factions.base.UserStore;
import net.dnddev.factions.configuration.Lang;

/**
 * The processor that handles all {@link ValidCommand ValidCommands}.
 * 
 * @author Michael Ziluck
 */
public class CommandHandler implements CommandExecutor, TabCompleter
{

    private static CommandHandler instance;

    private static SimpleCommandMap commandMapInstance = getCommandMap();

    private static Map<String, Command> knownCommands = getKnownCommands();

    private List<ValidCommand> commands;

    /**
     * Constructs a new CommandHandler. Will initialize the commands list.
     */
    public CommandHandler()
    {
        this.commands = new LinkedList<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        User user = UserStore.getInstance().getUser(sender);
        // this should never happen, it is just to make debugging easier
        if (user == null)
        {
            throw new IllegalStateException("User not found for sender.");
        }
        ValidCommand command = getCommand(label);
        if (command != null)
        {
            if (!command.hasPermission() || sender.hasPermission(command.getPermission().getPermission()))
            {
                command.process(user, new String[] { label }, args);
            }
            else
            {
                // The following classes also have this check: ...
                Lang.NO_PERMS.send(sender);
            }
        }
        else
        {
            return false;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args)
    {
        User user = UserStore.getInstance().getUser(sender);
        ValidCommand command = getCommand(alias);
        if (command != null)
        {
            return command.processTabComplete(user, args);
        }

        return Collections.emptyList();
    }

    /**
     * Registers a new command owned by {@link Factions}.
     * 
     * @param command the command to register.
     * @see #registerCommand(ValidCommand, JavaPlugin)
     */
    public void registerCommand(ValidCommand command)
    {
        registerCommand(command, Factions.getInstance());
    }

    /**
     * Registers the command to be used with this command handler.
     * 
     * @param command the command to register.
     * @param plugin the plugin this command is owned by.
     */
    public void registerCommand(ValidCommand command, JavaPlugin plugin)
    {
        // remove any preexisting and conflicting commands.
        for (String str : checkString(knownCommands.keySet(), command))
        {
            knownCommands.remove(str);
        }

        // create an instance of the bukkit command and set the proper values.
        PluginCommand bukkitCommand = createBukkitCommand(command.getName(), plugin);
        bukkitCommand.setAliases(Arrays.asList(command.getAliases()));
        bukkitCommand.setDescription(command.getDescription());
        bukkitCommand.setExecutor(this);
        bukkitCommand.setTabCompleter(this);

        // register the command with bukkit
        commandMapInstance.register(plugin.getDescription().getName(), bukkitCommand);

        commands.add(command);
    }

    /**
     * Gets the custom command of the given name. This can be either the command's name or one of it's aliases. Will
     * return null if no match was found, which should never happen assuming the command registration went properly.
     * 
     * @param label the label of the command.
     * @return the command, if one exists.
     */
    public ValidCommand getCommand(String label)
    {
        for (ValidCommand command : commands)
        {
            if (command.matches(label))
            {
                return command;
            }
        }
        return null;
    }

    /**
     * Initializes the singleton instance of this handler.
     */
    public static void initialize()
    {
        instance = new CommandHandler();
    }

    /**
     * @return the singleton instance of this handler.
     */
    public static CommandHandler getInstance()
    {
        return instance;
    }

    /**
     * Gets all list of all commands that have a name that conflicts with the given valid command. This ensures that the
     * commands registered in our system will always supersede any other plugin's commands.
     * 
     * @param strings the names of all existing commands.
     * @param command the command to check.
     * @return all conflicting preexisting commands.
     */
    private List<String> checkString(Collection<String> strings, ValidCommand command)
    {
        List<String> aliases = new ArrayList<>(Arrays.asList(command.getAliases()));
        aliases.add(command.getName());
        aliases.retainAll(strings);
        return aliases;
    }

    private PluginCommand createBukkitCommand(String name, JavaPlugin plugin)
    {
        PluginCommand command = null;
        try
        {
            Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            c.setAccessible(true);

            command = c.newInstance(name, plugin);
        }
        catch (SecurityException | IllegalArgumentException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException ex)
        {
            ex.printStackTrace();
        }

        return command;
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Command> getKnownCommands()
    {
        Map<String, Command> existingCommands = null;
        try
        {
            Field f = SimpleCommandMap.class.getDeclaredField("knownCommands");
            f.setAccessible(true);

            existingCommands = (Map<String, Command>) f.get(commandMapInstance);
        }
        catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex)
        {
            ex.printStackTrace();
        }

        return existingCommands;
    }

    private static SimpleCommandMap getCommandMap()
    {
        SimpleCommandMap commandMap = null;

        try
        {
            if (Bukkit.getPluginManager() instanceof SimplePluginManager)
            {
                Field f = SimplePluginManager.class.getDeclaredField("commandMap");
                f.setAccessible(true);

                commandMap = (SimpleCommandMap) f.get(Bukkit.getPluginManager());
            }
        }
        catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex)
        {
            ex.printStackTrace();
        }

        return commandMap;
    }

    /**
     * @param sender the sender of the tab complete.
     * @param lastWord the beginning of the typed argument.
     * @return the name of all players visible to the sender.
     */
    public static List<String> defaultTabComplete(User sender, String lastWord)
    {
        // if the lastWord is null, something went wrong we should exit
        if (lastWord == null)
        {
            return null;
        }

        // set the argument to lower case for easier comparison
        lastWord = lastWord.toLowerCase();

        // create the list to return
        List<String> values = new LinkedList<>();

        // create a player instance of the sender
        Player playerSender = sender instanceof Player ? (Player) sender : null;

        // go through the players
        for (Player player : Bukkit.getOnlinePlayers())
        {
            // if the sender is not a player or if the player can see the target, add them
            if ((playerSender == null || playerSender.canSee(player)) && (lastWord.equals("") || player.getName().toLowerCase().startsWith(lastWord)))
            {
                values.add(player.getName());
            }
        }
        return values;
    }

}