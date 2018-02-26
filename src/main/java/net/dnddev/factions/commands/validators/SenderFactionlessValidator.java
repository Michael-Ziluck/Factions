package net.dnddev.factions.commands.validators;

import net.dnddev.factions.api.commands.SenderValidator;
import net.dnddev.factions.base.User;
import net.dnddev.factions.configuration.Lang;

/**
 * Checks if Users do not have a Faction.
 * 
 * @author Michael Ziluck
 */
public class SenderFactionlessValidator implements SenderValidator
{

    @Override
    public boolean validate(User sender)
    {
        if (sender.hasFaction())
        {
            Lang.ALREADY_HAS_FACTION.send(sender);
            return false;
        }
        return true;
    }

}
