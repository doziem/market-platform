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
import com.doziem.market_platform.service.email.EmailService;
import com.doziem.market_platform.system.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserImplementation customUser;
    private final UsernameService usernameService;
    private final EmailService emailService;

    @Value("${app.verification-url}")
    private String verificationUrl;

    @Transactional
    @Override
    public Result signup(UserDto userDto) {
       Optional<User> user = userRepository.findByEmail(userDto.getEmail());

       if (user.isPresent()) {
           throw new UserException("User with email " + userDto.getEmail() + " already exists");
       }
       validateUser(userDto);
       User convertedUser = UserMapper.toEntity(userDto);
       String username = usernameService.autoGenerateUsername(userDto.getDisplayName(), userDto.getEmail());
       convertedUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
       convertedUser.setUsername(username);
       convertedUser.setEmailVerificationToken(UUID.randomUUID().toString());
       convertedUser.setEmailVerificationTokenExpiry(ZonedDateTime.now().plusDays(1));

       User saveUser = userRepository.save(convertedUser);
       emailService.sendVerificationEmail(saveUser, verificationUrl + "?token=" + saveUser.getEmailVerificationToken());

       AuthResponse authResponse = new AuthResponse();
       authResponse.setUser(UserMapper.toDto(saveUser));

       return new Result(true, "Successfully Registered. Please verify your email.", authResponse);
    }

    @Transactional
    @Override
    public Result login(UserDto userDto) {
       User user = userRepository.findByEmail(userDto.getEmail())
               .orElseThrow(() -> new UserException("User not found"));

       if (Boolean.FALSE.equals(user.getActive())) {
           throw new UserException("This account is deactivated");
       }

       if (!user.isVerify()) {
           if (isVerificationTokenExpired(user)) {
               rotateVerificationTokenAndNotify(user);
               throw new UserException("Verification token expired. A new verification email has been sent.");
           }
           throw new UserException("Please verify your email before logging in");
       }

       Authentication auth = authenticate(userDto, user);
       SecurityContextHolder.getContext().setAuthentication(auth);

        String token = jwtProvider.generateToken(auth, user.getUsername());

        user.setLastLogin(ZonedDateTime.now());
        userRepository.save(user);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        authResponse.setUser(UserMapper.toDto(user));

        return new Result(true,"Successful", authResponse);
    }

    @Transactional
    @Override
    public Result verifyEmail(String token) {
        User user = userRepository.findByEmailVerificationToken(token)
                .orElseThrow(() -> new UserException("Invalid verification token"));

        if (isVerificationTokenExpired(user)) {
            rotateVerificationTokenAndNotify(user);
            return new Result(false, "Verification token expired. A new verification email has been sent.");
        }

        user.setVerify(true);
        user.setEmailVerificationToken(null);
        user.setEmailVerificationTokenExpiry(null);
        userRepository.save(user);

        return new Result(true, "Email verified successfully");
    }

    private boolean isVerificationTokenExpired(User user) {
        return user.getEmailVerificationTokenExpiry() != null
                && user.getEmailVerificationTokenExpiry().isBefore(ZonedDateTime.now());
    }

    private void rotateVerificationTokenAndNotify(User user) {
        user.setEmailVerificationToken(UUID.randomUUID().toString());
        user.setEmailVerificationTokenExpiry(ZonedDateTime.now().plusDays(1));
        User savedUser = userRepository.save(user);
        emailService.sendVerificationEmail(
                savedUser,
                verificationUrl + "?token=" + savedUser.getEmailVerificationToken()
        );
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
