package net.dnddev.factions.commands;

import net.dnddev.factions.api.commands.ValidBaseCommand;

/**
 * The base command for all of Factions.
 * 
 * @author Michael Ziluck
 */
public class FactionsBaseCommand extends ValidBaseCommand
{

    protected FactionsBaseCommand()
    {
        super("factions", "Anything and everything Factions.", new String[] { "faction", "f", "fact" });
    }

}
