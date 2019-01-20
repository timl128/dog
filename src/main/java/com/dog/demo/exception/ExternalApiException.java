package com.dog.demo.exception;

public class ExternalApiException extends RuntimeException {

    public ExternalApiException(){
        super("Service error from the external api.");
    }
}
