package com.twitter.twitterapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record TweetDto(String text, UserDto user, Set<LikeDto> likes, List<CommentDto> comments, List<RetweetDto> retweets) {
    public TweetDto {
        likes = likes == null ? Set.of() : Set.copyOf(likes);
        comments = comments == null ? List.of() : List.copyOf(comments);
        retweets = retweets == null ? List.of() : List.copyOf(retweets);
    }
}
