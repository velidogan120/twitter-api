package com.twitter.twitterapi.controller;

import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.twitter.twitterapi.dto.CommentDto;
import com.twitter.twitterapi.dto.request.CreateCommentRequest;
import com.twitter.twitterapi.dto.request.UpdateCommentRequest;
import com.twitter.twitterapi.service.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public CommentDto save(Authentication authentication, @RequestParam Long tweetId, @Validated @RequestBody CreateCommentRequest commentRequest) {
        String email = authentication.getName();
        return commentService.create(email,tweetId,commentRequest);
    }

    @PutMapping("/{id}")
    public CommentDto update(@PathVariable Long id, @Validated @RequestBody UpdateCommentRequest comment, Authentication authentication) {
        String email = authentication.getName();
        return commentService.update(id,comment,email);
    }

    @DeleteMapping("/{id}")
    public CommentDto delete(@PathVariable Long id,Authentication authentication) {
        String email = authentication.getName();
        return commentService.delete(id,email);
    }
}
