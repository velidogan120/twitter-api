package com.twitter.twitterapi.controller;

import com.twitter.twitterapi.dto.TweetDto;
import com.twitter.twitterapi.dto.request.CreateTweetRequest;
import com.twitter.twitterapi.dto.request.UpdateTweetRequest;
import com.twitter.twitterapi.service.TweetService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweet")
public class TweetController {
    private final TweetService tweetService;

    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TweetDto save(@RequestBody CreateTweetRequest tweetRequest, Authentication authentication) {
        String email = authentication.getName();
        return tweetService.create(email,tweetRequest);
    }

    @GetMapping
    public List<TweetDto> findByUserId(Authentication authentication) {
        String email = authentication.getName();
        return tweetService.findByUserId(email);
    }

    @GetMapping("/{id}")
    public TweetDto findById(@PathVariable Long id) {
        return tweetService.findById(id);
    }

    @PutMapping("/{id}")
    public TweetDto update(@PathVariable Long id, @RequestBody UpdateTweetRequest tweetRequest, Authentication authentication)  {
        String email = authentication.getName();
        return tweetService.update(id,tweetRequest,email);
    }

    @DeleteMapping("/{id}")
    public TweetDto deleteById(@PathVariable Long id,Authentication authentication) {
        String email = authentication.getName();
        return tweetService.delete(id,email);
    }
}
