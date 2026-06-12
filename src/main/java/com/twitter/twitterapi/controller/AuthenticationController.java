package com.twitter.twitterapi.controller;

import com.twitter.twitterapi.dto.UserDto;
import com.twitter.twitterapi.dto.request.CreateUserRequest;
import com.twitter.twitterapi.dto.request.LoginUserRequest;
import com.twitter.twitterapi.service.AuthenticationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public UserDto register(@RequestBody CreateUserRequest user){
        return authenticationService.register(user);
    }

    @PostMapping("/login")
    public UserDto login(@RequestBody LoginUserRequest user){
        return authenticationService.login(user);
    }
}
