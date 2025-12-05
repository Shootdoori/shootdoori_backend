package com.shootdoori.match.util;

import org.springframework.stereotype.Component;

@Component
public class TokenIssuer {

    private final JwtUtil jwtUtil;

    public TokenIssuer(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

//    public AuthToken issue(User user, DeviceType deviceType, String userAgent) {
//        String accessToken = jwtUtil.generateAccessToken(user);
//        String refreshTokenValue = jwtUtil.generateRefreshToken(user);
//
//        Claims claims = jwtUtil.getClaims(refreshTokenValue);
//        String tokenId = claims.getId();
//        LocalDateTime expiryDate = claims.getExpiration().toInstant()
//            .atZone(ZoneId.systemDefault()).toLocalDateTime();
//
//        RefreshToken refreshToken = new RefreshToken(tokenId, user, expiryDate, deviceType, userAgent);
//        refreshTokenRepository.save(refreshToken);
//
//        return new AuthToken(accessToken, refreshTokenValue);
//    }
}