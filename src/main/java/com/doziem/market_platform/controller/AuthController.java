package com.doziem.market_platform.controller;

import com.doziem.market_platform.payload.dto.UserDto;
import com.doziem.market_platform.service.AuthService;
import com.doziem.market_platform.system.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Result> signup(@RequestBody @Valid UserDto userDto) {
        Result authResponse = authService.signup(userDto);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<Result> login(@RequestBody @Valid UserDto userDto) {
        Result authResponse = authService.login(userDto);
        return ResponseEntity.status(HttpStatus.OK).body(authResponse);
    }
}
