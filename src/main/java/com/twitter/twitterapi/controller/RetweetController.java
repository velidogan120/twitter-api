package com.twitter.twitterapi.controller;

import com.twitter.twitterapi.dto.RetweetDto;
import com.twitter.twitterapi.dto.request.CreateRetweetRequest;
import com.twitter.twitterapi.service.RetweetService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/retweet")
public class RetweetController {
    private final RetweetService retweetService;

    public RetweetController(RetweetService retweetService) {
        this.retweetService = retweetService;
    }

    @PostMapping
    public RetweetDto addRetweet(@RequestBody CreateRetweetRequest retweet, Authentication authentication) {
        String email = authentication.getName();
        return retweetService.addRetweet(retweet,email);
    }

    @DeleteMapping("/{id}")
    public RetweetDto deleteRetweet(@PathVariable Long id, Authentication authentication) {
        String email = authentication.getName();
        return retweetService.delete(id,email);
    }

}
