package com.twitter.twitterapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RetweetDto(UserDto user, TweetDto tweet) {
}
