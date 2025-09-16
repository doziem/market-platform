package com.doziem.market_platform.service;

import com.doziem.market_platform.payload.dto.UserDto;
import com.doziem.market_platform.payload.response.AuthResponse;

public interface AuthService {
    AuthResponse signup(UserDto userDto);
    AuthResponse login(UserDto userDto);
}
