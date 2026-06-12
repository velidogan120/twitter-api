package com.twitter.twitterapi.controller;

import com.twitter.twitterapi.dto.LikeDto;
import com.twitter.twitterapi.dto.request.CreateLikeRequest;
import com.twitter.twitterapi.service.LikeService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping
    public LikeDto addLike(@RequestBody CreateLikeRequest like,Authentication authentication) {
        String email = authentication.getName();
        return likeService.addLike(like,email);
    }

    @DeleteMapping("/{tweetId}")
    public LikeDto deleteLike(@PathVariable Long tweetId, Authentication authentication) {
        String email = authentication.getName();
        return likeService.delete(tweetId,email);
    }
}
