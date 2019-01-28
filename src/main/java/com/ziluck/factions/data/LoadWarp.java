package com.ziluck.factions.data;

import com.ziluck.factions.base.Warp;
import com.ziluck.factions.configuration.Config;
import com.ziluck.factions.configuration.struct.Optimization;
import com.ziluck.factions.spatial.LazyLocation;
import org.apache.commons.codec.digest.DigestUtils;

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
    protected String name;

    protected String stub;

    protected LazyLocation location;

    protected boolean locked;

    protected String password;

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
