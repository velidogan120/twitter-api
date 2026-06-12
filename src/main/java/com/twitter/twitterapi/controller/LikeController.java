package com.twitter.twitterapi.controller;

import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twitter.twitterapi.dto.LikeDto;
import com.twitter.twitterapi.dto.request.CreateLikeRequest;
import com.twitter.twitterapi.service.LikeService;

@RestController
@RequestMapping("/like")
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping
    public LikeDto addLike(@Validated @RequestBody CreateLikeRequest like,Authentication authentication) {
        String email = authentication.getName();
        return likeService.addLike(like,email);
    }

    @DeleteMapping("/{tweetId}")
    public LikeDto deleteLike(@PathVariable Long tweetId, Authentication authentication) {
        String email = authentication.getName();
        return likeService.delete(tweetId,email);
    }
}
