package com.example.eventorestapi.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String field) {
        super(field + " already taken");
    }
}
