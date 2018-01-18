package net.dnddev.factions.data;

import net.dnddev.factions.base.User;

/**
 * The in-memory representation of a User.
 * <p>
 * To help reduce clutter and duplicate code, Users have an additional middle step between the interface and the
 * implementation that loads information to the database.
 * </p>
 * 
 * @author Michael Ziluck
 */
public abstract class LoadUser implements User
{

}
