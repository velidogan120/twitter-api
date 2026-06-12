package com.twitter.twitterapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record LikeDto(UserDto user, TweetDto tweet) {
}
