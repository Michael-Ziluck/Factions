package com.ziluck.factions.configuration;

import java.io.File;
import java.util.Arrays;

import com.ziluck.factions.Factions;
import com.ziluck.factions.base.Messageable;
import org.apache.commons.lang.mutable.MutableBoolean;
import org.bukkit.command.CommandSender;

import com.ziluck.factions.utils.CollectionUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

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
    GENERAL("general", "&6&lFACTIONS &e»&f {message}"),
    /**
     * The prefix before error messages.
     */
    ERROR("error", "&4&lERROR &c»&7 {message"),
    /**
     * The prefix before confirmation messages.
     */
    SUCCESS("success", "&3&lSUCCESS &b»&f {message}"),
    /**
     * When a player misuses a command.
     */
    USAGE("&6&lUSAGE &e» &7{message}"),
    /**
     * When players do not have permission to do something.
     */
    NO_PERMS("no_perms", "You don't have permission to do that."),
    /**
     * When a player does not have access to any sub-commands.
     */
    NO_SUBS("no_subs", "You don't have access to any sub-commands."),
    /**
     * When the console tries to run a command only usable by players.
     */
    NO_CONSOLE("no_console", "Console can't run that command."),
    /**
     * The header and footer for all commands.
     */
    HEADER_FOOTER("header_footer", "&7&m-----------------------------------"),
    /**
     * When a User tries to create a Faction with an used name.
     */
    USED_FACTION_NAME("factions.used_name", "§cThat faction name is already in use."),
    /**
     * When a create event gets cancelled for an unknown reason.
     */
    CREATE_CANCELLED("factions.create_cancelled", "§cFaction creation cancelled."),
    /**
     * What is broadcasted when a faction is created.
     */
    CREATE_BROADCAST("factions.create_broadcast", "§eThe faction §6{faction} §ewas created by §6{user}§e."),
    /**
     * What is sent to the user when they create a faction.
     */
    CREATE_SUCCESS("factions.create_success", "§eYou created the faction §6{faction}."),
    /**
     * When a player tries to look up a faction that does not exist.
     */
    FACTION_NOT_FOUND("factions.not_found", "§cNo faction by that name exists."),
    /**
     * When a player tries to create or join a faction when they already have one.
     */
    ALREADY_HAS_FACTION("factions.already_has_faction", "§cYou already have a faction."),
    /**
     * When a player tries to do something that requires having a faction.
     */
    HAS_NO_FACTION("factions.no_faction", "§cYou do not have a faction."),
    /**
     * Display information about a faction.
     */
    FACTION_SHOW("factions.show",
                 "§6______________.[ §a {faction}§6 ].______________",
                 "§6Description: §e{description}",
                 "§6Age: §e{age}",
                 "§6Flags: {open} §e| {peaceful}",
                 "§6Balance: §e{balance}");

    private String[] message;

    private final String path;

    Lang(String path, String... message)
    {
        this.path = path;
        this.message = message;
    }

    /**
     * @return the path of the language option in the actual language file. This is final and can't be changed.
     */
    public String getPath()
    {
        return path;
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
     * Sends this Lang object to the {@link Messageable} target. The parameters replace all placeholders that exist in the
     * String as well.
     *
     * @param messageable the {@link Messageable} receiving the message.
     * @param params      all additional arguments to fill placeholders.
     */
    public void send(Messageable messageable, Object... params)
    {
        messageable.sendMessage(getMessage(params));
    }

    /**
     * Sends this Lang object to the CommandSender target. The parameters replace all placeholders that exist in the
     * String as well.
     *
     * @param sender     the CommandSender receiving the message.
     * @param parameters all additional arguments to fill placeholders.
     */
    public void send(CommandSender sender, Object... parameters)
    {
        sender.sendMessage(getMessage(parameters));
    }

    /**
     * Sends this Lang object but prepended with the ERROR value as well.
     *
     * @param messageable the {@link Messageable} receiving the message.
     * @param parameters  all additional arguments to fill placeholders.
     */
    public void sendError(Messageable messageable, Object... parameters)
    {
        for (String line : getMessage(parameters))
        {
            ERROR.send(messageable, "{message}", line);
        }
    }

    /**
     * Sends this Lang object but prepended with the ERROR value as well.
     *
     * @param sender     the CommandSender receiving the message.
     * @param parameters all additional arguments to fill placeholders.
     */
    public void sendError(CommandSender sender, Object... parameters)
    {
        for (String line : getMessage(parameters))
        {
            ERROR.send(sender, "{message}", line);
        }
    }

    /**
     * Sends this Lang object but prepended with the SUCCESS value as well.
     *
     * @param messageable the {@link Messageable} receiving the message.
     * @param parameters  all additional arguments to fill placeholders.
     */
    public void sendSuccess(Messageable messageable, Object... parameters)
    {
        for (String line : getMessage(parameters))
        {
            SUCCESS.send(messageable, "{message}", line);
        }
    }

    /**
     * Sends this Lang object but prepended with the SUCCESS value as well.
     *
     * @param sender     the CommandSender receiving the message.
     * @param parameters all additional arguments to fill placeholders.
     */
    public void sendSuccess(CommandSender sender, Object... parameters)
    {
        for (String line : getMessage(parameters))
        {
            SUCCESS.send(sender, "{message}", line);
        }
    }

    /**
     * Sends this Lang object but prepended with the PREFIX value as well.
     *
     * @param sender     the CommandSender receiving the message.
     * @param parameters all additional arguments to fill placeholders.
     */
    public void sendInfo(CommandSender sender, Object... parameters)
    {
        for (String line : getMessage(parameters))
        {
            GENERAL.send(sender, "{message}", line);
        }
    }

    /**
     * Renders this message and returns it. Similar behavior to {@link #send(CommandSender, Object...)} and
     * {@link #send(Messageable, Object...)}, but instead of sending the message, it simply returns it.
     *
     * @param parameters all additional arguments to fill placeholders.
     *
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
     * @param args   the placeholders and proper content.
     *
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

    public static void update()
    {
        File langFile = new File(Factions.getInstance().getDataFolder(), "en.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(langFile);

        final MutableBoolean save = new MutableBoolean(false);

        for (Lang lang : values())
        {
            if (!config.isSet(lang.getPath()) || !Config.successful(() -> lang.setRawMessage(config.getString(lang.getPath()))))
            {
                config.set(lang.getPath(), lang.getRawMessage());
                error(lang.getPath());
                if (!save.booleanValue())
                {
                    save.setValue(true);
                }
            }
        }
    }

    /**
     * Alerts the console that there was an error loading a config value.
     *
     * @param location the location that caused an error.
     */
    private static void error(String location)
    {
        Factions.getInstance().getLogger().severe("Error loading the lang value '" + location + "'. Reverted it to default.");
    }
}
