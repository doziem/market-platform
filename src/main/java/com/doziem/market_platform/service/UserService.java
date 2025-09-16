package com.doziem.market_platform.service;

import com.doziem.market_platform.payload.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto getUserFromToken(String token);
    UserDto getCurrentUser();
    UserDto getCurrentUserByEmail(String email);
    UserDto getUSerByUserId(String userId);
    List<UserDto> getAllUsers();
}
