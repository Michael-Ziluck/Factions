package net.dnddev.factions.commands;

import java.util.List;

import org.bukkit.command.CommandSender;

import net.dnddev.factions.api.commands.CommandArgument;
import net.dnddev.factions.api.commands.CommandArgumentBuilder;
import net.dnddev.factions.api.commands.ValidCommand;
import net.dnddev.factions.base.struct.Permission;
import net.dnddev.factions.commands.parsers.StringParser;
import net.dnddev.factions.commands.validators.UnusedFactionNameValidator;

/**
 * Command to create a new Faction.
 * 
 * @author Michael Ziluck
 */
public class FactionsCreateCommand extends ValidCommand
{

    /**
     * Base constructor.
     */
    public FactionsCreateCommand()
    {
        super("create", "Create a new Faction.", Permission.CREATE, new String[] { "new" });

        addArgument(CommandArgumentBuilder.createBuilder(String.class)
                .setName("name")
                .setParser(new StringParser())
                .addValidator(new UnusedFactionNameValidator())
                .build());
    }

    @Override
    public void validRun(CommandSender sender, String[] label, List<CommandArgument<?>> arguments)
    {
        
    }

}
