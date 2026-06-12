package com.twitter.twitterapi.dto.converter;

import com.twitter.twitterapi.dto.*;
import com.twitter.twitterapi.entity.Retweet;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RetweetDtoConverter {

    public RetweetDto convert(Retweet retweet){
        return new RetweetDto(new UserDto(retweet.getUser().getFirstName(),
                retweet.getUser().getLastName(),
                retweet.getUser().getEmail(),
                null),
                new TweetDto(retweet.getTweet().getText(),
                        null,
                        retweet.getTweet().getLikes().stream().map(l-> new LikeDto(
                                        new UserDto(l.getUser().getFirstName(),
                                                l.getUser().getLastName(),
                                                l.getUser().getEmail(),
                                                null),
                                null
                                        )
                                ).collect(Collectors.toSet()),
                        retweet.getTweet().getComments().stream().map(c -> new CommentDto(c.getText(),
                                new UserDto(c.getUser().getFirstName(),c.getUser().getLastName(),c.getUser().getEmail(),null),null)).toList(),
                        null
                )
        );
    }

    public List<RetweetDto> convert(List<Retweet> retweets){
        return retweets.stream().map(r -> convert(r)).toList();
    }
}
