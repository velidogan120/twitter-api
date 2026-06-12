package com.twitter.twitterapi.dto.converter;

import com.twitter.twitterapi.dto.CommentDto;
import com.twitter.twitterapi.dto.TweetDto;
import com.twitter.twitterapi.dto.UserDto;
import com.twitter.twitterapi.entity.Comment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentDtoConverter {

    public CommentDto convert(Comment comment) {
        return new CommentDto(comment.getText(),
                new UserDto(comment.getUser().getFirstName(),
                        comment.getUser().getLastName(),
                        comment.getUser().getEmail(),
                        null),
                new TweetDto(comment.getTweet().getText(),null,null,null,null));
    }

    public List<CommentDto> convert(List<Comment> comments) {
        return comments.stream().map(c->convert(c)).toList();
    }
}
