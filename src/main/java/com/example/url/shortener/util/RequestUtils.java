package com.example.url.shortener.util;

import com.example.url.shortener.exception.NoAuthenticationException;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public class RequestUtils {
    private RequestUtils() {}

    @NonNull
    public static OAuth2AuthenticationToken getCurrentAuthentication() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null)
            throw new NoAuthenticationException();
        return (OAuth2AuthenticationToken) authentication;
    }
}
