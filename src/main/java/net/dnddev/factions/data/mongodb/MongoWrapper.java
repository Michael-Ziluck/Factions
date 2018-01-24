package net.dnddev.factions.data.mongodb;

import org.jongo.Jongo;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import net.dnddev.factions.configuration.Config;

/**
 * Stores the main connection to MongoDB.
 * 
 * @author Michael Ziluck
 */
public class MongoWrapper
{

    private static MongoWrapper instance;

    private ServerAddress addr;
    private MongoCredential creds;
    private MongoClient mc;
    private DB db;
    private Jongo jongo;

    /**
     * Constructs a new wrapper for MongoDB.
     */
    @SuppressWarnings("deprecation")
    public MongoWrapper()
    {
        instance = this;

        addr = new ServerAddress(Config.DATABASE_HOSTNAME.getValue(), Config.DATABASE_PORT.intValue());

        creds = MongoCredential.createCredential(Config.DATABASE_USERNAME.getValue(), Config.DATABASE_DATABASE.getValue(), Config.DATABASE_PASSWORD.getValue().toCharArray());

        mc = new MongoClient(addr, creds, MongoClientOptions.builder().description("Factions MongoDB Connection").build());

        db = mc.getDB(Config.DATABASE_DATABASE.getValue());

        jongo = new Jongo(db);
    }

    /**
     * Returns the active Jongo instance.
     * 
     * @return the Jongo instance.
     */
    public Jongo getJongo()
    {
        return jongo;
    }

    /**
     * Returns the singleton instance of this MongoWrapper.
     * 
     * @return the singleton instance of this MongoWrapper.
     */
    public static MongoWrapper getInstance()
    {
        if (instance == null)
        {
            new MongoWrapper();
        }
        return instance;
    }

}
