package com.shootdoori.match.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RefreshTokenTest {

    @Test
    @DisplayName("팩토리 메서드 of 테스트")
    void of() {
        // given
        String id = "refreshTokenId";
        User user = mock(User.class);
        DeviceType deviceType = DeviceType.ANDROID;
        String userAgent = "Mozilla/5.0...";
        LocalDateTime expiryDate = LocalDateTime.now().plusDays(7L);

        // when
        RefreshToken refreshToken = RefreshToken.of(id, user, deviceType, userAgent, expiryDate);

        // then
        assertThat(refreshToken).isNotNull();
    }
}
