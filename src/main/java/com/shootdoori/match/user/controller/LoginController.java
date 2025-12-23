package com.shootdoori.match.user.controller;

import com.shootdoori.match.dto.LoginRequest;
import com.shootdoori.match.dto.TokenRefreshRequest;
import com.shootdoori.match.user.dto.AuthTokenResponse;
import com.shootdoori.match.user.dto.UserCreateRequest;
import com.shootdoori.match.user.service.AuthService;
import com.shootdoori.match.user.service.UserCommandService;
import com.shootdoori.match.resolver.LoginUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final AuthService authService;
    private final UserCommandService userCommandService;

    public LoginController(AuthService authService, UserCommandService userCommandService) {
        this.authService = authService;
        this.userCommandService = userCommandService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthTokenResponse> login(
        @Valid @RequestBody LoginRequest loginRequest,
        HttpServletRequest request
    ) {
        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);

        return new ResponseEntity<>(authService.login(loginRequest, userAgent), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthTokenResponse> register(
        @Valid @RequestBody UserCreateRequest userCreateRequest,
        HttpServletRequest request
    ) {
        userCommandService.create(userCreateRequest);

        LoginRequest loginRequest = LoginRequest.from(userCreateRequest);
        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);

        return new ResponseEntity<>(authService.login(loginRequest, userAgent), HttpStatus.CREATED);
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