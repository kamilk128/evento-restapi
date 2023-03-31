package com.example.eventorestapi.exceptions;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException() {
        super("You don't have permission to modify this content");
    }
}
