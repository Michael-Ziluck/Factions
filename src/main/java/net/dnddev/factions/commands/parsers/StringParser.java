package net.dnddev.factions.commands.parsers;

import java.util.List;

import org.bukkit.command.CommandSender;

import net.dnddev.factions.api.commands.Parser;

/**
 * Parser for converting a String to a String. This will always return itself.
 * 
 * @author Michael Ziluck
 */
public class StringParser implements Parser<String>
{

    @Override
    public String parseArgument(CommandSender sender, String[] label, String rawArgument)
    {
        return rawArgument;
    }

    @Override
    public List<String> getRecommendations(CommandSender sender, String lastWord)
    {
        return null;
    }

}
