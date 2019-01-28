package com.ziluck.factions.economy;

import java.util.UUID;

/**
 * An economy hook in for Factions to use.
 * 
 * @author Michael Ziluck
 */
public interface Economy
{

    /**
     * Gets the balance of the given User.
     * 
     * @param uuid the UUID of the User.
     * @return the balance of the User.
     */
    public double getBalance(UUID uuid);

    /**
     * Deposit money into the given User's account.
     * 
     * @param uuid the UUID of the User.
     * @param amount the amount to deposit.
     * @return the new balance.
     */
    public double deposit(UUID uuid, double amount);

    /**
     * Withdraw money from the given User's account.
     * 
     * @param uuid the UUID of the User.
     * @param amount the amount to withdraw.
     * @return the new balance.
     */
    public double withdraw(UUID uuid, double amount);

    /**
     * Checks if there is an account connected to the UUID of the given User.
     * 
     * @param uuid the UUID of the User to check.
     * @return {@code true} if the User has an account.
     */
    public boolean exists(UUID uuid);

    /**
     * Creates a new account for the given user. Will return whether or not the attempt to create a new account was
     * successful or not. If the User already had a account, this will still return false.
     * 
     * @param uuid the UUID of the User to create.
     * @return {@code true} if the account was successfully created.
     */
    public boolean create(UUID uuid);

}
