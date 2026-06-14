package com.twitter.twitterapi.repository;

import com.twitter.twitterapi.entity.Like;
import com.twitter.twitterapi.entity.Tweet;
import com.twitter.twitterapi.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class LikeRepositoryTest {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;

    @Autowired
    LikeRepositoryTest(LikeRepository likeRepository,
                       TweetRepository tweetRepository,
                       UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.tweetRepository = tweetRepository;
    }

    @DisplayName("Find Like entity userId and tweetId")
    @Test
    void findUserIdAndTweetId() {
        User newUser = new User();
        newUser.setFirstName("Veli");
        newUser.setLastName("Aksu");
        newUser.setEmail("veliaksu120@gmail.com");
        newUser.setPassword("123456");
        newUser = userRepository.save(newUser);

        Tweet tweet = new Tweet();
        tweet.setText("hello");
        tweet.setUser(newUser);
        tweet = tweetRepository.save(tweet);

        Like like = new Like();
        like.setTweet(tweet);
        like.setUser(newUser);

        likeRepository.save(like);

        Optional<Like> dbLike = likeRepository.findUserIdAndTweetId(newUser.getId(), tweet.getId());

        assertNotNull(dbLike.get());
    }
}