package com.shootdoori.match.user.service;

import com.shootdoori.match.exception.common.ErrorCode;
import com.shootdoori.match.exception.common.UnauthorizedException;
import com.shootdoori.match.user.domain.RefreshToken;
import com.shootdoori.match.user.dto.AuthTokenResponse;
import com.shootdoori.match.user.dto.TokenRefreshRequest;
import com.shootdoori.match.user.repository.RefreshTokenRepository;
import com.shootdoori.match.user.util.GeneratedToken;
import com.shootdoori.match.user.util.JwtUtil;
import com.shootdoori.match.user.util.TokenIssuer;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TokenRefreshService {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenIssuer tokenIssuer;

    public TokenRefreshService(JwtUtil jwtUtil, RefreshTokenRepository refreshTokenRepository,
        TokenIssuer tokenIssuer) {
        this.jwtUtil = jwtUtil;
        this.refreshTokenRepository = refreshTokenRepository;
        this.tokenIssuer = tokenIssuer;
    }

    public AuthTokenResponse refresh(TokenRefreshRequest request) {
        Claims claims = jwtUtil.getClaims(request.refreshToken());
        String tokenId = claims.getId();

        RefreshToken storedToken = refreshTokenRepository.findById(tokenId)
            .orElseThrow(() -> new UnauthorizedException(ErrorCode.INVALID_TOKEN));

        revoke(storedToken);

        return reissue(storedToken);
    }

    private AuthTokenResponse reissue(RefreshToken storedToken) {
        GeneratedToken newTokens = tokenIssuer.issue(
            storedToken.getUser(),
            storedToken.getDeviceType(),
            storedToken.getUserAgent()
        );

        refreshTokenRepository.save(newTokens.refreshToken());

        return AuthTokenResponse.from(newTokens);
    }

    private void revoke(RefreshToken storedToken) {
        if (storedToken.isRevoked()) {
            refreshTokenRepository.deleteByUser(storedToken.getUser());
            throw new UnauthorizedException(ErrorCode.STOLEN_TOKEN_DETECTED);
        }

        if (storedToken.isExpired()) {
            refreshTokenRepository.delete(storedToken);
            throw new UnauthorizedException(ErrorCode.EXPIRED_TOKEN);
        }

        storedToken.revoke();
    }
}
