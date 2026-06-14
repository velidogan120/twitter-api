package com.twitter.twitterapi.repository;

import com.twitter.twitterapi.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class UserRepositoryTest {

    private final UserRepository userRepository;

    @Autowired
    UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private User savedUser;

    @BeforeEach
    void setUp() {
        User newUser = new User();
        newUser.setFirstName("Veli");
        newUser.setLastName("Aksu");
        newUser.setEmail("veliaksu120@gmail.com");
        newUser.setPassword("123456");
        savedUser = userRepository.save(newUser);
    }

    @AfterEach
    void tearDown() {
        userRepository.delete(savedUser);
    }

    @DisplayName("Find user by email")
    @Test
    void findByEmail() {
        Optional<User> user = userRepository.findByEmail("veliaksu120@gmail.com");

        assertNotNull(user.get());
        assertEquals("Veli", user.get().getFirstName());
        assertEquals("Aksu", user.get().getLastName());
        assertEquals("veliaksu120@gmail.com", user.get().getEmail());
    }
}