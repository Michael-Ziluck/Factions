package com.ziluck.factions.api.commands;

import java.util.Iterator;
import java.util.List;

import com.ziluck.factions.base.User;

/**
 * Used to convert passed arguments from Strings to their proper type.
 * 
 * @author Michael Ziluck
 * @param <T> the type to convert the String to.
 */
public interface Parser<T>
{

    /**
     * Parses the argument from the raw string into the appropriate type. If the argument can't be parsed
     * 
     * @param sender the sender of the command.
     * @param label the label of the command
     * @param rawArgument the argument to be parsed.
     * @return the successfully parsed argument.
     */
    public T parseArgument(User sender, String[] label, String rawArgument);

    /**
     * Get tab complete recommendations for an argument with this given parser. If the default is wanted, it exists in
     * {@link CommandHandler#defaultTabComplete(User, String)}.
     * 
     * @param sender the sender of the tab complete.
     * @param lastWord the content of the item so far.
     * @return the recommendations.
     */
    public List<String> getRecommendations(User sender, String lastWord);

    /**
     * Filter out values from the list of Strings that do not start with the given last word.
     * 
     * @param values the values to prune.
     * @param lastWord the content of the item so far.
     */
    public static void pruneSuggestions(List<String> values, String lastWord)
    {
        if (values == null || values.size() == 0)
        {
            return;
        }
        lastWord = lastWord.toLowerCase();
        Iterator<String> it = values.iterator();
        while (it.hasNext())
        {
            if (!it.next().toLowerCase().startsWith(lastWord))
            {
                it.remove();
            }
        }
    }

}