package com.ziluck.factions.commands.parsers;

import java.util.ArrayList;
import java.util.List;

import com.ziluck.factions.Factions;
import com.ziluck.factions.api.commands.Parser;
import com.ziluck.factions.base.Faction;
import com.ziluck.factions.base.FactionStore;
import com.ziluck.factions.base.User;
import com.ziluck.factions.configuration.Config;
import com.ziluck.factions.configuration.Lang;
import org.bukkit.Bukkit;

import com.ziluck.factions.events.FactionLookupEvent;

/**
 * Parses Strings into Factions.
 *
 * @author Michael Ziluck
 */
public class FactionParser implements Parser<Faction>
{

    private boolean includeWilderness;

    /**
     * Create a new FactionParser which specifies whether or not to include the Wilderness.
     *
     * @param includeWilderness whether or not the wilderness should be returned instead of null.
     */
    public FactionParser(boolean includeWilderness)
    {
        this.includeWilderness = includeWilderness;
    }

    @Override
    public Faction parseArgument(User sender, String[] label, String rawArgument)
    {
        FactionLookupEvent event = new FactionLookupEvent(sender, rawArgument);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
        {
            Lang.FACTION_NOT_FOUND.sendError(sender);
            return null;
        }

        Faction faction = Factions.getFaction(rawArgument);
        if (faction == null || (faction.isWilderness() && !includeWilderness))
        {
            Lang.FACTION_NOT_FOUND.sendError(sender);
            return null;
        }
        return faction;
    }

    @Override
    public List<String> getRecommendations(User sender, String lastWord)
    {
        List<String> recs = new ArrayList<>();
        if (Config.FACTION_TAB_COMPLETE.booleanValue() && lastWord.length() >= Config.FACTION_TAB_COMPLETE_MIN.intValue())
        {
            for (Faction f : FactionStore.getInstance().getFactions())
            {
                if (f.isWilderness() && !includeWilderness)
                {
                    continue;
                }
                if (f.getName().startsWith(lastWord))
                {
                    recs.add(f.getName());
                }
            }
        }
        return recs;
    }

}
