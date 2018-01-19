package net.dnddev.factions.utils;

import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Rectangle;

import net.dnddev.factions.spatial.BlockColumn;

public class GeometryUtils
{
    public static double distance(float x1, float y1, float x2, float y2, float a1, float b1, float a2, float b2)
    {
        if (intersects(x1, y1, x2, y2, a1, b1, a2, b2))
        {
            return 0;
        }
        boolean xyMostLeft = x1 < a1;
        float mostLeftX1 = xyMostLeft ? x1 : a1;
        float mostRightX1 = xyMostLeft ? a1 : x1;
        float mostLeftX2 = xyMostLeft ? x2 : a2;
        double xDifference = max(0, mostLeftX1 == mostRightX1 ? 0 : mostRightX1 - mostLeftX2);

        boolean xyMostDown = y1 < b1;
        float mostDownY1 = xyMostDown ? y1 : b1;
        float mostUpY1 = xyMostDown ? b1 : y1;
        float mostDownY2 = xyMostDown ? y2 : b2;

        double yDifference = max(0, mostDownY1 == mostUpY1 ? 0 : mostUpY1 - mostDownY2);

        return Math.sqrt(xDifference * xDifference + yDifference * yDifference);
    }

    public static boolean intersects(float x1, float y1, float x2, float y2, float a1, float b1, float a2, float b2)
    {
        return x1 <= a2 && a1 <= x2 && y1 <= b2 && b1 <= y2;
    }

    private static float max(float a, float b)
    {
        if (a < b)
            return b;
        else
            return a;
    }

    public static Rectangle create(double x1, double y1, double x2, double y2)
    {
        return Geometries.rectangle(x1, y1, x2, y2);
    }

    public static Rectangle create(float x1, float y1, float x2, float y2)
    {
        return Geometries.rectangle(x1, y1, x2, y2);
    }

    public static int getArea(BlockColumn pointOne, BlockColumn pointTwo)
    {
        return (int) new BoundedArea(pointOne, pointTwo).area();
    }

    public static BoundedArea getBoundedArea(BlockColumn pointOne, BlockColumn pointTwo)
    {
        return new BoundedArea(pointOne, pointTwo);
    }
    
}
