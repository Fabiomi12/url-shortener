package com.example.url.shortener.service;

import com.example.url.shortener.dto.ShortenedUrlGetDto;
import com.example.url.shortener.dto.ShortenedUrlPostDto;

import java.util.Collection;

public interface ShortenedUrlService {
    ShortenedUrlGetDto save(ShortenedUrlPostDto dto);
    Collection<ShortenedUrlGetDto> findAllCurrentUser();
    ShortenedUrlGetDto findByHash(String hash);
    ShortenedUrlGetDto findByShortUrlForCurrentUser(String shortUrl);
    void incrementClickCount(long id);
}
