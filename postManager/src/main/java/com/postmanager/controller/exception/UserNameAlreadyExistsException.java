package com.postmanager.controller.exception;

public class UserNameAlreadyExistsException extends RuntimeException {

    public UserNameAlreadyExistsException() {
        super("Username already exists, please try again with different username");

    }
}