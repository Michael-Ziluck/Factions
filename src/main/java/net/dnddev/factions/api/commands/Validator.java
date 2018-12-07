package net.dnddev.factions.api.commands;

import net.dnddev.factions.base.User;

/**
 * Validate a feature unique to an argument in relation to the sender.
 * 
 * @author Michael Ziluck
 * @param <T> the type that this validator can process.
 */
public interface Validator<T> extends CommandElement
{

    /**
     * Validates that the argument is in the correct state. This should also send any and all error messages associated
     * with the problem with the argument.
     * 
     * @param sender the sender of the command.
     * @param label the label of the command
     * @param arg the argument to be validated.
     * @return {@code true} if the argument is valid. {@code false} of the argument is not valid.
     */
    public boolean validateArgument(User sender, String[] label, T arg);

}