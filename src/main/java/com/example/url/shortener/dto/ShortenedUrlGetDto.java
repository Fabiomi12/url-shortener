package com.example.url.shortener.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ShortenedUrlGetDto {
    private String shortUrl;
    private String originalUrl;
    private Instant expiresAt;
    private Long clickCount;
}
