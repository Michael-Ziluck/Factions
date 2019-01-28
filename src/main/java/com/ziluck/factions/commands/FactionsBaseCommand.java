package com.ziluck.factions.commands;

import com.ziluck.factions.api.commands.ValidBaseCommand;
import com.ziluck.factions.base.struct.Permission;

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
        super("factions", "Anything and everything Factions.", Permission.BASE, new String[] { "faction", "f", "fact" });

        addSubCommand(new FactionsCreateCommand());
        addSubCommand(new FactionFactionCommand());
    }

}
