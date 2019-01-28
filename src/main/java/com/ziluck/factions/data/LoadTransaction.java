package com.ziluck.factions.data;

import java.util.Date;
import java.util.UUID;

import com.ziluck.factions.Factions;
import com.ziluck.factions.base.Transaction;
import com.ziluck.factions.base.User;
import com.ziluck.factions.configuration.Config;
import com.ziluck.factions.configuration.struct.Optimization;

/**
 * The in-memory representation of a Transaction.
 * <p>
 * To help reduce clutter and duplicate code, Transactions have an additional middle step between the interface and the
 * implementation that loads information to the database.
 * </p>
 *
 * @author Michael Ziluck
 */
public abstract class LoadTransaction implements Transaction
{
    protected User user;

    protected UUID userId;

    protected Date date;

    protected double amount;

    @Override
    public double getAmount()
    {
        return amount;
    }

    @Override
    public User getUser()
    {
        if (Config.OPTIMIZATION.getValue() == Optimization.MEMORY)
        {
            return Factions.getUser(userId);
        }
        else if (user == null)
        {
            user = Factions.getUser(userId);
        }
        return user;
    }

    @Override
    public Date getDate()
    {
        return date;
    }
}
