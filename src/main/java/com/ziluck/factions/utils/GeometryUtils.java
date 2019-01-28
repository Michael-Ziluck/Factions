package com.ziluck.factions.utils;

import static java.lang.Double.max;

import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Rectangle;
import com.google.common.base.Preconditions;

import com.ziluck.factions.spatial.BlockColumn;
import com.ziluck.factions.spatial.BoundedArea;

/**
 * Basic geometric math utilities.
 *
 * @author Michael Ziluck
 */
public class GeometryUtils
{
    /**
     * Get the distance between two rectangles.
     *
     * @param x1 x1 of shape one.
     * @param y1 y1 of shape one.
     * @param x2 x2 of shape one.
     * @param y2 y2 of shape one.
     * @param a1 x1 of shape two.
     * @param b1 y1 of shape two.
     * @param a2 x2 of shape two.
     * @param b2 y2 of shape two.
     *
     * @return the distance between the two rectangles.
     */
    public static double distance(double x1, double y1, double x2, double y2, double a1, double b1, double a2, double b2)
    {
        if (intersects(x1, y1, x2, y2, a1, b1, a2, b2))
        {
            return 0;
        }
        boolean xyMostLeft = x1 < a1;
        double mostLeftX1 = xyMostLeft ? x1 : a1;
        double mostRightX1 = xyMostLeft ? a1 : x1;
        double mostLeftX2 = xyMostLeft ? x2 : a2;
        double xDifference = max(0, mostLeftX1 == mostRightX1 ? 0 : mostRightX1 - mostLeftX2);

        boolean xyMostDown = y1 < b1;
        double mostDownY1 = xyMostDown ? y1 : b1;
        double mostUpY1 = xyMostDown ? b1 : y1;
        double mostDownY2 = xyMostDown ? y2 : b2;

        double yDifference = max(0, mostDownY1 == mostUpY1 ? 0 : mostUpY1 - mostDownY2);

        return Math.sqrt(xDifference * xDifference + yDifference * yDifference);
    }

    /**
     * Check if two rectangles intersect one another.
     *
     * @param x1 x1 of shape one.
     * @param y1 y1 of shape one.
     * @param x2 x2 of shape one.
     * @param y2 y2 of shape one.
     * @param a1 x1 of shape two.
     * @param b1 y1 of shape two.
     * @param a2 x2 of shape two.
     * @param b2 y2 of shape two.
     *
     * @return {@code true} if the rectangles intersect.
     */
    public static boolean intersects(double x1, double y1, double x2, double y2, double a1, double b1, double a2, double b2)
    {
        return x1 <= a2 && a1 <= x2 && y1 <= b2 && b1 <= y2;
    }

    /**
     * Create a new Rectangle with the given coordinates.
     *
     * @param x1 the x1.
     * @param y1 the y1.
     * @param x2 the x2.
     * @param y2 the y2.
     *
     * @return the new Rectangle.
     */
    public static Rectangle create(double x1, double y1, double x2, double y2)
    {
        return Geometries.rectangle(x1, y1, x2, y2);
    }

    /**
     * Get the area between the two points.
     *
     * @param pointOne the first point.
     * @param pointTwo the second point.
     *
     * @return the area.
     */
    public static int getArea(BlockColumn pointOne, BlockColumn pointTwo)
    {
        return (int) getBoundedArea(pointOne, pointTwo).area();
    }

    /**
     * Construct a new BoundedArea with the two given points.
     *
     * @param pointOne the first point.
     * @param pointTwo the second point.
     *
     * @return the new BoundedArea.
     */
    public static BoundedArea getBoundedArea(BlockColumn pointOne, BlockColumn pointTwo)
    {
        Preconditions.checkNotNull(pointOne, "Point One can't be null");
        Preconditions.checkNotNull(pointTwo, "Points Two can't be null");
        return new BoundedArea(pointOne, pointTwo);
    }

}
