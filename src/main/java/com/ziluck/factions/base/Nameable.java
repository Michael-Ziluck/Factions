package com.ziluck.factions.base;

/**
 * Represents an entity that can be named.
 *
 * @author Michael Ziluck
 */
public interface Nameable
{

    /**
     * Gets the human-readable name of this entity. Typically, this should not be treated
     * as an immutable descriptor as the name of the entity can be changed.
     *
     * @return the entity's name.
     */
    String getName();

    /**
     * Sets the name of the entity.
     *
     * @param name the new name of the entity.
     */
    void setName(String name);

    /**
     * Gets the stub of this entity. Typically, this will be the lower-case version of the
     * name. However, that is not always the case so documentation should be read and written
     * on a per-case basis.
     *
     * @return the entity's stub.
     */
    String getStub();

}
