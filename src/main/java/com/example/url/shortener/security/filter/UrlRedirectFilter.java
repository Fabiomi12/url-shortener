package com.example.url.shortener.security.filter;

import com.example.url.shortener.service.ShortenedUrlService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.example.url.shortener.util.Constants.SHORT_URL_PREFIX;

@RequiredArgsConstructor
public class UrlRedirectFilter extends OncePerRequestFilter {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private final ShortenedUrlService shortenedUrlService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var path = request.getServletPath();
        if (path.startsWith(SHORT_URL_PREFIX)) {
            try {
                String hash = path.substring(3); // Remove prefix
                var entity = shortenedUrlService.findByHash(hash);
                redirectStrategy.sendRedirect(request, response, entity.getOriginalUrl());
                return;
            } catch (Exception ignored) {
                // Ignored, continue with filter chain
            }
        }

        filterChain.doFilter(request, response);
    }
}
