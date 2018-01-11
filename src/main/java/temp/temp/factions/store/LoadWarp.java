package temp.temp.factions.store;

import org.apache.commons.codec.digest.DigestUtils;

import temp.temp.factions.base.Faction;
import temp.temp.factions.base.Warp;
import temp.temp.factions.spatial.LazyLocation;

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
        assertLoaded();
        return faction;
    }

    @Override
    public String getName()
    {
        assertLoaded();
        return name;
    }

    @Override
    public String getStub()
    {
        return getName().toLowerCase();
    }

    @Override
    public LazyLocation getLocation()
    {
        assertLoaded();
        return location;
    }

    @Override
    public String getPassword()
    {
        assertLoaded();
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

    protected void assertLoaded()
    {
        if (!loaded)
        {
            // TODO load
        }
        loaded = true;
    }

}
