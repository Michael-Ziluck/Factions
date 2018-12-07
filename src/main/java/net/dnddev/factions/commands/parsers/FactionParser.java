package net.dnddev.factions.commands.parsers;

import java.util.ArrayList;
import java.util.List;

import net.dnddev.factions.configuration.Config;
import org.bukkit.Bukkit;

import net.dnddev.factions.Factions;
import net.dnddev.factions.api.commands.Parser;
import net.dnddev.factions.base.Faction;
import net.dnddev.factions.base.FactionStore;
import net.dnddev.factions.base.User;
import net.dnddev.factions.configuration.Lang;
import net.dnddev.factions.events.FactionLookupEvent;

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
            Lang.FACTION_NOT_FOUND.send(sender);
            return null;
        }

        Faction faction = Factions.getFaction(rawArgument);
        if (faction == null || (faction.isWilderness() && !includeWilderness))
        {
            Lang.FACTION_NOT_FOUND.send(sender);
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
