package com.shootdoori.match.user.util;

import com.shootdoori.match.user.dto.AuthToken;
import com.shootdoori.match.user.domain.DeviceType;
import com.shootdoori.match.user.domain.RefreshToken;
import com.shootdoori.match.user.domain.User;
import io.jsonwebtoken.Claims;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.springframework.stereotype.Component;

@Component
public class TokenIssuer {

    private final JwtUtil jwtUtil;

    public TokenIssuer(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public GeneratedToken issue(User user, DeviceType deviceType, String userAgent) {
        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshTokenValue = jwtUtil.generateRefreshToken(user);

        Claims claims = jwtUtil.getClaims(refreshTokenValue);
        String tokenId = claims.getId();
        LocalDateTime expiryDate = claims.getExpiration().toInstant()
            .atZone(ZoneId.systemDefault()).toLocalDateTime();

        AuthToken authToken = new AuthToken(
            accessToken,
            refreshTokenValue,
            jwtUtil.getAccessTokenValidityInMilliseconds(),
            jwtUtil.getRefreshTokenValidityInMilliseconds()
        );
        RefreshToken refreshToken = new RefreshToken(tokenId, user, deviceType, userAgent,
            expiryDate);

        return new GeneratedToken(authToken, refreshToken);
    }
}
