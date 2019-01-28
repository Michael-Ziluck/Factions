package com.ziluck.factions.exceptions;

public final class MismatchStorageException extends Exception
{
    private String message;


    public MismatchStorageException()
    {
    }

    public MismatchStorageException(String message)
    {
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

}
