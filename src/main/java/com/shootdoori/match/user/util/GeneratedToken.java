package com.shootdoori.match.user.util;

import com.shootdoori.match.user.domain.RefreshToken;
import com.shootdoori.match.user.dto.AuthToken;

public record GeneratedToken(
    AuthToken authToken,
    RefreshToken refreshToken
) {

}
