package com.doziem.market_platform.service.impl;

import com.doziem.market_platform.configuration.JwtProvider;
import com.doziem.market_platform.enums.Role;
import com.doziem.market_platform.exception.UserException;
import com.doziem.market_platform.mapper.UserMapper;
import com.doziem.market_platform.model.User;
import com.doziem.market_platform.payload.dto.UserDto;
import com.doziem.market_platform.payload.response.AuthResponse;
import com.doziem.market_platform.repository.UserRepository;
import com.doziem.market_platform.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserImplementation customUser;

    @Override
    public AuthResponse signup(UserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail());

        if (user != null) {
            throw  new UserException("User with email " + userDto.getEmail() + " already exists");
        }

        validateUser(userDto);

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setUpdatedAt(ZonedDateTime.now());
        user.setCreatedAt(ZonedDateTime.now());
        user.setLastLogin(ZonedDateTime.now());

       User saveUser = userRepository.save(user);

        Authentication authentication =new   UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();

        authResponse.setMessage("User registered successfully");
        authResponse.setToken(token);
        authResponse.setUser(UserMapper.toDto(saveUser));

        return authResponse;
    }

    @Override
    public AuthResponse login(UserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail());

        Authentication auth = authenticate(userDto, user);
        SecurityContextHolder.getContext().setAuthentication(auth);

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        String roles = authorities.iterator().next().getAuthority();

        String token = jwtProvider.generateToken(auth);

        user.setLastLogin(ZonedDateTime.now());
        userRepository.save(user);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("User logged in successfully");
        authResponse.setToken(token);
        authResponse.setUser(UserMapper.toDto(user));
        return authResponse;

    }

    private Authentication authenticate(UserDto dto, User user) {

        UserDetails userDetails = customUser.loadUserByUsername(dto.getEmail());

        if (userDetails == null) {
            throw new UserException("User with email " + " " + "does not exist");
        }

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new UserException("Invalid credentials");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }


    private void validateUser(UserDto userDto) {
        if (userDto.getEmail() == null || userDto.getEmail().isEmpty()) {
            throw new UserException("Email cannot be empty");
        }
        if (!userDto.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new UserException("Provide a valid email");
        }

        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            throw new UserException("Password cannot be empty");
        }

        if (userDto.getRole().equals(Role.ROLE_ADMIN) || userDto.getRole().equals(Role.ROLE_SUPER_ADMIN)) {
            throw new UserException("Cannot assign admin role");
        }

        if (userDto.getPassword().length() < 8 || !userDto.getPassword().matches(".*[A-Z].*") || !userDto.getPassword().matches(".*[a-z].*") || !userDto.getPassword().matches(".*\\d.*")) {
            throw new UserException("Password must be at least 8 characters and contain uppercase, lowercase letters, and numbers");
        }
    }
}
