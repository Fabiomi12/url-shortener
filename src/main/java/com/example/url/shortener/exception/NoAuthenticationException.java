package com.example.url.shortener.exception;

public class NoAuthenticationException extends RuntimeException {
    public NoAuthenticationException() {
        super("No authentication provided");
    }
}
