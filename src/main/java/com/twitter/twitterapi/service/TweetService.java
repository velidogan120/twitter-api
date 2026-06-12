package com.twitter.twitterapi.service;

import com.twitter.twitterapi.dto.TweetDto;
import com.twitter.twitterapi.dto.converter.TweetDtoConverter;
import com.twitter.twitterapi.dto.request.CreateTweetRequest;
import com.twitter.twitterapi.dto.request.UpdateTweetRequest;
import com.twitter.twitterapi.entity.Tweet;
import com.twitter.twitterapi.entity.User;
import com.twitter.twitterapi.exception.ApiException;
import com.twitter.twitterapi.repository.TweetRepository;
import com.twitter.twitterapi.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TweetService {
    private final TweetRepository tweetRepository;
    private final TweetDtoConverter tweetDtoConverter;
    private final UserRepository userRepository;

    public TweetService(TweetRepository tweetRepository,
                        TweetDtoConverter tweetDtoConverter,
                        UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.tweetDtoConverter = tweetDtoConverter;
        this.userRepository = userRepository;
    }

    public TweetDto create(String email,CreateTweetRequest tweetRequest) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new ApiException("User NOT FOUND", HttpStatus.NOT_FOUND));

        Tweet tweet = new Tweet();
        tweet.setUser(user);
        tweet.setText(tweetRequest.getText());
        tweet.setLikes(Set.of());
        tweet.setComments(List.of());
        tweet.setRetweets(List.of());

        return tweetDtoConverter.convert(tweetRepository.save(tweet));
    }

    public TweetDto findById(Long id) {
        Tweet tweet = tweetRepository.findById(id).orElseThrow(() -> new ApiException("Tweet NOT FOUND", HttpStatus.NOT_FOUND));
        return tweetDtoConverter.convert(tweet);
    }

    public List<TweetDto> findByUserId(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("Tweet NOT FOUND", HttpStatus.NOT_FOUND));
        return tweetDtoConverter.convert(user.getTweets());
    }

    public TweetDto update(Long id, UpdateTweetRequest tweetRequest, String email) {
        Tweet tweetDb = findByTweetId(id);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ApiException("User NOT FOUND", HttpStatus.NOT_FOUND));

        if (tweetDb.getUser().getId().equals(user.getId())) {
            Tweet newTweet = new Tweet();
            newTweet.setId(tweetDb.getId());
            newTweet.setText(tweetRequest.getText());
            newTweet.setUser(tweetDb.getUser());
            newTweet.setLikes(tweetDb.getLikes());
            newTweet.setComments(tweetDb.getComments());
            newTweet.setRetweets(tweetDb.getRetweets());
            return tweetDtoConverter.convert(tweetRepository.save(newTweet));
        } else {
            throw new ApiException("You cant change the tweet", HttpStatus.BAD_REQUEST);
        }
    }

    public TweetDto delete(Long id,String email) {
        Tweet tweet = findByTweetId(id);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ApiException("User NOT FOUND", HttpStatus.NOT_FOUND));
        if(tweet.getUser().getId().equals(user.getId())) {
            tweetRepository.delete(tweet);
            return tweetDtoConverter.convert(tweet);
        }else {
            throw new ApiException("You cant delete tweet", HttpStatus.BAD_REQUEST);
        }
    }

    public Tweet findByTweetId(Long tweetId) {
        return tweetRepository.findById(tweetId).orElseThrow(() -> new ApiException("Tweet NOT FOUND", HttpStatus.NOT_FOUND));
    }
}
