package com.twitter.twitterapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CommentDto(String text, UserDto user, TweetDto tweet) {
}
