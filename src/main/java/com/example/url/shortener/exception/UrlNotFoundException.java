package com.example.url.shortener.exception;

public class UrlNotFoundException extends RuntimeException {
    public UrlNotFoundException(String hash) {
        super("No URL found for hash: " + hash);
    }
}
