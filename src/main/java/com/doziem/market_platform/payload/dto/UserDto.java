package com.doziem.market_platform.payload.dto;

import com.doziem.market_platform.enums.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Data
@Builder
public class UserDto {

    private  String userId;
    private String email;

    @JsonProperty(access = WRITE_ONLY)
    private String password;

    private String displayName;

    private String username;

    private Role role;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    private ZonedDateTime lastLogin;
}
