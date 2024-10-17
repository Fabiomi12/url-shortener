package com.example.url.shortener.security;

import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.util.Assert;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryAuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    private final ConcurrentHashMap<String, OAuth2AuthorizationRequest> authorizationRequests = new ConcurrentHashMap<>();
    private static final String STATE_PARAMETER = "state";
    private static final String AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        Assert.notNull(request, "request cannot be null");
        String stateParameter = getStateParameter(request);
        if (stateParameter == null) {
            return null;
        }
        return authorizationRequests.get(stateParameter);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request,
                                         HttpServletResponse response) {
        Assert.notNull(request, "request cannot be null");
        Assert.notNull(response, "response cannot be null");
        if (authorizationRequest == null) {
            removeAuthorizationRequest(request, response);
            return;
        }
        String state = authorizationRequest.getState();
        Assert.hasText(state, "authorizationRequest.state cannot be empty");
        authorizationRequests.put(state, authorizationRequest);
        addStateToCookie(request, response, state);
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        Assert.notNull(request, "request cannot be null");
        String stateParameter = getStateParameter(request);
        if (stateParameter == null) {
            return null;
        }
        OAuth2AuthorizationRequest authorizationRequest = authorizationRequests.remove(stateParameter);
        if (authorizationRequest != null && response != null) {
            removeStateFromCookie(response);
        }
        return authorizationRequest;
    }

    private String getStateParameter(HttpServletRequest request) {
        return request.getParameter(STATE_PARAMETER);
    }

    private void addStateToCookie(HttpServletRequest request, HttpServletResponse response, String state) {
        Cookie cookie = new Cookie(AUTHORIZATION_REQUEST_COOKIE_NAME, state);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(180);
        response.addCookie(cookie);
    }

    private void removeStateFromCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(AUTHORIZATION_REQUEST_COOKIE_NAME, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
