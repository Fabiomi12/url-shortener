package com.example.url.shortener.controller;

import com.example.url.shortener.dto.ShortenedUrlGetDto;
import com.example.url.shortener.dto.ShortenedUrlPostDto;
import com.example.url.shortener.service.UrlShortenerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/shortened-urls")
@RequiredArgsConstructor
public class ShortenedUrlController {

    private final UrlShortenerService service;

    @PostMapping
    public ShortenedUrlGetDto save(@RequestBody ShortenedUrlPostDto dto) {
        return service.save(dto);
    }

    @GetMapping
    public Collection<ShortenedUrlGetDto> findAllCurrentUser() {
        return service.findAllCurrentUser();
    }

    @GetMapping("original")
    public ShortenedUrlGetDto findOriginalUrl(@RequestParam String shortUrl) {
        return service.findByShortUrlForCurrentUser(shortUrl);
    }
}
