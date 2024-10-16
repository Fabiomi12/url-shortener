package com.example.url.shortener.service;

import com.example.url.shortener.config.ApplicationProperties;
import com.example.url.shortener.dal.entity.ShortenedUrl;
import com.example.url.shortener.dal.repo.ShortenedUrlRepository;
import com.example.url.shortener.dto.ShortenedUrlGetDto;
import com.example.url.shortener.dto.ShortenedUrlPostDto;
import com.example.url.shortener.exception.ForbiddenUserActionException;
import com.example.url.shortener.exception.UrlNotFoundException;
import com.example.url.shortener.mapper.ShortenedUrlMapper;
import com.example.url.shortener.util.RequestUtils;
import com.example.url.shortener.util.SimpleBase62;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static com.example.url.shortener.util.Constants.SHORT_URL_PREFIX;

@Service
@RequiredArgsConstructor
public class JpaShortenedUrlService implements ShortenedUrlService {

    private final ShortenedUrlRepository repository;
    private final ShortenedUrlMapper mapper;
    private final ApplicationProperties applicationProperties;

    @Override
    public ShortenedUrlGetDto save(ShortenedUrlPostDto dto) {
        var entity = mapper.fromDto(dto);
        var savedEntity = repository.save(entity);
        savedEntity.setHash(SimpleBase62.encode(entity.getId()));
        repository.save(savedEntity);

        return mapper.toDto(savedEntity);
    }

    @Override
    public Collection<ShortenedUrlGetDto> findAllCurrentUser() {
        var currentUserId = RequestUtils.getCurrentAuthentication().getName();
        return repository.findAllByUserId(currentUserId)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public ShortenedUrlGetDto findByHash(String hash) {
        var entity = findShortenedUrlByHash(hash);
        return mapper.toDto(entity);
    }

    @Override
    public ShortenedUrlGetDto findByShortUrlForCurrentUser(String shortUrl) {
        var currentUserId = RequestUtils.getCurrentAuthentication().getName();
        var hash = shortUrl.replace(applicationProperties.getApplicationUrl() + SHORT_URL_PREFIX, "");
        var entity = findShortenedUrlByHash(hash);
        if (!entity.getUserId().equals(currentUserId))
            throw new ForbiddenUserActionException("User '" + currentUserId + "' has no access to perform this action." );
        return mapper.toDto(entity);
    }

    @Override
    public void incrementClickCount(long id) {
        repository.incrementClickCount(id);
    }

    private ShortenedUrl findShortenedUrlByHash(String hash) {
        return repository.findByHash(hash).orElseThrow(() -> new UrlNotFoundException(hash));
    }
}
