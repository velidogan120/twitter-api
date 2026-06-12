package com.twitter.twitterapi.controller;

import com.twitter.twitterapi.dto.CommentDto;
import com.twitter.twitterapi.dto.request.CreateCommentRequest;
import com.twitter.twitterapi.dto.request.UpdateCommentRequest;
import com.twitter.twitterapi.service.CommentService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public CommentDto save(Authentication authentication, @RequestParam Long tweetId, @RequestBody CreateCommentRequest commentRequest) {
        String email = authentication.getName();
        return commentService.create(email,tweetId,commentRequest);
    }

    @PutMapping("/{id}")
    public CommentDto update(@PathVariable Long id, @RequestBody UpdateCommentRequest comment, Authentication authentication) {
        String email = authentication.getName();
        return commentService.update(id,comment,email);
    }

    @DeleteMapping("/{id}")
    public CommentDto delete(@PathVariable Long id,Authentication authentication) {
        String email = authentication.getName();
        return commentService.delete(id,email);
    }
}
