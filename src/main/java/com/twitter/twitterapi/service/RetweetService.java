package com.twitter.twitterapi.service;

import com.twitter.twitterapi.dto.RetweetDto;
import com.twitter.twitterapi.dto.converter.RetweetDtoConverter;
import com.twitter.twitterapi.dto.request.CreateRetweetRequest;
import com.twitter.twitterapi.entity.Retweet;
import com.twitter.twitterapi.entity.Tweet;
import com.twitter.twitterapi.entity.User;
import com.twitter.twitterapi.exception.ApiException;
import com.twitter.twitterapi.repository.RetweetRepository;
import com.twitter.twitterapi.repository.TweetRepository;
import com.twitter.twitterapi.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class RetweetService {

    private final RetweetRepository retweetRepository;
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;
    private final RetweetDtoConverter retweetDtoConverter;

    public RetweetService(RetweetRepository retweetRepository,
                             UserRepository userRepository,
                             TweetRepository tweetRepository,
                          RetweetDtoConverter retweetDtoConverter) {
        this.retweetRepository = retweetRepository;
        this.userRepository = userRepository;
        this.tweetRepository = tweetRepository;
        this.retweetDtoConverter = retweetDtoConverter;
    }


    public RetweetDto addRetweet(CreateRetweetRequest retweet,String email) {
        Tweet tweet = tweetRepository.findById(retweet.getTweetId()).orElseThrow(() -> new ApiException("Tweet NOT FOUND", HttpStatus.NOT_FOUND));
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ApiException("User NOT FOUND", HttpStatus.NOT_FOUND));

        Retweet newRetweet = new Retweet();
        newRetweet.setTweet(tweet);
        newRetweet.setUser(user);

        return retweetDtoConverter.convert(retweetRepository.save(newRetweet));
    }

    public RetweetDto delete(Long id,String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ApiException("User NOT FOUND", HttpStatus.NOT_FOUND));
        Retweet retweet = retweetRepository.findById(id).orElseThrow(() -> new ApiException("Like NOT FOUND", HttpStatus.NOT_FOUND));
        if (retweet.getUser().getId().equals(user.getId())) {
            retweetRepository.delete(retweet);
            return retweetDtoConverter.convert(retweet);
        }else{
            throw new ApiException("You didnt retweet this tweet", HttpStatus.BAD_REQUEST);
        }
    }
}
