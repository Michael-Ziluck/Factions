package net.dnddev.factions.utils;

/**
 * Designed to wrap objects that are not usually mutable to make them easily changed by other pieces of this system.
 * 
 * @author Michael Ziluck
 * @param <T> the type this MutableObject wraps.
 */
public class MutableEnum<T extends Enum<T>>
{

    private Enum<T> value;

    /**
     * Creates a new MutableObject wrapper for the given value.
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
