package com.twitter.twitterapi.service;

import com.twitter.twitterapi.dto.CommentDto;
import com.twitter.twitterapi.dto.TweetDto;
import com.twitter.twitterapi.dto.converter.CommentDtoConverter;
import com.twitter.twitterapi.dto.request.CreateCommentRequest;
import com.twitter.twitterapi.dto.request.UpdateCommentRequest;
import com.twitter.twitterapi.entity.Comment;
import com.twitter.twitterapi.entity.Tweet;
import com.twitter.twitterapi.entity.User;
import com.twitter.twitterapi.exception.ApiException;
import com.twitter.twitterapi.repository.CommentRepository;
import com.twitter.twitterapi.repository.TweetRepository;
import com.twitter.twitterapi.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;
    private final CommentDtoConverter commentDtoConverter;

    public CommentService(CommentRepository commentRepository,
                          UserRepository userRepository,
                          TweetRepository tweetRepository,
                          CommentDtoConverter commentDtoConverter) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.tweetRepository = tweetRepository;
        this.commentDtoConverter = commentDtoConverter;
    }

    public CommentDto create(@RequestHeader String email,@RequestHeader Long tweetId,@RequestBody CreateCommentRequest commentRequest) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new ApiException("User NOT FOUND", HttpStatus.NOT_FOUND));
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(()-> new ApiException("Tweet NOT FOUND", HttpStatus.NOT_FOUND));
        Comment comment = new Comment();
        comment.setText(commentRequest.getText());
        comment.setTweet(tweet);
        comment.setUser(user);

        return commentDtoConverter.convert(commentRepository.save(comment));
    }

    public CommentDto update(Long id, UpdateCommentRequest comment, String email){
        Comment commentDb = commentRepository.findById(id).orElseThrow(()-> new ApiException("Comment NOT FOUND", HttpStatus.NOT_FOUND));
        User user = userRepository.findByEmail(email).orElseThrow(()-> new ApiException("User NOT FOUND", HttpStatus.NOT_FOUND));

        if(commentDb.getUser().getId().equals(user.getId()) && commentDb.getId().equals(id)){
            Comment newComment = new Comment();
            newComment.setId(commentDb.getId());
            newComment.setText(comment.getText());
            newComment.setUser(user);
            newComment.setTweet(commentDb.getTweet());

            return commentDtoConverter.convert(commentRepository.save(newComment));
        }else {
            throw new ApiException("You cant update this comment", HttpStatus.BAD_REQUEST);
        }
    }

    public CommentDto delete(Long id,String email){
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ApiException("Comment NOT FOUND",HttpStatus.NOT_FOUND));
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ApiException("User NOT FOUND", HttpStatus.NOT_FOUND));
        if(comment.getUser().getId().equals(user.getId())) {
            commentRepository.delete(comment);
            return commentDtoConverter.convert(comment);
        } else {
            throw new ApiException("You cant delete comment", HttpStatus.BAD_REQUEST);
        }

    }
}