package com.example.url.shortener.dal.repo;

import com.example.url.shortener.dal.entity.ShortenedUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Optional;

public interface ShortenedUrlRepository extends JpaRepository<ShortenedUrl, Long> {

    @Query(value = "UPDATE shortened_url SET click_count = click_count + 1 WHERE id = :id", nativeQuery = true)
    int incrementClickCount(long id);
    Collection<ShortenedUrl> findAllByUserId(String userId);
    Optional<ShortenedUrl> findByHash(String hash);
    Optional<ShortenedUrl> findByHashAndUserId(String hash, String userId);
}
