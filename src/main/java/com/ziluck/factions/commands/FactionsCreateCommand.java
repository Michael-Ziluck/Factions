package com.ziluck.factions.commands;

import java.util.List;

import com.ziluck.factions.Factions;
import com.ziluck.factions.api.commands.CommandArgument;
import com.ziluck.factions.api.commands.CommandArgumentBuilder;
import com.ziluck.factions.api.commands.ValidCommand;
import com.ziluck.factions.base.FactionStore;
import com.ziluck.factions.base.User;
import com.ziluck.factions.base.struct.FactionType;
import com.ziluck.factions.base.struct.Permission;
import com.ziluck.factions.commands.parsers.StringParser;
import com.ziluck.factions.commands.validators.SenderFactionlessValidator;
import com.ziluck.factions.commands.validators.UnusedFactionNameValidator;
import com.ziluck.factions.configuration.Config;
import com.ziluck.factions.configuration.Lang;
import com.ziluck.factions.events.FactionCreateEvent;
import org.bukkit.Bukkit;

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
        super("create", "Create a new Faction.", Permission.CREATE, true, new String[]{ "new" });

        addSenderValidator(new SenderFactionlessValidator(0));

        addArgument(CommandArgumentBuilder.createBuilder(String.class)
                            .setName("name")
                            .setParser(new StringParser())
                            .addValidator(new UnusedFactionNameValidator(0))
                            .build());
    }

    @Override
    public void validRun(User sender, String[] label, List<CommandArgument<?>> args)
    {
        String name = (String) args.get(0).getValue();

        FactionCreateEvent event = FactionStore.getInstance().createFaction(sender, name, FactionType.NORMAL);

        Bukkit.getPluginManager().callEvent(event);

        if (Config.CREATE_CANCELLABLE.booleanValue() && event.isCancelled())
        {
            if (!Config.CREATE_SILENT_CANCEL.booleanValue())
            {
                Lang.CREATE_CANCELLED.sendError(sender);
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
            Lang.CREATE_SUCCESS.sendSuccess(sender, "{faction}", event.getFaction().getName());
        }
    }

}
