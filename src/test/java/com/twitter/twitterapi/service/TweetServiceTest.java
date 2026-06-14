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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class TweetServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TweetRepository tweetRepository;

    @Mock
    private TweetDtoConverter tweetDtoConverter;

    @InjectMocks
    private TweetService tweetService;

    @DisplayName("Tweet created")
    @Test
    void create() {
        String email = "veliaksu120@gmail.com";

        CreateTweetRequest request = new CreateTweetRequest();

        User newUser = new User();
        Tweet tweet = new Tweet();
        TweetDto dto = new TweetDto(null,null, null, null, null);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(newUser));
        when(tweetRepository.save(any(Tweet.class))).thenReturn(tweet);
        when(tweetDtoConverter.convert(any(Tweet.class))).thenReturn(dto);

        TweetDto result = tweetService.create(email, request);

        assertEquals(dto, result);

        verify(userRepository).findByEmail(email);
        verify(tweetRepository).save(any(Tweet.class));
        verify(tweetDtoConverter).convert(any(Tweet.class));
    }

    @DisplayName("Find By Id")
    @Test
    void findById() {
        Long id = 1L;

        Tweet tweet = new Tweet();
        TweetDto dto = new TweetDto(null,null, null, null, null);

        when(tweetRepository.findById(id)).thenReturn(Optional.of(tweet));
        when(tweetDtoConverter.convert(tweet)).thenReturn(dto);

        TweetDto result = tweetService.findById(id);

        assertEquals(dto, result);
        assertEquals(dto.comments(), result.comments());

        verify(tweetRepository).findById(id);
        verify(tweetDtoConverter).convert(tweet);
    }


    @DisplayName("Update Tweet")
    @Test
    void update() {
        Long id =  1L;
        UpdateTweetRequest request = new UpdateTweetRequest();
        request.setText("updated tweet");
        String email = "veliaksu120@gmail.com";

        User user = new User();
        user.setId(1L);

        Tweet tweet = new Tweet();
        tweet.setId(1L);
        tweet.setUser(user);

        TweetDto dto = new TweetDto(null,null, null, null, null);

        when(tweetRepository.findById(id)).thenReturn(Optional.of(tweet));
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(tweetRepository.save(any(Tweet.class))).thenReturn(tweet);
        when(tweetDtoConverter.convert(tweet)).thenReturn(dto);

        TweetDto result = tweetService.update(id,request,email);

        assertEquals(dto, result);
        assertEquals(dto.text(), result.text());

        verify(userRepository).findByEmail(email);
        verify(tweetRepository).save(any(Tweet.class));
        verify(tweetDtoConverter).convert(tweet);
    }

    @DisplayName("Cant Update Tweet")
    @Test
    void cantUpdate() {
        Long id =  1L;
        UpdateTweetRequest request = new UpdateTweetRequest();
        request.setText("updated tweet");
        String email = "veliaksu120@gmail.com";

        User user = new User();
        user.setId(1L);

        User otherUser = new User();
        otherUser.setId(2L);

        Tweet tweet = new Tweet();
        tweet.setId(1L);
        tweet.setUser(user);

        TweetDto dto = new TweetDto(null,null, null, null, null);

        when(tweetRepository.findById(id)).thenReturn(Optional.of(tweet));
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(otherUser));

        assertThatThrownBy(() -> tweetService.update(id,request,email)).isInstanceOf(ApiException.class).hasMessageContaining("You cant change the tweet");

        verify(tweetRepository, never()).save(any(Tweet.class));
        verify(tweetDtoConverter, never()).convert(any(Tweet.class));
    }

    @DisplayName("Tweet Delete")
    @Test
    void delete() {
        Long id = 1L;
        String email = "veliaksu120@gmail.com";

        User user = new User();
        user.setId(1L);

        Tweet tweet = new Tweet();
        tweet.setId(1L);
        tweet.setUser(user);

        TweetDto dto = new TweetDto("dsads", null, null, null, null);

        when(tweetRepository.findById(id)).thenReturn(Optional.of(tweet));
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(tweetDtoConverter.convert(tweet)).thenReturn(dto);

        TweetDto result = tweetService.delete(id, email);

        assertEquals(dto, result);

        verify(tweetRepository).delete(tweet);
        verify(tweetDtoConverter).convert(tweet);
    }
}