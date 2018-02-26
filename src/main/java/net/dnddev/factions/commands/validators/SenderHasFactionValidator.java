package net.dnddev.factions.commands.validators;

import net.dnddev.factions.api.commands.SenderValidator;
import net.dnddev.factions.base.User;
import net.dnddev.factions.configuration.Lang;

/**
 * Checks if Users have a Faction.
 * 
 * @author Michael Ziluck
 */
public class SenderHasFactionValidator implements SenderValidator
{

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
