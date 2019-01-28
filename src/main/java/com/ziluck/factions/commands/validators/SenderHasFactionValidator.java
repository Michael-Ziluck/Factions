package com.ziluck.factions.commands.validators;

import com.ziluck.factions.base.User;
import com.ziluck.factions.configuration.Lang;

/**
 * Checks if Users have a Faction.
 *
 * @author Michael Ziluck
 */
public class SenderHasFactionValidator extends PrioritizedSenderValidator
{

    /**
     * @param priority the priority.
     *
     * @see PrioritizedSenderValidator#PrioritizedSenderValidator(int)
     */
    public SenderHasFactionValidator(int priority)
    {
        super(priority);
    }

    @Override
    public boolean validate(User sender)
    {
        if (!sender.hasFaction())
        {
            Lang.HAS_NO_FACTION.sendError(sender);
            return false;
        }
        return true;
    }

}
