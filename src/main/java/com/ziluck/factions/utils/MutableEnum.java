package com.ziluck.factions.utils;

/**
 * Designed to wrap enums to force them to behave mutably.
 * 
 * @author Michael Ziluck
 * @param <T> the type this MutableEnum wraps.
 */
public class MutableEnum<T extends Enum<T>>
{

    private Enum<T> value;

    /**
     * Creates a new MutableEnum wrapper for the given value.
     * 
     * @param value the value to wrap.
     */
    public MutableEnum(T value)
    {
        this.value = value;
    }

    /**
     * @return the currently wrapped value.
     */
    public Enum<T> getValue()
    {
        return value;
    }

    /**
     * The value can't be null.
     * 
     * @param value the new wrapped value.
     */
    public void setValue(T value)
    {
        if (value == null)
        {
            throw new NullPointerException("Value can't be null.");
        }
        this.value = value;
    }

    /**
     * @return the class type of the contained Enum.
     */
    public Class<T> getType()
    {
        return value.getDeclaringClass();
    }

}
