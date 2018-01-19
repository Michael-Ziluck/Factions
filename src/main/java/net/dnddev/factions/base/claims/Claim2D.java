package net.dnddev.factions.base.claims;

import java.util.Collection;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.block.Block;

import com.github.davidmoten.rtree.geometry.Geometry;
import com.github.davidmoten.rtree.geometry.Rectangle;

import net.dnddev.factions.base.Faction;
import net.dnddev.factions.base.User;

/**
 * The two-dimensional implementation of a {@link Claim}.
 * 
 * @author Michael Ziluck
 */
public class Claim2D implements Claim, Rectangle
{

    protected int x1;
    protected int x2;

    protected int z1;
    protected int z2;
    
    protected double cost;

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
    public double distance(Rectangle r)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Rectangle mbr()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean intersects(Rectangle r)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Geometry geometry()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public float x1()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public float y1()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public float x2()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public float y2()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public float area()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Rectangle add(Rectangle r)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean contains(double x, double y)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public float intersectionArea(Rectangle r)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public float perimeter()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Faction getFaction()
    {
        // TODO Auto-generated method stub
        return null;
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
