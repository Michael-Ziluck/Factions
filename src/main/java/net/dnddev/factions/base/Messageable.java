package net.dnddev.factions.base;

import java.util.Collection;

/**
 * Represents anything that is able to receive a string based message.
 *
 * @author Michael Ziluck
 */
public interface Messageable
{
    /**
     * Sends the given messages to this entity.
     *
     * @param messages the messages to send.
     */
    public void sendMessage(String... messages);

    /**
     * Sends the given messages to this entity.
     *
     * @param messages the messages to send.
     */
    public void sendMessage(Collection<String> messages);

}
