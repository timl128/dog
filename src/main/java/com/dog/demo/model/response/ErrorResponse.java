package com.dog.demo.model.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class ErrorResponse {

    private String timestamp;

    private String error;
    private String exception;

    private ErrorResponse() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        timestamp = LocalDateTime.now().format(formatter);
    }



    public ErrorResponse(Throwable ex) {
        this();
        this.error = ex.getMessage();
        this.exception = ex.getClass().getSimpleName();
    }
}
