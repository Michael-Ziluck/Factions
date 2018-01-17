package net.dnddev.factions.api.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.command.CommandSender;

import net.dnddev.factions.base.struct.Permission;
import net.dnddev.factions.configuration.Lang;
import net.dnddev.factions.utils.StringUtils;

/**
 * A type of {@link ValidCommand} that has added sub-commands.
 * 
 * @author Michael Ziluck
 */
public abstract class ValidBaseCommand extends ValidCommand
{

    protected List<ValidCommand> subCommands;

    /**
     * Constructs a new base command.
     * 
     * @param name the name of the command.
     * @param description the description of the command.
     * @param permission the required rank to use this command.
     * @param aliases any aliases for this command.
     */
    protected ValidBaseCommand(String name, String description, Permission permission, String[] aliases)
    {
        super(name, description, permission, aliases);

        subCommands = new LinkedList<>();
    }

    /**
     * Constructs a new base command with no aliases.
     * 
     * @param name the name of the command.
     * @param description the description of the command.
     * @param permission the required rank to use this command.
     */
    protected ValidBaseCommand(String name, String description, Permission permission)
    {
        this(name, description, permission, new String[0]);
    }

    /**
     * Constructs a new base command with no required permission.
     * 
     * @param name the name of the command.
     * @param description the description of the command.
     * @param aliases any aliases for this command.
     */
    protected ValidBaseCommand(String name, String description, String[] aliases)
    {
        this(name, description, null, aliases);
    }

    /**
     * Constructs a new base command with no aliases and no required permission.
     * 
     * @param name the name of the command.
     * @param description the description of the command.
     */
    protected ValidBaseCommand(String name, String description)
    {
        this(name, description, null, new String[0]);
    }

    @Override
    protected void process(CommandSender sender, String[] label, String[] rawArguments)
    {
        ValidCommand sub;
        if (rawArguments.length == 0 || (sub = getSubCommand(rawArguments[0])) == null)
        {
            help(sender, label);
        }
        else
        {
            if ((!hasPermission() || sender.hasPermission(getPermission().getPermission())) && (!sub.hasPermission() || sender.hasPermission(sub.getPermission().getPermission())))
            {
                sub.process(sender, StringUtils.add(label, rawArguments[0]), Arrays.copyOfRange(rawArguments, 1, rawArguments.length));
            }
            else
            {
                Lang.NO_PERMS.send(sender);
            }
        }
    }

    @Override
    public List<String> processTabComplete(CommandSender sender, String[] rawArguments)
    {
        if (rawArguments.length == 1)
        {
            return getSubCommandNames(sender, rawArguments[0]);
        }
        else
        {
            return getSubCommand(rawArguments[0]).processTabComplete(sender, Arrays.copyOfRange(rawArguments, 1, rawArguments.length));

        }
    }

    /**
     * Add a new sub command to this base command.
     * 
     * @param subCommand the sub command to add.
     */
    public void addSubCommand(ValidCommand subCommand)
    {
        subCommands.add(subCommand);
    }

    /**
     * Searches for a sub command by the given name. This can be either the command's name or one of it's aliases.
     * 
     * @param label the label sent by the player.
     * @return the sub command if one is found.
     */
    public ValidCommand getSubCommand(String label)
    {
        for (ValidCommand command : subCommands)
        {
            if (command.matches(label))
            {
                return command;
            }
        }
        return null;
    }

    /**
     * Get the name all sub commands whose name or one if it's aliases starts with the given string. The name for each
     * command will be whichever piece was provided, whether that be the alias or the name.
     * 
     * @param sender the person trying to access the sub commands.
     * @param start the beginning of the label.
     * @return the command labels if any are found.
     */
    public List<String> getSubCommandNames(CommandSender sender, String start)
    {
        List<String> commandNames = new LinkedList<>();
        if (!hasPermission() || sender.hasPermission(getPermission().getPermission()))
        {
            return commandNames;
        }
        String match;
        for (ValidCommand sub : subCommands)
        {
            if ((match = sub.getMatchingAlias(start)) != null && (!sub.hasPermission() || sender.hasPermission(sub.getPermission().getPermission())))
            {
                commandNames.add(match);
            }
        }
        return commandNames;
    }

    /**
     * Get a view of all the sub commands. This is not able to be modified and doing so will throw an exception.
     * 
     * @return all the sub commands.
     */
    public List<ValidCommand> getSubCommands()
    {
        return Collections.unmodifiableList(subCommands);
    }

    /**
     * Sends the help content to the player.
     * 
     * @param sender the one who is sending the command.
     * @param label the previous pieces of the command and the current alias.
     */
    public void help(CommandSender sender, String label[])
    {
        List<ValidCommand> allowedSubs = new LinkedList<>();
        for (ValidCommand sub : subCommands)
        {
            if (!sub.hasPermission() || sender.hasPermission(sub.getPermission().getPermission()))
            {
                allowedSubs.add(sub);
            }
        }

        if (allowedSubs.size() == 0)
        {
            Lang.NO_SUBS.send(sender);
            return;
        }

        Lang.HEADER_FOOTER.send(sender);
        for (ValidCommand sub : allowedSubs)
        {
            sender.sendMessage(" §b/" + StringUtils.compile(label) + " " + sub.getName() + ": §7" + sub.getDescription());
        }
        Lang.HEADER_FOOTER.send(sender);
    }

    @Override
    public void validRun(CommandSender sender, String[] label, List<CommandArgument<?>> arguments)
    {
    }

}