package com.ziluck.factions.commands.validators;

import com.ziluck.factions.api.commands.SenderValidator;

/**
 * A wrapper to simplify creating a SenderValidator with a variable priority level.
 * 
 * @author Michael Ziluck
 */
public abstract class PrioritizedSenderValidator implements SenderValidator
{

    protected int priority;

    /**
     * Constructor for passing in the priority level.
     * 
     * @param priority the priority of the sender validator.
     */
    public PrioritizedSenderValidator(int priority)
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
        return CommandElementType.SENDER_VALIDATOR;
    }

}
