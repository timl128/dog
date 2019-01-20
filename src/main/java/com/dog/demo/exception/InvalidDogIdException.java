package com.dog.demo.exception;

public class InvalidDogIdException extends RuntimeException {

    public InvalidDogIdException(long dogId)
    {
        super(String.format("Dog id %s not found.",dogId));
    }
}
