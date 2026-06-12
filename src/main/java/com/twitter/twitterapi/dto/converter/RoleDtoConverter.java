package com.twitter.twitterapi.dto.converter;

import com.twitter.twitterapi.dto.RoleDto;
import com.twitter.twitterapi.entity.Role;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleDtoConverter {

    public RoleDto convert(Role role) {
        return new RoleDto(role.getAuthority());
    }

    public Set<RoleDto> convert(Set<Role> roles) {
        return roles.stream().map(r->convert(r)).collect(Collectors.toSet());
    }
}
