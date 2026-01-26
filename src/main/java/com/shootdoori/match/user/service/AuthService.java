package com.shootdoori.match.user.service;

import com.shootdoori.match.dto.LoginRequest;
import com.shootdoori.match.exception.common.ErrorCode;
import com.shootdoori.match.exception.common.UnauthorizedException;
import com.shootdoori.match.user.domain.DeviceType;
import com.shootdoori.match.user.domain.User;
import com.shootdoori.match.user.dto.AuthTokenResponse;
import com.shootdoori.match.user.repository.RefreshTokenRepository;
import com.shootdoori.match.user.util.GeneratedToken;
import com.shootdoori.match.user.util.JwtUtil;
import com.shootdoori.match.user.util.TokenIssuer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import java.util.Collections;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final JwtUtil jwtUtil;

    private static final String BEARER_PREFIX = "Bearer ";

    public AuthService(UserQueryService userQueryService, PasswordEncoder passwordEncoder,
        TokenIssuer tokenIssuer, RefreshTokenRepository refreshTokenRepository, JwtUtil jwtUtil) {
        this.userQueryService = userQueryService;
        this.passwordEncoder = passwordEncoder;
        this.tokenIssuer = tokenIssuer;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtUtil = jwtUtil;
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

    public void logout(String refreshToken) {
        Claims claims = jwtUtil.getClaims(refreshToken);
        String tokenId = claims.getId();

        refreshTokenRepository.deleteById(tokenId);
    }

    public void logoutAll(Long userId) {
        User user = userQueryService.findByIdForEntity(userId);
        refreshTokenRepository.deleteByUser(user);
    }

    public UsernamePasswordAuthenticationToken authenticationToken(String authorizationHeader) {

        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            String jwt = authorizationHeader.substring(BEARER_PREFIX.length());

            try {
                boolean valid = jwtUtil.validateToken(jwt);
                if (valid && SecurityContextHolder.getContext().getAuthentication() == null) {
                    String userId = jwtUtil.getUserId(jwt);
                    Long principalUserId = Long.parseLong(userId);

                    return new UsernamePasswordAuthenticationToken(
                        principalUserId, null, Collections.emptyList());
                }
            } catch (ExpiredJwtException e) {
                throw new UnauthorizedException(ErrorCode.EXPIRED_TOKEN);
            } catch (JwtException | NumberFormatException e) {
                throw new UnauthorizedException(ErrorCode.INVALID_TOKEN);
            }
        }
        return null;
    }

    private void validatePasswordMatch(LoginRequest request, User user) {
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new UnauthorizedException(ErrorCode.FAIL_LOGIN);
        }
    }
}
