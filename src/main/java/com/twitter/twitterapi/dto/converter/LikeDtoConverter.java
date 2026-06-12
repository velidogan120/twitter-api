package com.twitter.twitterapi.dto.converter;

import com.twitter.twitterapi.dto.LikeDto;
import com.twitter.twitterapi.dto.TweetDto;
import com.twitter.twitterapi.dto.UserDto;
import com.twitter.twitterapi.entity.Like;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class LikeDtoConverter {

    public LikeDto convert(Like like) {
        return new LikeDto(new UserDto(like.getUser().getFirstName(),
                like.getUser().getLastName(),
                like.getUser().getEmail(),
                null),
                new TweetDto(
                      like.getTweet().getText(),null,
                        null,null,null

        ));
    }

    public Set<LikeDto> convert(Set<Like> likes) {
        return likes.stream().map(l -> convert(l)).collect(Collectors.toSet());
    }
}
