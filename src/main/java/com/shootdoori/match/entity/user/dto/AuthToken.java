package com.shootdoori.match.entity.user.dto;

public record AuthToken(
    String accessToken,
    String refreshToken,
    long accessTokenExpiresIn,
    long refreshTokenExpiresIn
) {
}
