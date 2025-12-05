package com.shootdoori.match.controller;

import com.shootdoori.match.dto.AuthTokenResponse;
import com.shootdoori.match.dto.LoginRequest;
import com.shootdoori.match.dto.TokenRefreshRequest;
import com.shootdoori.match.entity.user.dto.UserCreateRequest;
import com.shootdoori.match.resolver.LoginUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping("/login")
    public ResponseEntity<AuthTokenResponse> login(
        @Valid @RequestBody LoginRequest loginRequest,
        HttpServletRequest request
    ) {

        return null;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthTokenResponse> register(
        @Valid @RequestBody UserCreateRequest profileCreateRequest,
        HttpServletRequest request
    ) {
        return null;
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthTokenResponse> refresh(
        @RequestBody TokenRefreshRequest token
    ) {
        return null;
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody TokenRefreshRequest token) {

        return null;
    }

    @PostMapping("/logout-all")
    public ResponseEntity<Void> logoutAll(
        @LoginUser Long userId
    ) {
        return null;
    }

    @PostMapping("/login-cookie")
    public ResponseEntity<Void> loginWithCookie(
        @Valid @RequestBody LoginRequest loginRequest,
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        return null;
    }

    @PostMapping("/logout-cookie")
    public ResponseEntity<Void> logoutWithCookie(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        return null;
    }

}