package net.dnddev.factions.api.commands;

/**
 * Represents an element that is tracked by the command system. In order to allow commands to control the load order of
 * commands, we need to add the functionality to track the priority with additional default values.
 * <p>
 * The priority system fires the lowest element number first. The argument parser fires at a priority level
 * {@link #PARSER_LEVEL}. If an argument validator fires at priority less than or equal to 50, it will throw an
 * exception.
 *
 * @author Michael Ziluck
 */
public interface CommandElement
{

    /**
     * The priority level used by the parser.
     */
    public static final int PARSER_LEVEL = 50;

    /**
     * Returns the type of the element.
     *
     * @return the type of the element.
     */
    CommandElementType getType();

    /**
     * @return the priority of the element.
     */
    int getPriority();

    /**
     * An enum to properly identity the type of the command element.
     *
     * @author Michael Ziluck
     */
    enum CommandElementType
    {
        /**
         * An argument validator.
         */
        ARGUMENT_VALIDATOR,
        /**
         * A sender validator.
         */
        SENDER_VALIDATOR
    }

}
