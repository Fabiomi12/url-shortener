package com.example.url.shortener.exception;

public class ForbiddenUserActionException extends RuntimeException {
    public ForbiddenUserActionException(String message) {
        super(message);
    }
}
