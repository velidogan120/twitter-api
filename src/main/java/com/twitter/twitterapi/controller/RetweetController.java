package com.twitter.twitterapi.controller;

import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twitter.twitterapi.dto.RetweetDto;
import com.twitter.twitterapi.dto.request.CreateRetweetRequest;
import com.twitter.twitterapi.service.RetweetService;

@RestController
@RequestMapping("/retweet")
public class RetweetController {
    private final RetweetService retweetService;

    public RetweetController(RetweetService retweetService) {
        this.retweetService = retweetService;
    }

    @PostMapping
    public RetweetDto addRetweet(@Validated @RequestBody CreateRetweetRequest retweet, Authentication authentication) {
        String email = authentication.getName();
        return retweetService.addRetweet(retweet,email);
    }

    @DeleteMapping("/{id}")
    public RetweetDto deleteRetweet(@PathVariable Long id, Authentication authentication) {
        String email = authentication.getName();
        return retweetService.delete(id,email);
    }

}
