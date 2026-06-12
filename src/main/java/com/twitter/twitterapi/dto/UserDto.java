package com.twitter.twitterapi.dto;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDto(String firstName, String lastName, String email,Set<RoleDto> roles) {
}
