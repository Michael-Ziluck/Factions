package net.dnddev.factions.commands.validators;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import net.dnddev.factions.Factions;
import net.dnddev.factions.api.commands.Validator;
import net.dnddev.factions.configuration.Lang;
import net.dnddev.factions.events.FactionUnusedNameCheckEvent;

/**
 * Checks if the given String has been used as a Faction name.
 * 
 * @author Michael Ziluck
 */
public class UnusedFactionNameValidator implements Validator<String>
{

    @Override
    public boolean validateArgument(CommandSender sender, String[] label, String arg)
    {
        FactionUnusedNameCheckEvent event = new FactionUnusedNameCheckEvent(sender, arg);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled() || Factions.getFaction(arg) != null)
        {
            Lang.USED_FACTION_NAME.send(sender);
            return false;
        }
        return true;
    }

}
