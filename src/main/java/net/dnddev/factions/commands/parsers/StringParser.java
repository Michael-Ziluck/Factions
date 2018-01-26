package net.dnddev.factions.commands.parsers;

import java.util.List;

import net.dnddev.factions.api.commands.Parser;
import net.dnddev.factions.base.User;

/**
 * Parser for converting a String to a String. This will always return itself.
 * 
 * @author Michael Ziluck
 */
public class StringParser implements Parser<String>
{

    @Override
    public String parseArgument(User sender, String[] label, String rawArgument)
    {
        return rawArgument;
    }

    @Override
    public List<String> getRecommendations(User sender, String lastWord)
    {
        return null;
    }

}
