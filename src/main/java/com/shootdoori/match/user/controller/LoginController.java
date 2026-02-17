package com.shootdoori.match.user.controller;

import com.shootdoori.match.dto.LoginRequest;
import com.shootdoori.match.policy.CookieSecurityPolicy;
import com.shootdoori.match.resolver.LoginUser;
import com.shootdoori.match.user.dto.AuthTokenResponse;
import com.shootdoori.match.user.dto.TokenRefreshRequest;
import com.shootdoori.match.user.dto.UserCreateRequest;
import com.shootdoori.match.user.service.AuthService;
import com.shootdoori.match.user.service.TokenRefreshService;
import com.shootdoori.match.user.service.UserCommandService;
import com.shootdoori.match.util.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.Arrays;
import java.util.Optional;
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

    private final AuthService authService;
    private final UserCommandService userCommandService;
    private final TokenRefreshService tokenRefreshService;
    private final CookieSecurityPolicy cookieSecurityPolicy;

    public LoginController(AuthService authService, UserCommandService userCommandService,
        TokenRefreshService tokenRefreshService, CookieSecurityPolicy cookieSecurityPolicy) {
        this.authService = authService;
        this.userCommandService = userCommandService;
        this.tokenRefreshService = tokenRefreshService;
        this.cookieSecurityPolicy = cookieSecurityPolicy;
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
        @RequestBody TokenRefreshRequest request
    ) {
        return new ResponseEntity<>(tokenRefreshService.refresh(request), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody TokenRefreshRequest token) {
        authService.logout(token.refreshToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/logout-all")
    public ResponseEntity<Void> logoutAll(
        @LoginUser Long userId
    ) {
        authService.logoutAll(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/login-cookie")
    public ResponseEntity<Void> loginWithCookie(
        @Valid @RequestBody LoginRequest loginRequest,
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
        AuthTokenResponse token = authService.login(loginRequest, userAgent);

        CookieUtil.setHttpOnlyCookie(
            response,
            "accessToken",
            token.accessToken(),
            token.accessTokenExpiresIn() / 1000,
            cookieSecurityPolicy
        );
        CookieUtil.setHttpOnlyCookie(
            response,
            "refreshToken",
            token.refreshToken(),
            token.refreshTokenExpiresIn() / 1000,
            cookieSecurityPolicy
        );

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/logout-cookie")
    public ResponseEntity<Void> logoutWithCookie(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        findCookieValue(request, "refreshToken").ifPresent(authService::logout);

        CookieUtil.clearHttpOnlyCookie(response, "accessToken", cookieSecurityPolicy);
        CookieUtil.clearHttpOnlyCookie(response, "refreshToken", cookieSecurityPolicy);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Optional<String> findCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return Optional.empty();
        }

        return Arrays.stream(cookies)
            .filter(cookie -> cookieName.equals(cookie.getName()))
            .map(Cookie::getValue)
            .findFirst();
    }
}
