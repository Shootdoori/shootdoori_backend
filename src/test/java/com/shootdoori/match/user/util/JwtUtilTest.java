package com.shootdoori.match.user.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.shootdoori.match.user.domain.User;
import com.shootdoori.match.user.domain.value.Email;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;

class JwtUtilTest {
    private JwtUtil jwtUtil;

    private final String secretKey = "testSecretKeyForTesttestSecretKeyForTest";
    private final long accessTokenValidity = 3600L;
    private final long refreshTokenValidity = 86400L;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil(secretKey, accessTokenValidity, refreshTokenValidity);
    }

    @Test
    @DisplayName("액세스 토큰 생성 및 파싱 확인")
    void generateAccessToken() {
        // given
        User user = mock(User.class);
        given(user.getId()).willReturn(1L);
        given(user.getEmail()).willReturn(Email.of("gamza@kangwon.ac.kr"));

        // when
        String token = jwtUtil.generateAccessToken(user);

        // then
        assertThat(token).isNotNull();
        assertThat(jwtUtil.validateToken(token)).isTrue();
        assertThat(jwtUtil.getUserId(token)).isEqualTo("1");
    }

    @Test
    @DisplayName("리프레시 토큰 생성 및 파싱 확인")
    void generateRefreshToken() {
        // given
        User user = mock(User.class);
        given(user.getId()).willReturn(1L);

        // when
        String token = jwtUtil.generateRefreshToken(user);

        // then
        assertThat(token).isNotNull();
        assertThat(jwtUtil.validateToken(token)).isTrue();
        assertThat(jwtUtil.getUserId(token)).isEqualTo("1");
    }

    @Test
    @DisplayName("HTTP 요청 헤더에서 토큰 추출")
    void extractToken_from_header() {
        // given
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        httpServletRequest.addHeader(HttpHeaders.AUTHORIZATION, "Bearer testHeaderToken!!!");

        // when
        String extractedFromHeader = jwtUtil.extractToken(httpServletRequest);

        // then
        assertThat(extractedFromHeader).isEqualTo("testHeaderToken!!!");
    }

    @Test
    @DisplayName("HTTP 요청 쿠키에서 토큰 추출")
    void extractToken_from_cookie() {
        // given
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        Cookie cookie = new Cookie("accessToken", "Bearer%20testHeaderToken!!!");
        httpServletRequest.setCookies(cookie);

        // when
        String extractedFromHeader = jwtUtil.extractToken(httpServletRequest);

        // then
        assertThat(extractedFromHeader).isEqualTo("testHeaderToken!!!");
    }

    @Test
    @DisplayName("정상 토큰은 검증 성공(true)을 반환")
    void success_validateToken() {
        // given
        User user = mock(User.class);
        given(user.getId()).willReturn(1L);
        given(user.getEmail()).willReturn(Email.of("gamza@kangwon.ac.kr"));

        String validToken = jwtUtil.generateAccessToken(user);

        // when & then
        assertThat(jwtUtil.validateToken(validToken)).isTrue();
    }


    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
        "Bearer fail",
        "invalid-token"
    })
    @DisplayName("유효하지 않은 토큰은 검증 실패를 반환")
    void fail_validateToken(String invalidToken) {
        // when
        boolean validateResult = jwtUtil.validateToken(invalidToken);

        // then
        assertThat(validateResult).isFalse();
    }
}
