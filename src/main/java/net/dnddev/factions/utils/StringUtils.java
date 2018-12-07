package net.dnddev.factions.utils;

/**
 * A group of utilities designed for managing Strings and String arrays.
 *
 * @author Michael Ziluck
 */
public class StringUtils
{
    /**
     * Combine several Strings into one from the start to the end range.
     *
     * @param strings all the Strings to compile.
     * @param start   the beginning index.
     * @param end     the ending index.
     *
     * @return the combined String.
     */
    public static String implode(String[] strings, int start, int end)
    {
        StringBuilder sb = new StringBuilder();

        for (int i = start; i < end; i++)
        {
            sb.append(strings[i]).append(" ");
        }

        return sb.toString().trim();
    }

    /**
     * Constructs a new String[] of a size one larger than the given array. Then adds the other given string to the
     * array and returns the result.
     *
     * @param array the base array.
     * @param add   the new string to add.
     *
     * @return the result of adding the string to the array.
     */
    public static String[] add(String[] array, String add)
    {
        String[] values = new String[array.length + 1];
        System.arraycopy(array, 0, values, 0, array.length);
        values[array.length] = add;
        return values;
    }

    /**
     * Combine all the Strings in the array to one with spaces between them
     *
     * @param strings the Strings to combine.
     *
     * @return the compiled String from the array.
     */
    public static String compile(String[] strings)
    {
        return implode(strings, 0, strings.length);
    }
}
