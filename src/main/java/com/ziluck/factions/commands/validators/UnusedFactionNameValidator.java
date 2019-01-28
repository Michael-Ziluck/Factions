package com.ziluck.factions.commands.validators;

import com.ziluck.factions.Factions;
import com.ziluck.factions.base.User;
import com.ziluck.factions.configuration.Lang;
import org.bukkit.Bukkit;

import com.ziluck.factions.events.FactionUnusedNameCheckEvent;

/**
 * Checks if the given String has been used as a Faction name.
 * 
 * @author Michael Ziluck
 */
public class UnusedFactionNameValidator extends PrioritizedValidator<String>
{

    /**
     * @see PrioritizedSenderValidator#PrioritizedSenderValidator(int)
     * @param priority the priority.
     */
    public UnusedFactionNameValidator(int priority)
    {
        super(priority);
    }

    @Override
    public boolean validateArgument(User sender, String[] label, String arg)
    {
        FactionUnusedNameCheckEvent event = new FactionUnusedNameCheckEvent(sender, arg);

        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled() || Factions.getFaction(arg) != null)
        {
            Lang.USED_FACTION_NAME.sendError(sender);
            return false;
        }

        return true;
    }

}
