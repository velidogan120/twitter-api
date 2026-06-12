package com.twitter.twitterapi.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twitter.twitterapi.dto.UserDto;
import com.twitter.twitterapi.dto.request.CreateUserRequest;
import com.twitter.twitterapi.dto.request.LoginUserRequest;
import com.twitter.twitterapi.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public UserDto register(@Validated @RequestBody CreateUserRequest user){
        return authenticationService.register(user);
    }

    @PostMapping("/login")
    public UserDto login(@Validated @RequestBody LoginUserRequest user){
        return authenticationService.login(user);
    }
}
