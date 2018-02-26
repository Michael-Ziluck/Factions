package net.dnddev.factions.commands;

import java.util.List;

import net.dnddev.factions.api.commands.CommandArgument;
import net.dnddev.factions.api.commands.CommandArgumentBuilder;
import net.dnddev.factions.api.commands.ValidCommand;
import net.dnddev.factions.base.Faction;
import net.dnddev.factions.base.User;
import net.dnddev.factions.base.struct.Permission;
import net.dnddev.factions.commands.parsers.FactionParser;
import net.dnddev.factions.commands.validators.SenderHasFactionValidator;
import net.dnddev.factions.configuration.Lang;
import net.dnddev.factions.utils.DateUtils;

/**
 * Command: /faction faction [faction]
 * 
 * @author Michael Ziluck
 */
public class FactionFactionCommand extends ValidCommand
{

    private SenderHasFactionValidator validator = new SenderHasFactionValidator();

    /**
     * Constructs a new FactionFactionCommand with default settings.
     */
    public FactionFactionCommand()
    {
        super("faction", "Show information about a Faction", Permission.LOOKUP_SELF, true, new String[] { "f", "fact" });

        addArgument(CommandArgumentBuilder.createBuilder(Faction.class)
                .setName("faction")
                .setParser(new FactionParser(false))
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
            if (!validator.validate(sender))
            {
                return;
            }
            faction = sender.getFaction();
        }

        Lang.FACTION_SHOW.send(sender,
                "{faction}", faction.getName(),
                "{description}", faction.getDescription(),
                "{age}", DateUtils.formatDateDiff(faction.getFounded()),
                "{open}", (faction.isOpen() ? "§a" : "§c") + "open",
                "{peaceful}", (faction.isPeaceful() ? "§a" : "§c") + "peaceful",
                "{balance}", "§a$0");
        // TODO add balance
    }

}
