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
import com.doziem.market_platform.system.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserImplementation customUser;
    private final UserMapper userMapper;

    @Override
    public Result signup(UserDto userDto) {

        try {

        Optional<User> user = userRepository.findByEmail(userDto.getEmail());

        if (user.isPresent()) {
            throw  new UserException("User with email " + userDto.getEmail() + " already exists");
        }
        validateUser(userDto);
        User convertedUser = UserMapper.toEntity(userDto);

        convertedUser.setPassword(passwordEncoder.encode(userDto.getPassword()));

       User saveUser = userRepository.save(convertedUser);

        Authentication authentication =new   UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();

        authResponse.setToken(token);
        authResponse.setUser(UserMapper.toDto(saveUser));

        return new Result(true, "Successfully Registered", authResponse);
        }catch (Exception ex){
            log.info("Internal Server Error :: {}",ex.getMessage());
            throw  ex;
        }
    }

    @Override
    public Result login(UserDto userDto) {
        try {

        User user = userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(()->new UserException("User not found"));

        Authentication auth = authenticate(userDto, user);
        SecurityContextHolder.getContext().setAuthentication(auth);

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        String roles = authorities.iterator().next().getAuthority();

        String token = jwtProvider.generateToken(auth);

//        user.setRole(Role.valueOf(roles));
        user.setLastLogin(ZonedDateTime.now());
        userRepository.save(user);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        authResponse.setUser(UserMapper.toDto(user));

        return new Result(true,"Successful", authResponse);
        }catch (Exception ex){
            log.info("Internal Server Error ::: {}",ex.getMessage());
            throw  ex;
        }
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
