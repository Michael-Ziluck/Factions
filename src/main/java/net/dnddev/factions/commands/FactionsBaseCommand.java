package net.dnddev.factions.commands;

import net.dnddev.factions.api.commands.ValidBaseCommand;

/**
 * The base command for all of Factions.
 * 
 * @author Michael Ziluck
 */
public class FactionsBaseCommand extends ValidBaseCommand
{

    /**
     * Construct a new FactionsBaseCommand
     */
    public FactionsBaseCommand()
    {
        super("factions", "Anything and everything Factions.", new String[] { "faction", "f", "fact" });
        
        addSubCommand(new FactionsCreateCommand());
    }

}
