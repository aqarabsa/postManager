package com.postmanager.controller.exception;

import java.util.UUID;

public class ObjectNotExistException extends RuntimeException {

    public ObjectNotExistException(UUID id) {
        super(String.format("Object with id %s does not exist",id.toString()));
    }

}

