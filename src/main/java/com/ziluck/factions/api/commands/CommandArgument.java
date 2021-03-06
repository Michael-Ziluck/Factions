package com.ziluck.factions.api.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import com.ziluck.factions.base.User;
import com.ziluck.factions.base.struct.Permission;
import com.ziluck.factions.configuration.Lang;
import com.ziluck.factions.utils.PriorityValue;

/**
 * @param <T> the type the argument will be after being parsed.
 *
 * @author Michael Ziluck
 */
public class CommandArgument<T>
{

    protected ValidCommand command;

    protected T value;

    protected String name;

    protected boolean optional;

    protected boolean variableLength;

    protected boolean allowsConsole;

    protected int ordinal;

    protected Queue<PriorityValue<SenderValidator>> senderValidators;

    protected Queue<PriorityValue<SenderValidator>> absentSenderValidators;

    protected Queue<PriorityValue<Validator<T>>> validators;

    protected Parser<T> parser;

    protected Permission permission;

    protected String[] tabOptions;

    /**
     * Constructs a new argument. This will also initialize the validators list.
     */
    protected CommandArgument()
    {
        this.senderValidators = new PriorityQueue<>();
        this.absentSenderValidators = new PriorityQueue<>();
        this.validators = new PriorityQueue<>();
    }

    /**
     * Process the given argument. This will parse it into it's appropriate form, as well as validate that it is in the
     * proper state. The value returned represents whether or not the process was successful. No error message is
     * required beyond what was already sent within the parsers and validators.
     *
     * @param sender   the sender of the command.
     * @param label    the label of the command.
     * @param argument the raw string argument to be processed.
     *
     * @return {@code true} if the process was successful. Otherwise, returns {@code false}.
     */
    protected boolean process(User sender, String[] label, String argument)
    {
        if (hasPermission() && !sender.hasPermission(permission.getPermission()))
        {
            Lang.NO_PERMS.sendError(sender);
            return false;
        }

        for (PriorityValue<SenderValidator> pair : senderValidators)
        {
            if (!pair.getValue().validate(sender))
            {
                return false;
            }
        }

        value = parser.parseArgument(sender, label, argument);

        if (value == null)
        {
            return false;
        }

        for (PriorityValue<Validator<T>> pair : validators)
        {
            if (!pair.getValue().validateArgument(sender, label, value))
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Retrieves all potential options a player can have when trying to tab-complete this argument. This should always
     * be implemented. However, because that most likely will not happen, it defaults to the same behavior that Bukkit
     * uses which is to list off the names of all visible connected players.
     *
     * @param sender   the sender who is tab-completing.
     * @param lastWord the last word in the player's tab complete.
     *
     * @return all argument options.
     */
    protected List<String> getRecommendations(User sender, String lastWord)
    {
        List<String> recommendations = parser.getRecommendations(sender, lastWord);
        if (recommendations == null)
        {
            recommendations = CommandHandler.defaultTabComplete(sender, lastWord);
        }
        return recommendations;
    }

    /**
     * Clears the most recent value processed by this argument.
     */
    public void clearValue()
    {
        value = null;
    }

    /**
     * @return {@code true} if the argument has a required rank. Otherwise returns {@code false}.
     */
    public boolean hasPermission()
    {
        return permission != null;
    }

    /**
     * The ability to use an argument might differ from the ability to use a command in general. This is the rank
     * required to use this argument within a command.
     *
     * @return the rank required to use this argument.
     */
    public Permission getPermission()
    {
        return permission;
    }

    /**
     * Sets the required permission for this argument. This should only be set once, and should only be changed from the
     * CommandArgumentBuilder. Will throw an {@link IllegalStateException} if set a second time.
     *
     * @param permission the required permission.
     */
    public void setPermission(Permission permission)
    {
        if (this.permission != null)
        {
            throw new IllegalArgumentException("Permission can only be set once.");
        }
        this.permission = permission;
    }

    /**
     * @return the parser for this argument.
     */
    public Parser<T> getParser()
    {
        return parser;
    }

    /**
     * Sets the parser for this argument. This should only be set once, and should only be changed from the
     * CommandArgumentBuilder. Will throw an {@link IllegalStateException} if set a second time.
     *
     * @param parser the argument's parser.
     */
    protected void setParser(Parser<T> parser)
    {
        if (this.parser != null)
        {
            throw new IllegalArgumentException("Parser can only be set once.");
        }
        this.parser = parser;
    }

    /**
     * Returns a view of the validators. This view can't be changed, and will throw exceptions if it is attempted.
     *
     * @return the validators for this argument.
     */
    public List<Validator<T>> getValidators()
    {
        List<Validator<T>> list = new ArrayList<>(validators.size());
        for (PriorityValue<Validator<T>> pair : validators)
        {
            list.add(pair.getValue());
        }
        return list;
    }

    /**
     * Adds a new validator to the argument.
     *
     * @param validator the new validator.
     * @param priority  the priority of the validator
     */
    public void addValidator(Validator<T> validator, int priority)
    {
        this.validators.add(new PriorityValue<>(validator, priority));
    }

    /**
     * Returns a view of the sender validators. This view can't be changed, and will throw an exception if it is
     * attempted.
     *
     * @return the sender validators for this argument
     */
    public List<SenderValidator> getSenderValidators()
    {
        List<SenderValidator> list = new ArrayList<>(senderValidators.size());
        for (PriorityValue<SenderValidator> pair : senderValidators)
        {
            list.add(pair.getValue());
        }
        return list;
    }

    /**
     * Adds a new sender validator to the argument.
     *
     * @param senderValidator the new sender validator.
     * @param priority        the priority of the validator.
     */
    public void addSenderValidator(SenderValidator senderValidator, int priority)
    {
        this.senderValidators.add(new PriorityValue<>(senderValidator, priority));
    }

    /**
     * Returns a view of the sender validators that are run when the argument is absent. This view can't be changed, and
     * will throw an exception if it is attempted.
     *
     * @return the absent sender validators for this argument.
     */
    public List<SenderValidator> getAbsentSenderValidators()
    {
        List<SenderValidator> list = new ArrayList<>(absentSenderValidators.size());
        for (PriorityValue<SenderValidator> pair : absentSenderValidators)
        {
            list.add(pair.getValue());
        }
        return list;
    }

    /**
     * Adds a new absent sender validator to the argument.
     *
     * @param absentSenderValidator the new absent sender validator.
     * @param priority              the priority of the validator.
     */
    public void addAbsentSenderValidator(SenderValidator absentSenderValidator, int priority)
    {
        this.absentSenderValidators.add(new PriorityValue<>(absentSenderValidator, priority));
    }

    /**
     * Called to validate the sender when this argument is absent. It will call all the absent sender validators.
     *
     * @param sender the sender of the command.
     *
     * @return {@code true} if the absent validators pass.
     */
    public boolean absent(User sender)
    {
        for (PriorityValue<SenderValidator> pair : absentSenderValidators)
        {
            if (!pair.getValue().validate(sender))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets where in the order of arguments this argument falls for a particular command. The default is -1, so if -1 is
     * returned it means that this argument has not yet been assigned to a command.
     *
     * @return the argument ordinal.
     */
    public int getOrdinal()
    {
        return ordinal;
    }

    /**
     * Sets the order that this argument falls for a particular command. This method is protected to make sure that the
     * CommandHandler does not break when managing the argument system.
     *
     * @param ordinal the new argument ordinal.
     */
    protected void setOrdinal(int ordinal)
    {
        this.ordinal = ordinal;
    }

    /**
     * @return {@code true} if this argument can't be used by console. Otherwise returns {@code false}.
     */
    public boolean allowsConsole()
    {
        return allowsConsole;
    }

    /**
     * @param status {@code true} if this argument can't be used by console.
     */
    public void setAllowsConsole(boolean status)
    {
        this.allowsConsole = status;
    }

    /**
     * @return {@code true} if this argument has variable length. Otherwise returns {@code false}.
     */
    public boolean hasVariableLength()
    {
        return variableLength;
    }

    /**
     * @param status {@code true} if this argument has variable length.
     */
    public void setVariableLength(boolean status)
    {
        this.variableLength = status;
    }

    /**
     * @return whether this argument is optional or not.
     */
    public boolean isOptional()
    {
        return optional;
    }

    /**
     * @param state whether or not the argument is optional.
     */
    public void setOptional(boolean state)
    {
        this.optional = state;
    }

    /**
     * @return the name of the argument
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the name for the argument. This should only be set once, and should only be changed from the
     * CommandArgumentBuilder. Will throw an {@link IllegalStateException} if set a second time.
     *
     * @param name the name of the argument.
     */
    public void setName(String name)
    {
        if (this.name != null)
        {
            throw new IllegalStateException("Name can only be set once.");
        }
        this.name = name;
    }

    /**
     * This should only be used to check if an optional argument has a value.
     *
     * @return {@code true} if this argument has a value.
     */
    public boolean hasValue()
    {
        return value != null;
    }

    /**
     * Gets the value that was just parsed and validated.
     *
     * @return the value.
     */
    public T getValue()
    {
        if (value == null)
        {
            throw new IllegalStateException("Argument has not been processed.");
        }
        return value;
    }

    /**
     * @return the command this argument belongs to.
     */
    protected ValidCommand getCommand()
    {
        return command;
    }

    /**
     * @param command the command this argument belongs to.
     */
    protected void setCommand(ValidCommand command)
    {
        this.command = command;
    }

}