package com.doziem.market_platform.service;

import com.doziem.market_platform.payload.dto.UserDto;
import com.doziem.market_platform.system.Result;

import java.util.List;

public interface UserService {
    UserDto getUserFromToken(String token);
    UserDto getCurrentUser();
    UserDto getCurrentUserByEmail(String email);
    UserDto getUserByUserId(String userId);
    List<UserDto> getAllUsers();
    Result updateUser(String userId, UserDto userDto);
    Result deactivateUser(String userId);
    Result deleteUser(String userId);
}
