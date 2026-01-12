package com.shootdoori.match.user.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.shootdoori.match.user.domain.DeviceType;
import com.shootdoori.match.user.domain.User;
import com.shootdoori.match.user.dto.AuthToken;
import io.jsonwebtoken.Claims;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("TokenIssuer 테스트")
class TokenIssuerTest {
    @Mock
    private JwtUtil jwtUtil;

    private TokenIssuer tokenIssuer;

    @BeforeEach
    void setUp() {
        tokenIssuer = new TokenIssuer(jwtUtil);
    }

    @Test
    void issue() {
        // given
        User user = mock(User.class);
        DeviceType deviceType = DeviceType.IOS;
        String userAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS...)";

        String expectedAccessToken = "access-token";
        String expectedRefreshToken = "refresh-token";

        long expectedAccessTime = 3600L;
        long expectedRefreshTime = 86400L;

        given(jwtUtil.generateAccessToken(any(User.class))).willReturn(expectedAccessToken);
        given(jwtUtil.generateRefreshToken(any(User.class))).willReturn(expectedRefreshToken);

        given(jwtUtil.getAccessTokenValidityInMilliseconds()).willReturn(expectedAccessTime);
        given(jwtUtil.getRefreshTokenValidityInMilliseconds()).willReturn(expectedRefreshTime);

        Claims claims = mock(Claims.class);
        String refreshTokenId = "refresh-token-id";
        Date expireDate = new Date(System.currentTimeMillis() + (expectedRefreshTime * 1000));
        
        given(claims.getId()).willReturn(refreshTokenId);
        given(claims.getExpiration()).willReturn(expireDate);
        given(jwtUtil.getClaims(any())).willReturn(claims);

        // when
        GeneratedToken generatedToken = tokenIssuer.issue(user, deviceType, userAgent);

        // then
        assertThat(generatedToken).isNotNull();
        assertThat(generatedToken.authToken().accessToken()).isEqualTo(expectedAccessToken);
        assertThat(generatedToken.authToken().refreshToken()).isEqualTo(expectedRefreshToken);
        assertThat(generatedToken.refreshToken().getId()).isEqualTo(refreshTokenId);
    }
}