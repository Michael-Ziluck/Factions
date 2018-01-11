package temp.temp.factions.store.mongodb;

import java.util.List;

import temp.temp.factions.base.User;
import temp.temp.factions.base.Warp;
import temp.temp.factions.spatial.LazyLocation;
import temp.temp.factions.store.LoadFaction;

public class MongoFaction extends LoadFaction
{

    @Override
    public void removeWarp(String name)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void clearWarps()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public User getLeader()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<User> getAdmins()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<User> getModerators()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<User> getMembers()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isPermanent()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isPeaceful()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void save()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void sendMessage(String message)
    {
        // TODO Auto-generated method stub

    }

    @Override
    protected Warp createWarp(String name, LazyLocation location, String password)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
