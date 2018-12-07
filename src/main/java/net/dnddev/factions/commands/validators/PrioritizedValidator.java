package net.dnddev.factions.commands.validators;

import net.dnddev.factions.api.commands.Validator;

/**
 * A wrapper to simplify creating a SenderValidator with a variable priority level.
 * 
 * @author Michael Ziluck
 * @param <T> The type of the argument to be validated.
 */
public abstract class PrioritizedValidator<T> implements Validator<T>
{

    protected int priority;

    /**
     * Constructor for passing in the priority level.
     * 
     * @param priority the priority of the sender validator.
     */
    public PrioritizedValidator(int priority)
    {
        this.priority = priority;
    }

    @Override
    public int getPriority()
    {
        return priority;
    }

    @Override
    public CommandElementType getType()
    {
        return CommandElementType.ARGUMENT_VALIDATOR;
    }

}
