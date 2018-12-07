package net.dnddev.factions.utils;

/**
 * A pair value that represents a value and a number priority associated with it.
 * 
 * @author Michael Ziluck
 * @param <T> the type of the pair.
 */
public class PriorityPair<T> implements Comparable<PriorityPair<T>>
{
    private T value;
    private int priority;

    /**
     * @param value the value.
     * @param priority the priority of the value.
     */
    public PriorityPair(T value, int priority)
    {
        this.value = value;
        this.priority = priority;
    }

    /**
     * Returns the value of the pair.
     * 
     * @return the value of the pair.
     */
    public T getValue()
    {
        return value;
    }

    /**
     * Returns the priority.
     * 
     * @return the priority.
     */
    public int getPriority()
    {
        return priority;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (value == null && obj == null)
        {
            return true;
        }
        else if ((value != null ^ obj != null) || obj.getClass() != value.getClass())
        {
            return false;
        }
        else
        {
            return value.equals(obj);
        }
    }

    @Override
    public int compareTo(PriorityPair<T> o)
    {
        return Integer.compare(getPriority(), o.getPriority());
    }

}