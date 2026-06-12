package com.twitter.twitterapi.controller;

import com.twitter.twitterapi.config.SecurityConfig;
import com.twitter.twitterapi.dto.TweetDto;
import com.twitter.twitterapi.dto.request.CreateTweetRequest;
import com.twitter.twitterapi.dto.request.UpdateTweetRequest;
import com.twitter.twitterapi.entity.Tweet;
import com.twitter.twitterapi.entity.User;
import com.twitter.twitterapi.service.TweetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TweetController.class)
@Import(SecurityConfig.class)
class TweetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TweetService tweetService;


    @Test
    @WithMockUser(username = "veliaksu120@gmail.com")
    void save() throws Exception {
        String email = "veliaksu120@gmail.com";

        CreateTweetRequest request = new CreateTweetRequest();
        request.setText("hello");

        TweetDto dto = new TweetDto(null, null, null, null, null);

        when(tweetService.create(eq(email), any(CreateTweetRequest.class)))
                .thenReturn(dto);

        mockMvc.perform(post("/tweet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(request)))
                .andExpect(status().isCreated());

        verify(tweetService).create(eq(email), any(CreateTweetRequest.class));
    }

    @Test
    @WithMockUser(username = "veliaksu120@gmail.com")
    void findById() throws Exception{
        Long id = 1L;

        TweetDto response = new TweetDto(null, null, null, null, null);

        when(tweetService.findById(id)).thenReturn(response);

        mockMvc.perform(get("/tweet/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "veliaksu120@gmail.com")
    void update() throws Exception{
        Long id = 1L;
        String email = "veliaksu120@gmail.com";

        UpdateTweetRequest request = new UpdateTweetRequest();
        request.setText("updated tweet");

        TweetDto response = new TweetDto(null, null, null, null, null);

        when(tweetService.update(eq(id), any(UpdateTweetRequest.class), eq(email)))
                .thenReturn(response);

        mockMvc.perform(put("/tweet/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "veliaksu120@gmail.com")
    void deleteById() throws Exception{
        Long id = 1L;
        String email = "veliaksu120@gmail.com";

        TweetDto response = new TweetDto(null, null, null, null, null);

        when(tweetService.delete(id, email)).thenReturn(response);

        mockMvc.perform(delete("/tweet/{id}", id))
                .andExpect(status().isOk());
    }

    public static String jsonToString(Object object) throws Exception {
        return new ObjectMapper().writeValueAsString(object);
    }
}