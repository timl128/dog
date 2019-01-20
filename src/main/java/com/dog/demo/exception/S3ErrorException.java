package com.dog.demo.exception;

public class S3ErrorException  extends RuntimeException{

    public S3ErrorException(){
        super("S3 error.");
    }
}
