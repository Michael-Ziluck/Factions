package net.dnddev.factions.base.claims;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;

import net.dnddev.factions.base.Faction;
import net.dnddev.factions.base.User;
import net.dnddev.factions.base.UserStore;
import net.dnddev.factions.spatial.BlockColumn;
import net.dnddev.factions.spatial.BoundedArea;
import net.dnddev.factions.spatial.LazyLocation;

/**
 * The two-dimensional implementation of a {@link Claim}.
 *
 * @author Michael Ziluck
 */
public abstract class Claim2D extends BoundedArea implements Claim
{

    protected double cost;

    protected Faction faction;

    protected Set<User> owners;

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
        Set<User> users = new HashSet<>();
        for (User user : UserStore.getInstance().getOnlineUsers())
        {
            if (intersects(BlockColumn.fromLocation(user.getLastLocation().asBukkitLocation())))
            {
                users.add(user);
            }
        }
        return users;
    }

    @Override
    public Collection<User> getOwners()
    {
        return owners;
    }

    @Override
    public boolean isWithin(User user)
    {
        return isWithin(user.getLastLocation().asBukkitLocation());
    }

    @Override
    public boolean isWithin(Location location)
    {
        return getWorld() == location.getWorld() && BlockColumn.fromLocation(location).intersects(this);
    }

    @Override
    public LazyLocation getCenter()
    {
        return new LazyLocation((x1 + x2) / 2, getWorld().getMaxHeight() / 2.0f, (z1 + z2) / 2, getWorld());
    }

}
