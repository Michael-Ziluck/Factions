package net.dnddev.factions.configuration;

import org.bukkit.command.CommandSender;

import net.dnddev.factions.base.Messageable;

/**
 * The system for processesing and sending messages to players.
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
     * The header and footer for all commands.
     */
    HEADER_FOOTER("§7§m-----------------------------------");

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
    public String[] getMessage()
    {
        return message;
    }

    /**
     * Sets the message for this Lang object. This should not be done after startup to ensure data security.
     * 
     * @param message the new message.
     */
    public void setMessage(String... message)
    {
        this.message = message;
    }

    /**
     * Sends this Lang object to the Messageable target.
     * 
     * @param messageable the Messageable receiving the message.
     */
    public void send(Messageable messageable)
    {
        messageable.sendMessage(message);
    }

    /**
     * Sends this Lang object to the CommandSender target.
     * 
     * @param sender the CommandSender receiving the message.
     */
    public void send(CommandSender sender)
    {
        sender.sendMessage(message);
    }

}
