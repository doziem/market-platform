package com.doziem.market_platform.service;

import com.doziem.market_platform.payload.dto.UserDto;
import com.doziem.market_platform.system.Result;

public interface AuthService {
    Result signup(UserDto userDto);
    Result login(UserDto userDto);
}
