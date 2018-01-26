package net.dnddev.factions.api.commands;

import net.dnddev.factions.base.User;

/**
 * Validate a feature unique to the Sender.
 * 
 * @author Michael Ziluck
 */
public interface SenderValidator
{

    /**
     * Validates that the sender is in the proper state.
     * 
     * @param sender the person sending the command.
     * @return {@code true} if the sender is valid.
     */
    public boolean validate(User sender);

}