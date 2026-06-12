package com.twitter.twitterapi.repository;

import com.twitter.twitterapi.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query("SELECT l FROM Like l WHERE l.user.id = :userId AND l.tweet.id = :tweet")
    Optional<Like> findUserIdAndTweetId(@Param("userId") Long userId, @Param("tweet") Long tweetId);
}
