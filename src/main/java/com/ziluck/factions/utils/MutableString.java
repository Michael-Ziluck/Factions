package com.ziluck.factions.utils;

/**
 * Designed to wrap Strings to force them to behave mutably.
 * 
 * @author Michael Ziluck
 */
public class MutableString
{

    private String value;

    /**
     * Creates a new MutableString wrapper for the given value.
     * 
     * @param value the value to wrap.
     */
    public MutableString(String value)
    {
        this.value = value;
    }

    /**
     * @return the currently wrapped value.
     */
    public String getValue()
    {
        return value;
    }

    /**
     * The value can't be null.
     * 
     * @param value the new wrapped value.
     */
    public void setValue(String value)
    {
        if (value == null)
        {
            throw new NullPointerException("Value can't be null.");
        }
        this.value = value;
    }

}
