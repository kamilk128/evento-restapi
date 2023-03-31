package com.example.eventorestapi.exceptions;

public class NotExistException extends RuntimeException {
    public NotExistException(String entity, String field) {
        super(entity + " with provided " + field + " does not exist");
    }
}
