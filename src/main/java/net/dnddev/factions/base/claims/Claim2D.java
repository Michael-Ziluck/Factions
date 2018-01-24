package net.dnddev.factions.base.claims;

import java.util.Collection;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.block.Block;

import net.dnddev.factions.base.Faction;
import net.dnddev.factions.base.User;
import net.dnddev.factions.spatial.BoundedArea;

/**
 * The two-dimensional implementation of a {@link Claim}.
 * 
 * @author Michael Ziluck
 */
public class Claim2D extends BoundedArea implements Claim
{

    protected double cost;

    protected Faction faction;

    @Override
    public boolean hasCost()
    {
        return cost > 0;
    }

    @Override
    public double getCost()
    {
        return cost;
    }

    @Override
    public Faction getFaction()
    {
        return faction;
    }

    @Override
    public Collection<User> getWithin()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<User> getOwners()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isWithin(User user)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isWithin(Location location)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Set<Block> getWalls()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Location getCenter()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
