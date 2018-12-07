package net.dnddev.factions.spatial;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import com.github.davidmoten.rtree.geometry.Geometry;
import com.github.davidmoten.rtree.geometry.Rectangle;

import net.dnddev.factions.utils.GeometryUtils;

/**
 * Represents a two-dimensional bounded area.
 * <p>
 * BoundedArea also implements the R-Tree interface {@link Rectangle}. This allows spatial and geometric operations to
 * be performed on it with ease.
 * </p>
 *
 * @author Michael Ziluck
 */
public class BoundedArea implements Rectangle
{

    protected float x1;
    protected float z1;
    protected float x2;
    protected float z2;

    protected String world;

    private World parsedWorld;

    /**
     * Constructs a new BoundedArea with the two given bounds. If the two bounds are in different worlds, an
     * {@link IllegalArgumentException} will be thrown.
     *
     * @param pointOne the first point.
     * @param pointTwo the second point.
     */
    public BoundedArea(BlockColumn pointOne, BlockColumn pointTwo)
    {
        if (pointOne == null || pointTwo == null)
        {
            throw new IllegalArgumentException("Neither point can be null.");
        }
        this.x1 = Math.min(pointOne.getX(), pointTwo.getX());
        this.x2 = Math.max(pointOne.getX(), pointTwo.getX());
        this.z1 = Math.min(pointOne.getZ(), pointTwo.getZ());
        this.z2 = Math.max(pointOne.getZ(), pointTwo.getZ());

        if (pointOne.getWorld() != pointTwo.getWorld())
        {
            throw new IllegalArgumentException("Points must be in the same world.");
        }

        setWorld(pointOne.getWorld());
    }

    /**
     * Empty constructor for ORMs.
     */
    public BoundedArea()
    {
    }

    /**
     * Constructs a new BoundedArea with the given coordinates.
     *
     * @param x1    the lowest x.
     * @param x2    the greatest x.
     * @param z1    the lowest z.
     * @param z2    the greatest z.
     * @param world the world.
     */
    public BoundedArea(float x1, float x2, float z1, float z2, World world)
    {

        this.x1 = Math.min(x1, x2);
        this.x2 = Math.max(x1, x2);
        this.z1 = Math.min(z1, z2);
        this.z2 = Math.max(z1, z2);

        setWorld(world);
    }

    /**
     * @return the minX
     */
    public final float getMinX()
    {
        return x1;
    }

    /**
     * @param minX the minX to set
     */
    public final void setMinX(float minX)
    {
        this.x1 = minX;
    }

    /**
     * @return the minZ
     */
    public final float getMinZ()
    {
        return z1;
    }

    /**
     * @param minZ the minZ to set
     */
    public final void setMinZ(float minZ)
    {
        this.z1 = minZ;
    }

    /**
     * @return the maxX
     */
    public final float getMaxX()
    {
        return x2;
    }

    /**
     * @param maxX the maxX to set
     */
    public final void setMaxX(float maxX)
    {
        this.x2 = maxX;
    }

    /**
     * @return the maxZ
     */
    public final float getMaxZ()
    {
        return z2;
    }

    /**
     * @param maxZ the maxZ to set
     */
    public final void setMaxZ(int maxZ)
    {
        this.z2 = maxZ;
    }

    /**
     * Check if the given location is within the bounded area.
     *
     * @param location the location to check.
     *
     * @return {@code true} if this location is within the bounded area.
     */
    public final boolean contains(Location location)
    {
        return containsHelper(location.getWorld(), location.getX(), location.getZ());
    }

    /**
     * Check if the given block column is within the bounded area.
     *
     * @param blockColumn the block column to check.
     *
     * @return {@code true} if this block column is within the bounded area.
     */
    public final boolean contains(BlockColumn blockColumn)
    {
        return containsHelper(blockColumn.getWorld(), blockColumn.getX(), blockColumn.getZ());
    }

    private boolean containsHelper(World world, double x, double z)
    {
        if (world != getWorld())
        {
            return false;
        }
        return x >= getMinX() && x <= getMaxX() && z >= getMinZ() && z <= getMaxZ();
    }

    /**
     * @return the world
     */
    public final World getWorld()
    {
        if (parsedWorld == null)
        {
            parsedWorld = Bukkit.getWorld(world);
        }
        return parsedWorld;
    }

    /**
     * @param parsedWorld the world to set
     */
    public final void setWorld(World parsedWorld)
    {
        this.parsedWorld = parsedWorld;
        this.world = parsedWorld.getName();
    }

    @Override
    public final double distance(Rectangle r)
    {
        return GeometryUtils.distance(x1(), y1(), x2(), y2(), r.x1(), r.y1(), r.x2(), r.y2());
    }

    @Override
    public final boolean intersects(Rectangle r)
    {
        return GeometryUtils.intersects(x1(), y1(), x2(), y2(), r.x1(), r.y1(), r.x2(), r.y2());
    }

    @Override
    public final Rectangle mbr()
    {
        return this;
    }

    @Override
    public final Geometry geometry()
    {
        return this;
    }

    @Override
    public final Rectangle add(Rectangle r)
    {
        return new BoundedArea(x1 + r.x1(), x2 + r.x2(), z1 + r.y1(), z2 + r.y2(), getWorld());
    }

    @Override
    public final float area()
    {
        return (x2() - x1()) * (y2() - y1());
    }

    @Override
    public final boolean contains(double x, double y)
    {
        return x >= x1() && x <= x2() && y >= y1() && y <= y2();
    }

    @Override
    public final float intersectionArea(Rectangle r)
    {
        if (!intersects(r))
        {
            return 0;
        }
        else
        {
            return GeometryUtils.create(Math.max(x1(), r.x1()), Math.max(y1(), r.y1()), Math.min(x2, r.x2()), Math.min(y2(), r.y2())).area();
        }
    }

    @Override
    public final float perimeter()
    {
        return 2 * (x2() - x1()) + 2 * (y2() - y1());
    }

    @Override
    public final float x1()
    {
        return x1;
    }

    @Override
    public final float x2()
    {
        return x2;
    }

    @Override
    public final float y1()
    {
        return z1;
    }

    @Override
    public final float y2()
    {
        return z2;
    }

    /**
     * Get the length of the BoundedArea.
     *
     * @return the length.
     */
    public final float getLength()
    {
        return y2() - y1() + 1;
    }

    /**
     * Get the width of the BoundedArea;
     *
     * @return the width;
     */
    public final float getWidth()
    {
        return x2() - x1() + 1;
    }

    /**
     * Get the Blocks that make up the walls of this BoundedArea. This will also return the floor and roof.
     *
     * @return the Blocks of the walls.
     */
    public Set<Block> getWalls()
    {
        HashSet<Block> walls = new HashSet<>();
        float          i, j;
        World          w     = getWorld();

        for (i = this.x1; i <= this.x2; i++)
        {
            for (j = 0; j <= w.getMaxHeight(); j++)
            {
                walls.add(w.getBlockAt((int) i, (int) j, (int) getMinZ()));
                walls.add(w.getBlockAt((int) i, (int) j, (int) getMaxZ()));
            }
        }
        for (i = this.z1; i <= this.z2; i++)
        {
            for (j = 0; j <= w.getMaxHeight(); j++)
            {
                walls.add(w.getBlockAt((int) getMinX(), (int) j, (int) i));
                walls.add(w.getBlockAt((int) getMaxX(), (int) j, (int) i));
            }
        }
        for (i = this.x1; i <= this.x2; i++)
        {
            for (j = this.z1; j <= this.z2; j++)
            {
                walls.add(w.getBlockAt((int) i, 0, (int) j));
                walls.add(w.getBlockAt((int) i, w.getMaxHeight(), (int) j));
            }
        }
        return walls;

    }

    @Override
    public String toString()
    {
        return "[x1: " + x1 + " x2:" + x2 + " z1:" + z1 + " z2:" + z2 + "]";
    }

}
