package net.dnddev.factions.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * A utility for managing and viewing different types of Collections.
 * 
 * @author Michael Ziluck
 */
public class CollectionUtils
{

    /**
     * Retrieves the last value of the given list. If the list is null or it has no elements, this will always return
     * null. If the List is also a Deque, this will return the last element using {@link Deque#peekLast()}.
     * 
     * @param list the list to get the last value of.
     * @return the last value.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getLast(List<T> list)
    {
        if (list == null || list.size() == 0)
        {
            return null;
        }
        if (list instanceof Deque)
        {
            return ((Deque<T>) list).peekLast();
        }
        return list.get(list.size() - 1);
    }

    /**
     * Safely checks if the given collection is immutable. If the collection is mutable, the data will not be affected
     * unless the collection in question keeps track of total number of operations. The test is done by calling
     * {@link Collection#removeIf(java.util.function.Predicate)} with the predicate of {@code 1 == 2}.
     * 
     * @param values the collection to check.
     * @return {@code true} if the collection is immutable.
     */
    public static boolean isImmutable(Collection<?> values)
    {
        try
        {
            values.removeIf(x -> 1 == 2);
            return true;
        }
        catch (UnsupportedOperationException ex)
        {
            return false;
        }
    }

    /**
     * Converts the given values into their string counterpart. This is done by calling {@link Object#toString()} on
     * every object. More specific use cases like {@link org.bukkit.entity.Player#getName()} etc are not compatible.
     * 
     * @param values the values to convert.
     * @return the generated list of Strings.
     */
    public static <T> List<String> getStringList(Collection<T> values)
    {
        if (values == null || values.size() == 0)
        {
            return Arrays.asList();
        }
        List<String> list = new LinkedList<>();
        for (Object o : values)
        {
            if (o != null)
            {
                list.add(o.toString());
            }
            else
            {
                list.add(null);
            }
        }
        return list;
    }

    /**
     * Searches through the given values for the first non-null value.
     * 
     * @param values the values to find.
     * @return the first non-null value.
     */
    @SafeVarargs
    public static <T> T firstNonNull(T... values)
    {
        for (T value : values)
        {
            if (value != null)
            {
                return value;
            }
        }
        return null;
    }

}