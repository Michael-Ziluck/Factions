package net.dnddev.factions.configuration;

import java.util.Arrays;

import org.bukkit.command.CommandSender;

import net.dnddev.factions.base.Messageable;
import net.dnddev.factions.utils.CollectionUtils;

/**
 * The system for processing and sending messages to players.
 * 
 * @author Michael Ziluck
 */
public enum Lang
{

    /**
     * The prefix before most of the lang messages.
     */
    PREFIX("§6[§eFactions§6]"),
    /**
     * When players do not have permission to do something.
     */
    NO_PERMS("§cYou don't have permission to do that."),
    /**
     * When a player does not have access to any sub-commands.
     */
    NO_SUBS("§cYou don't have access to any sub-commands."),
    /**
     * When the console tries to run a command only usable by players.
     */
    NO_CONSOLE("§cConsole can't run that command."),
    /**
     * When a player misuses a command.
     */
    USAGE("§6Usage: §e§f{usage}§e."),
    /**
     * The header and footer for all commands.
     */
    HEADER_FOOTER("§7§m-----------------------------------"),
    /**
     * When a User tries to create a Faction with an used name.
     */
    USED_FACTION_NAME("§cThat faction name is already in use."),
    /**
     * When a create event gets cancelled for an unknown reason.
     */
    CREATE_CANCELLED("§cFaction creation cancelled."),
    /**
     * What is broadcasted when a faction is created.
     */
    CREATE_BROADCAST("§eThe faction §6{faction} §ewas created by §6{user}§e."),
    /**
     * What is sent to the user when they create a faction.
     */
    CREATE_SUCCESS("§eYou created the faction §6{faction}."),
    /**
     * When a player tries to look up a faction that does not exist.
     */
    FACTION_NOT_FOUND("§cNo faction by that name exists."),
    /**
     * Display information about a faction.
     */
    FACTION_SHOW("§6______________.[ §a {faction}§6 ].______________",
            "§6Description: §e{description}",
            "§6Age: §e{age}",
            "§6Flags: {open} §c| {peaceful}",
            "§6Balance: §e{balance}");

    private String[] message;

    private Lang(String... message)
    {
        this.message = message;
    }

    /**
     * Retrieves the message for this Lang object. This can be changed by editing the language configuration files, so
     * they should NOT be treated as constants. Additionally their Strings should NOT be stored to reference anything.
     * 
     * @return the message for this Lang object.
     */
    public String[] getRawMessage()
    {
        return message;
    }

    /**
     * Sets the message for this Lang object. This should not be done after startup to ensure data security.
     * 
     * @param message the new message.
     */
    public void setRawMessage(String... message)
    {
        this.message = message;
    }

    /**
     * Sends this Lang object to the Messageable target. The parameters replace all placeholders that exist in the
     * String as well.
     * 
     * @param messageable the Messageable receiving the message.
     * @param parameters all additional arguments to fill placeholders.
     */
    public void send(Messageable messageable, Object... parameters)
    {
        messageable.sendMessage(getMessage(parameters));
    }

    /**
     * Sends this Lang object to the CommandSender target. The parameters replace all placeholders that exist in the
     * String as well.
     * 
     * @param sender the CommandSender receiving the message.
     * @param parameters all additional arguments to fill placeholders.
     */
    public void send(CommandSender sender, Object... parameters)
    {
        sender.sendMessage(getMessage(parameters));
    }

    /**
     * Renders this message and returns it. Similar behavior to {@link #send(CommandSender, Object...)} and
     * {@link #send(Messageable, Object...)}, but instead of sending the message, it simply returns it.
     * 
     * @param parameters all additional arguments to fill placeholders.
     * @return the compiled message.
     */
    public String[] getMessage(Object... parameters)
    {
        String[] args = Arrays.copyOf(message, message.length);
        for (int i = 0; i < args.length; i++)
        {
            args[i] = renderString(args[i], parameters);
        }
        return args;
    }

    /**
     * Render a string with the proper parameters.
     *
     * @param string the rendered string.
     * @param args the placeholders and proper content.
     * @return the rendered string.
     */
    protected String renderString(String string, Object... args)
    {
        if (args.length % 2 != 0)
        {
            throw new IllegalArgumentException("Message rendering requires arguments of an even number. " + Arrays.toString(args) + " given.");
        }

        for (int i = 0; i < args.length; i += 2)
        {
            string = string.replace(args[i].toString(), CollectionUtils.firstNonNull(args[i + 1], "").toString());
        }

        return string;
    }

}
