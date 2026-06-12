package com.twitter.twitterapi.service;

import com.twitter.twitterapi.dto.LikeDto;
import com.twitter.twitterapi.dto.converter.LikeDtoConverter;
import com.twitter.twitterapi.dto.request.CreateLikeRequest;
import com.twitter.twitterapi.entity.Like;
import com.twitter.twitterapi.entity.Tweet;
import com.twitter.twitterapi.entity.User;
import com.twitter.twitterapi.exception.ApiException;
import com.twitter.twitterapi.repository.LikeRepository;
import com.twitter.twitterapi.repository.TweetRepository;
import com.twitter.twitterapi.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;
    private final LikeDtoConverter likeDtoConverter;

    public LikeService(LikeRepository likeRepository,
                       UserRepository userRepository,
                       TweetRepository tweetRepository,
                       LikeDtoConverter likeDtoConverter) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.tweetRepository = tweetRepository;
        this.likeDtoConverter = likeDtoConverter;
    }

    public LikeDto addLike(CreateLikeRequest like,String email) {
        Tweet tweet = tweetRepository.findById(like.getTweetId()).orElseThrow(() -> new ApiException("Tweet NOT FOUND", HttpStatus.NOT_FOUND));
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ApiException("User NOT FOUND", HttpStatus.NOT_FOUND));

        boolean alreadyLiked = user.getLikes()
                .stream()
                .anyMatch(l -> l.getTweet().getId().equals(tweet.getId()));

        if(alreadyLiked) {
            throw new ApiException("You already like this tweet", HttpStatus.BAD_REQUEST);
        }else{
            Like newLike = new Like();
            newLike.setTweet(tweet);
            newLike.setUser(user);
            return likeDtoConverter.convert(likeRepository.save(newLike));
        }
    }

    public LikeDto delete(Long tweetId,String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ApiException("User NOT FOUND", HttpStatus.NOT_FOUND));
        Optional<Like> like = likeRepository.findUserIdAndTweetId(user.getId(), tweetId);

        if (like.isPresent()) {
            System.out.println(like.get().getId());
            likeRepository.delete(like.get());
            return likeDtoConverter.convert(like.get());
        }else{
            throw new ApiException("You cant delete this like", HttpStatus.BAD_REQUEST);
        }
    }
}
