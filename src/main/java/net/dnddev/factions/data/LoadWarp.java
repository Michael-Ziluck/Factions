package net.dnddev.factions.data;

import org.apache.commons.codec.digest.DigestUtils;

import net.dnddev.factions.base.Faction;
import net.dnddev.factions.base.Warp;
import net.dnddev.factions.configuration.Config;
import net.dnddev.factions.configuration.struct.Optimization;
import net.dnddev.factions.spatial.LazyLocation;

/**
 * The in-memory representation of a Warp.
 * <p>
 * To help reduce clutter and duplicate code, Warps have an additional middle step between the interface and the
 * implementation that loads information to the database.
 * </p>
 *
 * @author Michael Ziluck
 */
public abstract class LoadWarp implements Warp
{

    protected Faction faction;

    protected String name;

    protected String stub;

    protected LazyLocation location;

    protected boolean locked;

    protected String password;

    protected boolean loaded;

    @Override
    public Faction getFaction()
    {
        return faction;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public String getStub()
    {
        if (Config.OPTIMIZATION.getValue() == Optimization.MEMORY)
        {
            return getName().toLowerCase();
        }

        if (stub == null)
        {
            stub = getName().toLowerCase();
        }

        return stub;
    }

    @Override
    public LazyLocation getLocation()
    {
        return location;
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    @Override
    public boolean hasPassword()
    {
        return getPassword() == null;
    }

    @Override
    public boolean isPassword(String check)
    {
        if (!hasPassword())
        {
            return false;
        }
        return DigestUtils.md5Hex(check).equals(getPassword());
    }

    @Override
    public void setPassword(String password)
    {
        this.password = DigestUtils.md5Hex(password);
    }

}
