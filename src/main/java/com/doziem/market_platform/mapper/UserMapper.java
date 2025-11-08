package com.doziem.market_platform.mapper;

import com.doziem.market_platform.model.User;
import com.doziem.market_platform.payload.dto.UserDto;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Data
@Component
public class UserMapper {

    public static UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        return UserDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .displayName(user.getDisplayName())
                .username(user.getUsername())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .lastLogin(user.getLastLogin())
                .build();
    }

    public static User toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }

        return User.builder()
                .email(dto.getEmail())
                .displayName(dto.getDisplayName())
                .username(dto.getUsername())
                .role(dto.getRole())
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .lastLogin(ZonedDateTime.now())
                .build();
    }
}
