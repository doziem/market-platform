package com.doziem.market_platform.service.impl;

import com.doziem.market_platform.configuration.JwtProvider;
import com.doziem.market_platform.exception.UserException;
import com.doziem.market_platform.mapper.UserMapper;
import com.doziem.market_platform.model.User;
import com.doziem.market_platform.payload.dto.UserDto;
import com.doziem.market_platform.repository.UserRepository;
import com.doziem.market_platform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;


    @Override
    public UserDto getUserFromToken(String token) {
        String email = jwtProvider.getEmailFromToken(token);
        User user =userRepository.findByEmail(email).orElseThrow(()->new UserException("User not found"));
        return UserMapper.toDto(user);
    }

    @Override
    public UserDto getCurrentUser() {
        String  email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(()->new UserException("User not found"));
        return UserMapper.toDto(user);
    }

    @Override
    public UserDto getCurrentUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new UserException("User not found"));
        return UserMapper.toDto(user);
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new UserException("User not found"));
        return UserMapper.toDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserMapper::toDto).toList();
    }
}
