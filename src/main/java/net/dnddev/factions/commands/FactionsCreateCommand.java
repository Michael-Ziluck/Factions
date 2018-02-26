package net.dnddev.factions.commands;

import java.util.List;

import org.bukkit.Bukkit;

import net.dnddev.factions.Factions;
import net.dnddev.factions.api.commands.CommandArgument;
import net.dnddev.factions.api.commands.CommandArgumentBuilder;
import net.dnddev.factions.api.commands.ValidCommand;
import net.dnddev.factions.base.Faction.Type;
import net.dnddev.factions.base.FactionStore;
import net.dnddev.factions.base.User;
import net.dnddev.factions.base.struct.Permission;
import net.dnddev.factions.commands.parsers.StringParser;
import net.dnddev.factions.commands.validators.SenderFactionlessValidator;
import net.dnddev.factions.commands.validators.UnusedFactionNameValidator;
import net.dnddev.factions.configuration.Config;
import net.dnddev.factions.configuration.Lang;
import net.dnddev.factions.events.FactionCreateEvent;

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
        super("create", "Create a new Faction.", Permission.CREATE, true, new String[] { "new" });

        addSenderValidator(new SenderFactionlessValidator());

        addArgument(CommandArgumentBuilder.createBuilder(String.class)
                .setName("name")
                .setParser(new StringParser())
                .addValidator(new UnusedFactionNameValidator())
                .build());
    }

    @Override
    public void validRun(User sender, String[] label, List<CommandArgument<?>> args)
    {
        String name = (String) args.get(0).getValue();

        FactionCreateEvent event = FactionStore.getInstance().createFaction(sender, name, Type.NORMAL);

        Bukkit.getPluginManager().callEvent(event);

        if (Config.CREATE_CANCELLABLE.booleanValue() && event.isCancelled())
        {
            if (!Config.CREATE_SILENT_CANCEL.booleanValue())
            {
                Lang.CREATE_CANCELLED.send(sender);
            }
            return;
        }

        event.complete();

        if (Config.CREATE_BROADCAST.booleanValue())
        {
            Factions.broadcastMessage(Lang.CREATE_BROADCAST.getMessage(
                    "{faction}", event.getFaction().getName(),
                    "{user}", sender.getName()));
        }
        else
        {
            Lang.CREATE_SUCCESS.send(sender,
                    "{faction}", event.getFaction().getName());
        }
    }

}
