package com.shootdoori.match.entity.user;

import com.shootdoori.match.entity.user.dto.AuthToken;

public record GeneratedToken(
    AuthToken authToken,
    RefreshToken refreshToken
) {

}
