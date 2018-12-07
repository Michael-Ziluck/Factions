package net.dnddev.factions.commands.validators;

import net.dnddev.factions.base.User;
import net.dnddev.factions.configuration.Lang;

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
            Lang.HAS_NO_FACTION.send(sender);
            return false;
        }
        return true;
    }

}
