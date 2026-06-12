package com.twitter.twitterapi.repository;

import com.twitter.twitterapi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
