package com.twitter.twitterapi.dto.converter;

import com.twitter.twitterapi.dto.RoleDto;
import com.twitter.twitterapi.dto.UserDto;
import com.twitter.twitterapi.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDtoConverter {

    public UserDto convert(User user) {
        return new UserDto(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRoles().stream().map((r)->new RoleDto(r.getAuthority())).collect(Collectors.toSet())
        );
    }

    public List<UserDto> convert(List<User> users) {
        return users.stream().map((u)->convert(u)).collect(Collectors.toList());
    }
}
