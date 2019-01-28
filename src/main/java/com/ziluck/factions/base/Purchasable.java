package com.ziluck.factions.base;

/**
 * There are several things that are able to be purchased within Factions. In order to allow things to abstracted
 * properly, this interface will applied to anything that players are able to purchase.
 * 
 * @author Michael Ziluck
 */
public interface Purchasable
{

    /**
     * @return {@code true} if this Ownable has a cost associated with it.
     */
    public boolean hasCost();

    /**
     * @return the cost of this Ownable.
     */
    public double getCost();

}
