package com.twitter.twitterapi.service;

import com.twitter.twitterapi.dto.UserDto;
import com.twitter.twitterapi.dto.converter.UserDtoConverter;
import com.twitter.twitterapi.dto.request.CreateUserRequest;
import com.twitter.twitterapi.dto.request.LoginUserRequest;
import com.twitter.twitterapi.entity.Role;
import com.twitter.twitterapi.entity.User;
import com.twitter.twitterapi.exception.ApiException;
import com.twitter.twitterapi.repository.RoleRepository;
import com.twitter.twitterapi.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDtoConverter userDtoConverter;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository,
                                 PasswordEncoder passwordEncoder, UserDtoConverter userDtoConverter,
                                AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDtoConverter = userDtoConverter;
        this.authenticationManager = authenticationManager;
    }

    public UserDto register(CreateUserRequest createUserRequest) {
        Optional<User> existUser = userRepository.findByEmail(createUserRequest.getEmail());
        if (existUser.isPresent()) {
            throw new ApiException("User already exist",HttpStatus.BAD_REQUEST);
        }
        String encodedPassword = passwordEncoder.encode(createUserRequest.getPassword());
        Role role = roleRepository.findByAuthority("USER").get();

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        User user = new User();
        user.setFirstName(createUserRequest.getFirstName());
        user.setLastName(createUserRequest.getLastName());
        user.setEmail(createUserRequest.getEmail());
        user.setPassword(encodedPassword);
        user.setRoles(roles);

        return userDtoConverter.convert(userRepository.save(user));
    }

    public UserDto login(LoginUserRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        return userDtoConverter.convert(user);
    }
}
