package com.example.url.shortener.util;

import com.example.url.shortener.exception.NoAuthenticationException;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class RequestUtils {
    private RequestUtils() {}

    @NonNull
    public static JwtAuthenticationToken getCurrentAuthentication() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null)
            throw new NoAuthenticationException();
        return (JwtAuthenticationToken) authentication;
    }
}
