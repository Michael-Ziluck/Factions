package com.ziluck.factions.data.mongodb;

import java.util.logging.Level;
import java.util.logging.LogManager;

import org.jongo.Jongo;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.diagnostics.logging.Loggers;

import com.ziluck.factions.configuration.Config;

/**
 * Stores the main connection to MongoDB.
 *
 * @author Michael Ziluck
 */
public class MongoWrapper
{

    private static MongoWrapper instance;

    private MongoClient client;

    private Jongo jongo;

    /**
     * Constructs a new wrapper for MongoDB.
     */
    @SuppressWarnings("deprecation")
    public MongoWrapper()
    {
        instance = this;

        ServerAddress addr = new ServerAddress(
                Config.DATABASE_HOSTNAME.getValue(),
                Config.DATABASE_PORT.intValue());

        MongoCredential creds = MongoCredential.createCredential(
                Config.DATABASE_USERNAME.getValue(),
                Config.DATABASE_DATABASE.getValue(),
                Config.DATABASE_PASSWORD.getValue().toCharArray());

        // Stop MongoDB from spamming the console.
        LogManager.getLogManager().getLogger(Loggers.getLogger("cluster").getName()).setLevel(Level.WARNING);
        LogManager.getLogManager().getLogger(Loggers.getLogger("connection").getName()).setLevel(Level.WARNING);

        client = new MongoClient(addr, creds, MongoClientOptions.builder()
                                                      .connectTimeout(Config.DATABASE_TIMEOUT.intValue())
                                                      .description("Factions Connection")
                                                      .build());

        DB db = client.getDB(Config.DATABASE_DATABASE.getValue());

        jongo = new Jongo(db);
    }

    /**
     * Returns the active {@link Jongo} instance.
     *
     * @return the active {@link Jongo} instance.
     */
    public Jongo getJongo()
    {
        return jongo;
    }

    /**
     * Returns the active {@link MongoClient} instance.
     *
     * @return the active {@link MongoClient} instance.
     */
    public MongoClient getMongoClient()
    {
        return client;
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
