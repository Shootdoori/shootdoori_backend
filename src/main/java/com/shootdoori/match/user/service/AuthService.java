package com.shootdoori.match.user.service;

import com.shootdoori.match.dto.LoginRequest;
import com.shootdoori.match.exception.common.ErrorCode;
import com.shootdoori.match.exception.common.UnauthorizedException;
import com.shootdoori.match.user.domain.DeviceType;
import com.shootdoori.match.user.domain.User;
import com.shootdoori.match.user.dto.AuthTokenResponse;
import com.shootdoori.match.user.repository.RefreshTokenRepository;
import com.shootdoori.match.user.util.GeneratedToken;
import com.shootdoori.match.user.util.TokenIssuer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    private final UserQueryService userQueryService;
    private final PasswordEncoder passwordEncoder;
    private final TokenIssuer tokenIssuer;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthService(UserQueryService userQueryService, PasswordEncoder passwordEncoder,
        TokenIssuer tokenIssuer, RefreshTokenRepository refreshTokenRepository) {
        this.userQueryService = userQueryService;
        this.passwordEncoder = passwordEncoder;
        this.tokenIssuer = tokenIssuer;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public AuthTokenResponse login(LoginRequest request, String userAgent) {
        User user = userQueryService.findByEmail(request.email());
        validatePasswordMatch(request, user);

        DeviceType deviceType = DeviceType.fromUserAgent(userAgent);
        GeneratedToken generatedToken = tokenIssuer.issue(user, deviceType, userAgent);

        saveRefreshToken(generatedToken);

        return AuthTokenResponse.from(generatedToken);
    }

    private void saveRefreshToken(GeneratedToken generatedToken) {
        refreshTokenRepository.save(generatedToken.refreshToken());
    }

    private void validatePasswordMatch(LoginRequest request, User user) {
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new UnauthorizedException(ErrorCode.FAIL_LOGIN);
        }
    }
}
