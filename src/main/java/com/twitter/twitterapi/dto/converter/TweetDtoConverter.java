package com.twitter.twitterapi.dto.converter;

import com.twitter.twitterapi.dto.*;
import com.twitter.twitterapi.entity.Like;
import com.twitter.twitterapi.entity.Tweet;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TweetDtoConverter {

    public TweetDto convert(Tweet tweet) {
        UserDto userDto = new UserDto(tweet.getUser().getFirstName(),
                tweet.getUser().getLastName(),
                tweet.getUser().getEmail(),
                null
        );

        Set<Like> likes = new HashSet<>(tweet.getLikes());

        Set<LikeDto> likeDtos = likes.stream()
                .map(l->new LikeDto(new UserDto(
                        l.getUser().getFirstName(),
                        l.getUser().getLastName(),
                        l.getUser().getEmail(),
                        null
                ),null))
                .collect(Collectors.toSet());

        List<CommentDto> commentDtos = tweet.getComments().stream()
                .map(c -> new CommentDto(c.getText(),
                            new UserDto(c.getUser().getFirstName(),
                                c.getUser().getLastName(),
                                c.getUser().getEmail(),
                                null
                            ),null
                        )
                ).toList();

        List<RetweetDto> retweetDtos = tweet.getRetweets().stream()
                .map(rt->new RetweetDto(new UserDto(rt.getUser().getFirstName(),
                        rt.getUser().getLastName(),
                        rt.getUser().getEmail(),
                        null),
                null)).toList();

        return new TweetDto(tweet.getText(),
                userDto,
                likeDtos,
                commentDtos,
                retweetDtos
        );
    }

    public List<TweetDto> convert(List<Tweet> tweets) {
        return tweets.stream().map(tweet -> convert(tweet)).toList();
    }
}