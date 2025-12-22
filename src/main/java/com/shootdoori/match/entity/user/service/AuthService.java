package com.shootdoori.match.entity.user.service;

import com.shootdoori.match.dto.LoginRequest;
import com.shootdoori.match.entity.user.DeviceType;
import com.shootdoori.match.entity.user.GeneratedToken;
import com.shootdoori.match.entity.user.User;
import com.shootdoori.match.entity.user.dto.AuthTokenResponse;
import com.shootdoori.match.entity.user.repository.RefreshTokenRepository;
import com.shootdoori.match.entity.user.util.TokenIssuer;
import com.shootdoori.match.exception.common.ErrorCode;
import com.shootdoori.match.exception.common.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final UserQueryService userQueryService;
    private final PasswordEncoder passwordEncoder;
    private final TokenIssuer tokenIssuer;
    private final RefreshTokenRepository refreshTokenRepository;

    private static final String LOG_LOGIN_SUCCESS = "User login success: email={}, device={}";
    private static final String LOG_LOGIN_FAIL = "Login failed: email={}, reason={}";

    public AuthService(UserQueryService userQueryService, PasswordEncoder passwordEncoder,
        TokenIssuer tokenIssuer, RefreshTokenRepository refreshTokenRepository) {
        this.userQueryService = userQueryService;
        this.passwordEncoder = passwordEncoder;
        this.tokenIssuer = tokenIssuer;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public AuthTokenResponse login(LoginRequest request, String userAgent) {
        User user = userQueryService.findByEmail(request.email());
        validatePasswordMatch(request, user);

        DeviceType deviceType = DeviceType.fromUserAgent(userAgent);
        GeneratedToken generatedToken = tokenIssuer.issue(user, deviceType, userAgent);

        saveRefreshToken(generatedToken);
        logLoginSuccess(userAgent, user);

        return createAuthTokenResponse(generatedToken);
    }

    private void logLoginSuccess(String userAgent, User user) {
        log.info(LOG_LOGIN_SUCCESS, user.getEmail(), userAgent);
    }

    private void saveRefreshToken(GeneratedToken generatedToken) {
        refreshTokenRepository.save(generatedToken.refreshToken());
    }

    private void validatePasswordMatch(LoginRequest request, User user) {
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            log.warn(LOG_LOGIN_FAIL, request.email(), "Password mismatch");
            throw new UnauthorizedException(ErrorCode.FAIL_LOGIN);
        }
    }

    private AuthTokenResponse createAuthTokenResponse(GeneratedToken generatedToken) {
        return new AuthTokenResponse(
            generatedToken.authToken().accessToken(),
            generatedToken.authToken().refreshToken(),
            generatedToken.authToken().accessTokenExpiresIn(),
            generatedToken.authToken().refreshTokenExpiresIn()
        );
    }
}
