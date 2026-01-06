package com.shootdoori.match.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.shootdoori.match.entity.common.Position;
import com.shootdoori.match.entity.common.SkillLevel;
import com.shootdoori.match.exception.common.UnauthorizedException;
import com.shootdoori.match.team.domain.value.UniversityName;
import com.shootdoori.match.user.domain.DeviceType;
import com.shootdoori.match.user.domain.RefreshToken;
import com.shootdoori.match.user.domain.User;
import com.shootdoori.match.user.domain.value.Bio;
import com.shootdoori.match.user.domain.value.Department;
import com.shootdoori.match.user.domain.value.Email;
import com.shootdoori.match.user.domain.value.KakaoTalkId;
import com.shootdoori.match.user.domain.value.Password;
import com.shootdoori.match.user.domain.value.StudentYear;
import com.shootdoori.match.user.domain.value.UserName;
import com.shootdoori.match.user.dto.AuthToken;
import com.shootdoori.match.user.dto.AuthTokenResponse;
import com.shootdoori.match.user.dto.TokenRefreshRequest;
import com.shootdoori.match.user.repository.RefreshTokenRepository;
import com.shootdoori.match.user.util.GeneratedToken;
import com.shootdoori.match.user.util.JwtUtil;
import com.shootdoori.match.user.util.TokenIssuer;
import io.jsonwebtoken.Claims;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("TokenRefreshService 테스트")
class TokenRefreshServiceTest {

    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private RefreshTokenRepository refreshTokenRepository;
    @Mock
    private TokenIssuer tokenIssuer;

    private TokenRefreshService tokenRefreshService;

    @BeforeEach
    void setUp() {
        tokenRefreshService = new TokenRefreshService(jwtUtil, refreshTokenRepository, tokenIssuer);
    }

    @Test
    @DisplayName("토큰 갱신 성공")
    void refresh_success() {
        // given
        String refreshTokenStr = "validRefreshToken";
        TokenRefreshRequest request = new TokenRefreshRequest(refreshTokenStr);
        String tokenId = "tokenId";

        Claims claims = mock(Claims.class);
        given(claims.getId()).willReturn(tokenId);

        User user = createUser();
        RefreshToken storedToken = createRefreshToken(user, false, LocalDateTime.now().plusDays(1));

        given(jwtUtil.getClaims(refreshTokenStr)).willReturn(claims);
        given(refreshTokenRepository.findById(tokenId)).willReturn(Optional.of(storedToken));

        GeneratedToken newGeneratedToken = createGeneratedToken(user);
        given(tokenIssuer.issue(any(User.class), any(DeviceType.class), any())).willReturn(
            newGeneratedToken);

        // when
        AuthTokenResponse response = tokenRefreshService.refresh(request);

        // then
        assertThat(response).isNotNull();
        assertThat(storedToken.isRevoked()).isTrue();
        verify(refreshTokenRepository).save(any(RefreshToken.class));
    }

    @Test
    @DisplayName("존재하지 않는 토큰으로 갱신 시도 시 예외 발생")
    void refresh_fail_invalid_token() {
        // given
        String refreshTokenStr = "invalidRefreshToken";
        TokenRefreshRequest request = new TokenRefreshRequest(refreshTokenStr);
        String tokenId = "tokenId";

        Claims claims = mock(Claims.class);
        given(jwtUtil.getClaims(refreshTokenStr)).willReturn(claims);
        given(claims.getId()).willReturn(tokenId);

        given(refreshTokenRepository.findById(tokenId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> tokenRefreshService.refresh(request))
            .isInstanceOf(UnauthorizedException.class);
    }

    @Test
    @DisplayName("이미 사용된(Revoked) 토큰으로 갱신 시도 시 예외 발생 및 전체 삭제")
    void refresh_fail_revoked_token() {
        // given
        String refreshTokenStr = "revokedRefreshToken";
        TokenRefreshRequest request = new TokenRefreshRequest(refreshTokenStr);
        String tokenId = "tokenId";

        Claims claims = mock(Claims.class);
        given(claims.getId()).willReturn(tokenId);

        User user = createUser();
        RefreshToken storedToken = createRefreshToken(user, true, LocalDateTime.now());

        given(jwtUtil.getClaims(refreshTokenStr)).willReturn(claims);
        given(refreshTokenRepository.findById(tokenId)).willReturn(Optional.of(storedToken));

        // when & then
        assertThatThrownBy(() -> tokenRefreshService.refresh(request))
            .isInstanceOf(UnauthorizedException.class);
        verify(refreshTokenRepository).deleteByUser(user);
    }

    @Test
    @DisplayName("만료된 토큰으로 갱신 시도 시 예외 발생 및 삭제")
    void refresh_fail_expired_token() {
        // given
        String refreshTokenStr = "expiredRefreshToken";
        TokenRefreshRequest request = new TokenRefreshRequest(refreshTokenStr);
        String tokenId = "tokenId";

        Claims claims = mock(Claims.class);
        given(claims.getId()).willReturn(tokenId);

        User user = createUser();
        RefreshToken storedToken = createRefreshToken(user, false,
            LocalDateTime.now().minusDays(1));

        given(jwtUtil.getClaims(refreshTokenStr)).willReturn(claims);
        given(refreshTokenRepository.findById(tokenId)).willReturn(Optional.of(storedToken));

        // when & then
        assertThatThrownBy(() -> tokenRefreshService.refresh(request))
            .isInstanceOf(UnauthorizedException.class);

        verify(refreshTokenRepository).delete(storedToken);
    }

    private User createUser() {
        return new User(
            new UserName("정상수"),
            new Email("gamza@kangwon.ac.kr"),
            new Password("encodedPassword"),
            Position.FW,
            SkillLevel.AMATEUR,
            new KakaoTalkId("kakao123"),
            new UniversityName("강원대학교"),
            new Department("컴퓨터공학과"),
            new StudentYear("23"),
            new Bio("안녕하세요")
        );
    }

    private RefreshToken createRefreshToken(User user, boolean revoked, LocalDateTime expiryDate) {
        RefreshToken refreshToken = new RefreshToken("tokenId", user, DeviceType.WEB, "userAgent",
            expiryDate);
        if (revoked) {
            refreshToken.revoke();
        }
        return refreshToken;
    }

    private GeneratedToken createGeneratedToken(User user) {
        AuthToken authToken = new AuthToken("newAccessToken", "newRefreshToken", 3600L, 86400L);
        RefreshToken refreshToken = new RefreshToken("newTokenId", user, DeviceType.WEB,
            "userAgent", LocalDateTime.now().plusDays(1));
        return new GeneratedToken(authToken, refreshToken);
    }
}
