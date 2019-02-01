package com.ziluck.factions.commands;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import com.ziluck.factions.api.commands.CommandArgument;
import com.ziluck.factions.api.commands.CommandArgumentBuilder;
import com.ziluck.factions.api.commands.ValidCommand;
import com.ziluck.factions.base.Faction;
import com.ziluck.factions.base.User;
import com.ziluck.factions.base.struct.Permission;
import com.ziluck.factions.commands.parsers.FactionParser;
import com.ziluck.factions.commands.validators.SenderHasFactionValidator;
import com.ziluck.factions.configuration.Config;
import com.ziluck.factions.configuration.Lang;
import com.ziluck.factions.utils.DateUtils;

/**
 * Command: /faction faction [faction]
 *
 * @author Michael Ziluck
 */
public class FactionFactionCommand extends ValidCommand
{

    /**
     * Constructs a new FactionFactionCommand with default settings.
     */
    public FactionFactionCommand()
    {
        super("faction", "Show information about a Faction", Permission.LOOKUP_SELF, true, new String[]{ "f", "fact" });

        addArgument(CommandArgumentBuilder.createBuilder(Faction.class)
                            .setName("faction")
                            .setParser(new FactionParser(false))
                            .addAbsentSenderValidator(new SenderHasFactionValidator(0))
                            .setAllowsConsole()
                            .setOptional()
                            .build());
    }

    @Override
    public void validRun(User sender, String[] label, List<CommandArgument<?>> args)
    {
        Faction faction;
        if (args.get(0).hasValue())
        {
            faction = (Faction) args.get(0).getValue();
        }
        else
        {
            faction = sender.getFaction();
        }

        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.forLanguageTag(Config.LOCALE.getValue()));

        Lang.FACTION_SHOW.send(sender, "{faction}", faction.getName(),
                               "{description}", faction.getDescription(),
                               "{age}", DateUtils.formatDateDiff(faction.getFounded()),
                               "{open}", (faction.isOpen() ? "§a" : "§c") + "open",
                               "{peaceful}", (faction.isPeaceful() ? "§a" : "§c") + "peaceful",
                               "{balance}", "§a" + format.format(faction.getBalance()));
        
        // TODO change output based on member,ally,enemy,neutral
    }

}
