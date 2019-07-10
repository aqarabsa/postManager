package com.postmanager.controller.exception;

public class UnauthorizedAccessException extends RuntimeException {

    public UnauthorizedAccessException() {
        super("Unauthorized access to the specified resource");
    }

}

