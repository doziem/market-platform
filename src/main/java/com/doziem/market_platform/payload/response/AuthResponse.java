package com.doziem.market_platform.payload.response;

import com.doziem.market_platform.payload.dto.UserDto;
import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private UserDto user;
}
