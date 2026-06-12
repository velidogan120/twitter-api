package com.twitter.twitterapi.repository;

import com.twitter.twitterapi.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
    @Query("SELECT t FROM Tweet t WHERE t.user = :userId")
    Optional<List<Tweet>> findByUserId(@Param("userId") Long userId);
}
