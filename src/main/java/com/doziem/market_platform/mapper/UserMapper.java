package com.doziem.market_platform.mapper;

import com.doziem.market_platform.model.User;
import com.doziem.market_platform.payload.dto.UserDto;
import lombok.Data;

@Data
public class UserMapper {

    public static UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setCreatedAt(user.getCreatedAt());
        userDto.setUpdatedAt(user.getUpdatedAt());
        userDto.setLastLogin(user.getLastLogin());
        return userDto;
    }

    public static User toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setCreatedAt(userDto.getCreatedAt());
        user.setUpdatedAt(userDto.getUpdatedAt());
        user.setLastLogin(userDto.getLastLogin());
        return user;
    }
}
