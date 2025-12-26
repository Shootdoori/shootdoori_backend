package com.shootdoori.match.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.shootdoori.match.dto.LoginRequest;
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
import com.shootdoori.match.user.repository.RefreshTokenRepository;
import com.shootdoori.match.user.util.GeneratedToken;
import com.shootdoori.match.user.util.TokenIssuer;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthService 테스트")
class AuthServiceTest {

    @Mock
    private UserQueryService userQueryService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private TokenIssuer tokenIssuer;
    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService(userQueryService, passwordEncoder, tokenIssuer,
            refreshTokenRepository);
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void login() {
        // given
        LoginRequest request = new LoginRequest("gamza@kangwon.ac.kr", "Password123!!");
        User user = createUser();
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36";
        GeneratedToken generatedToken = createGeneratedToken(user);

        given(userQueryService.findByEmail(request.email())).willReturn(user);
        given(passwordEncoder.matches(request.password(), user.getPassword())).willReturn(true);
        given(tokenIssuer.issue(any(User.class), any(DeviceType.class), anyString()))
            .willReturn(generatedToken);

        // when
        AuthTokenResponse response = authService.login(request, userAgent);

        // then
        assertThat(response).isNotNull();
        assertThat(response.accessToken()).isEqualTo("accessToken");
        verify(refreshTokenRepository).save(any(RefreshToken.class));
    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호 불일치")
    void login_fail_password_mismatch() {
        // given
        LoginRequest request = new LoginRequest("gamza@kangwon.ac.kr", "WrongPassword");
        User user = createUser();
        String userAgent = "Mozilla/5.0";

        given(userQueryService.findByEmail(request.email())).willReturn(user);
        given(passwordEncoder.matches(request.password(), user.getPassword())).willReturn(false);

        // when & then
        assertThatThrownBy(() -> authService.login(request, userAgent))
            .isInstanceOf(UnauthorizedException.class);
    }

    private User createUser() {
        return new User(
            UserName.of("정상수"),
            Email.of("gamza@kangwon.ac.kr"),
            Password.of("encodedPassword"),
            Position.FW,
            SkillLevel.AMATEUR,
            KakaoTalkId.of("kakao123"),
            UniversityName.of("강원대학교"),
            Department.of("컴퓨터공학과"),
            StudentYear.of("23"),
            Bio.of("안녕하세요")
        );
    }

    private GeneratedToken createGeneratedToken(User user) {
        AuthToken authToken = new AuthToken("accessToken", "refreshToken", 3600L, 86400L);
        RefreshToken refreshToken = new RefreshToken("refreshTokenId", user, DeviceType.WEB,
            "userAgent", LocalDateTime.now().plusDays(1));
        return new GeneratedToken(authToken, refreshToken);
    }
}
