package com.ziluck.factions.data.mongodb;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import com.ziluck.factions.base.Transaction;
import com.ziluck.factions.base.User;
import com.ziluck.factions.data.LoadTransaction;

/**
 * Transaction implementation for saving to MongoDB.
 *
 * @author Michael Ziluck
 */
@JsonIgnoreProperties({ "user", "userId" })
public class MongoTransaction extends LoadTransaction
{
    @JsonProperty(value = "uid")
    protected String uuidString;

    /**
     * An empty constructor. Jongo requires an empty constructor in order to load things
     * nicely.
     */
    public MongoTransaction()
    {
    }

    /**
     * Creates a new {@link Transaction} with the given parameters.
     *
     * @param user   the user that made the transaction.
     * @param amount the amount of the transaction.
     * @param date   the date this transaction was made.
     */
    public MongoTransaction(User user, Date date, double amount)
    {
        Preconditions.checkNotNull(user, "User can't be null.");
        Preconditions.checkNotNull(date, "Location can't be null.");

        this.user = user;
        this.userId = user.getUniqueId();
        this.date = date;
        this.amount = amount;
    }

    @Override
    public UUID getUserId()
    {
        if (userId == null)
        {
            userId = UUID.fromString(uuidString);
        }
        return userId;
    }
}
