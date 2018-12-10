package net.dnddev.factions.data.nitrite;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import net.dnddev.factions.Factions;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dizitart.no2.Nitrite;

/**
 * Stores the main connection to MongoDB.
 *
 * @author Michael Ziluck
 */
public class NitriteWrapper
{
    private static NitriteWrapper instance;

    private Nitrite db;

    /**
     * Constructs a new wrapper for MongoDB.
     */
    public NitriteWrapper()
    {
        instance = this;

        db = Nitrite.builder()
                    .compressed()
                    .filePath(new File(Factions.getInstance().getDataFolder(), "users.db"))
                    .openOrCreate();

        for (Logger logger : (ArrayList<Logger>) Collections.<Logger>list(LogManager.getCurrentLoggers()))
        {
            if (logger.getName().startsWith("org.dizitart.no2"))
            {
                logger.setLevel(Level.OFF);
            }
        }
    }

    /**
     * Returns the active {@link Nitrite} instance.
     *
     * @return the {@link Nitrite} instance.
     */
    public Nitrite getNitrite()
    {
        return db;
    }

    /**
     * Returns the singleton instance of this NitriteWrapper.
     *
     * @return the singleton instance of this NitriteWrapper.
     */
    public static NitriteWrapper getInstance()
    {
        if (instance == null)
        {
            new NitriteWrapper();
        }
        return instance;
    }

}
