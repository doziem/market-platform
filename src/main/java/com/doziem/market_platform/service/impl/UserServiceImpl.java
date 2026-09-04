package com.doziem.market_platform.service.impl;

import com.doziem.market_platform.configuration.JwtProvider;
import com.doziem.market_platform.enums.Role;
import com.doziem.market_platform.exception.UserException;
import com.doziem.market_platform.mapper.UserMapper;
import com.doziem.market_platform.model.User;
import com.doziem.market_platform.payload.dto.UserDto;
import com.doziem.market_platform.repository.UserRepository;
import com.doziem.market_platform.service.UserService;
import com.doziem.market_platform.system.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;


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

    @Transactional
    @Override
    public Result updateUser(String userId, UserDto userDto) {
        User currentUser = getAuthenticatedUser();

        if (!currentUser.getUserId().equals(userId)) {
            throw new UserException("You can only update your own account");
        }

        User userToUpdate = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found"));

        if (userDto.getDisplayName() != null && !userDto.getDisplayName().isBlank()) {
            userToUpdate.setDisplayName(userDto.getDisplayName().trim());
        }

        if (userDto.getPassword() != null && !userDto.getPassword().isBlank()) {
            validatePassword(userDto.getPassword());
            userToUpdate.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        userToUpdate.setUpdatedAt(ZonedDateTime.now());
        User savedUser = userRepository.save(userToUpdate);
        return new Result(true, "User updated successfully", UserMapper.toDto(savedUser));
    }

    @Transactional
    @Override
    public Result deactivateUser(String userId) {
        User currentUser = getAuthenticatedUser();
        if (!isAdmin(currentUser)) {
            throw new UserException("Only admins can deactivate users");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found"));

        user.setActive(false);
        user.setUpdatedAt(ZonedDateTime.now());
        User savedUser = userRepository.save(user);
        return new Result(true, "User deactivated successfully", UserMapper.toDto(savedUser));
    }

    @Transactional
    @Override
    public Result deleteUser(String userId) {
        User currentUser = getAuthenticatedUser();
        boolean deletingSelf = currentUser.getUserId().equals(userId);

        if (!isAdmin(currentUser) && !deletingSelf) {
            throw new UserException("Only admins can delete other users");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found"));

        userRepository.delete(user);
        return new Result(true, "User deleted successfully");
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new UserException("User not authenticated");
        }

        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new UserException("User not found"));
    }

    private boolean isAdmin(User user) {
        return user.getRole() == Role.ROLE_ADMIN || user.getRole() == Role.ROLE_SUPER_ADMIN;
    }

    private void validatePassword(String password) {
        if (password.length() < 8
                || !password.matches(".*[A-Z].*")
                || !password.matches(".*[a-z].*")
                || !password.matches(".*\\d.*")) {
            throw new UserException("Password must be at least 8 characters and contain uppercase, lowercase letters, and numbers");
        }
    }
}
