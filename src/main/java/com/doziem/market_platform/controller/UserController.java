package com.doziem.market_platform.controller;

import com.doziem.market_platform.payload.dto.UserDto;
import com.doziem.market_platform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(@RequestHeader("Authorization") String  token){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserFromToken(token));
    }

    @GetMapping("/current/user")
    public ResponseEntity<UserDto> getCurrentUser(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getCurrentUser());
    }

    @GetMapping
    public ResponseEntity<UserDto> getCurrentUserByUserEmail(@RequestParam String  email){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getCurrentUserByEmail(email));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUSerByUserId(@PathVariable String  userId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUSerByUserId(userId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

}
