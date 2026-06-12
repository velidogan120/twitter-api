package com.twitter.twitterapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.twitter.twitterapi.dto.TweetDto;
import com.twitter.twitterapi.dto.request.CreateTweetRequest;
import com.twitter.twitterapi.dto.request.UpdateTweetRequest;
import com.twitter.twitterapi.service.TweetService;

@RestController
@RequestMapping("/tweet")
public class TweetController {
    private final TweetService tweetService;

    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TweetDto save(@Validated @RequestBody CreateTweetRequest tweetRequest, Authentication authentication) {
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
    public TweetDto update(@PathVariable Long id, @Validated @RequestBody UpdateTweetRequest tweetRequest, Authentication authentication)  {
        String email = authentication.getName();
        return tweetService.update(id,tweetRequest,email);
    }

    @DeleteMapping("/{id}")
    public TweetDto deleteById(@PathVariable Long id,Authentication authentication) {
        String email = authentication.getName();
        return tweetService.delete(id,email);
    }
}
