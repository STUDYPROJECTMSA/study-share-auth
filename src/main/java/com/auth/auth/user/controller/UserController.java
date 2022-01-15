package com.auth.auth.user.controller;

import com.auth.auth.oauth2.dto.SecurityToken;
import com.auth.auth.user.controller.request.LoginRequest;
import com.auth.auth.user.dto.UserDto;
import com.auth.auth.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequest loginRequest) {
        UserDto info = userService.loginService(loginRequest.getCode());
        return ResponseEntity.ok(info);
    }

    @PostMapping("/refresh")
    public ResponseEntity<SecurityToken> newRefreshToken(HttpRequest request) throws Exception {
        String expiredAccessToken = request.getHeaders().getOrEmpty("token").get(0);
        String refreshToken = request.getHeaders().getOrEmpty("refresh").get(0);
        SecurityToken newToken = userService.refreshService(expiredAccessToken, refreshToken);
        return ResponseEntity.ok(newToken);
    }
}
