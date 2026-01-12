package com.shootdoori.match.user.domain.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class KakaoTalkIdTest {

    @Test
    @DisplayName("팩토리 메서드 of 테스트")
    void of() {
        // given
        String kakaoId = "kakao123";

        // when
        KakaoTalkId kakaoTalkId = KakaoTalkId.of(kakaoId);

        // then
        assertThat(kakaoTalkId).isNotNull();
        assertThat(kakaoTalkId.value()).isEqualTo(kakaoId);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("카카오톡 ID null 또는 빈 문자열 테스트")
    void of_Fail_By_Blank_or_Null(String kakaoId) {
        assertThatThrownBy(() -> KakaoTalkId.of(kakaoId))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("카카오톡 ID는 필수 입력 값입니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "too_long_kakao_id_12345", "invalid#id", "한글아이디"})
    @DisplayName("카카오톡 ID 형식 불일치 테스트")
    void of_Fail_By_Invalid_Format(String kakaoId) {
        assertThatThrownBy(() -> KakaoTalkId.of(kakaoId))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("ID는 4~20자의 영문, 숫자, 특수문자(-, _, .)만 사용 가능합니다.");
    }
}

