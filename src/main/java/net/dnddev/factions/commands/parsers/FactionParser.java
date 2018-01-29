package net.dnddev.factions.commands.parsers;

import java.util.ArrayList;
import java.util.List;

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
        if (faction == null)
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
        for (Faction f : FactionStore.getInstance().getFactions())
        {
            if (f.getName().startsWith(lastWord))
            {
                recs.add(f.getName());
            }
        }
        return recs;
    }

}