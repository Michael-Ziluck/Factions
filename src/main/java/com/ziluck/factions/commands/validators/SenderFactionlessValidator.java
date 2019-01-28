package com.ziluck.factions.commands.validators;

import com.ziluck.factions.base.User;
import com.ziluck.factions.configuration.Lang;

/**
 * Checks if Users do not have a Faction.
 *
 * @author Michael Ziluck
 */
public class SenderFactionlessValidator extends PrioritizedSenderValidator
{

    /**
     * @param priority the priority.
     *
     * @see PrioritizedSenderValidator#PrioritizedSenderValidator(int)
     */
    public SenderFactionlessValidator(int priority)
    {
        super(priority);
    }

    @Override
    public boolean validate(User sender)
    {
        if (sender.hasFaction())
        {
            Lang.ALREADY_HAS_FACTION.sendError(sender);
            return false;
        }
        return true;
    }

}
