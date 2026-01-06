package com.shootdoori.match.user.dto;

import com.shootdoori.match.user.util.GeneratedToken;

public record AuthTokenResponse(
        String accessToken,
        String refreshToken,
        long accessTokenExpiresIn,
        long refreshTokenExpiresIn
) {

    public static AuthTokenResponse from(GeneratedToken generatedToken) {
        AuthToken authToken = generatedToken.authToken();
        return new AuthTokenResponse(
            authToken.accessToken(),
            authToken.refreshToken(),
            authToken.accessTokenExpiresIn(),
            authToken.refreshTokenExpiresIn()
        );
    }
}