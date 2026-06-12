package com.twitter.twitterapi.repository;

import com.twitter.twitterapi.entity.Retweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RetweetRepository extends JpaRepository<Retweet,Long> {
}
