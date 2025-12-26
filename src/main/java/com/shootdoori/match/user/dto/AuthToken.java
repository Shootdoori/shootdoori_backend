package com.shootdoori.match.user.dto;

public record AuthToken(
    String accessToken,
    String refreshToken,
    long accessTokenExpiresIn,
    long refreshTokenExpiresIn
) {
}
