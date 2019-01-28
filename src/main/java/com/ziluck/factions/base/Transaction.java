package com.ziluck.factions.base;

import java.util.Date;
import java.util.UUID;

/**
 * Represents a transaction that is made on the balance of a Faction.
 */
public interface Transaction
{
    /**
     * Returns the amount of the transaction.
     *
     * @return the amount of the transaction.
     */
    public double getAmount();

    /**
     * Returns the id of the {@link User} that made this transaction.
     *
     * @return the id of the {@link User} that made this transaction.
     */
    public UUID getUserId();

    /**
     * Returns the {@link User} that made this transaction.
     *
     * @return the {@link User} that made this transaction.
     */
    public User getUser();


    /**
     * Returns the date of this transaction.
     *
     * @return the date of this transaction.
     */
    public Date getDate();


}
