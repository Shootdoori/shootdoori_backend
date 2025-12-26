package com.shootdoori.match.user.domain.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class EmailTest {

    @Test
    @DisplayName("팩토리 메서드 of 테스트")
    void of() {
        // given
        String emailStr = "gamza@kangwon.ac.kr";

        // when
        Email email = Email.of(emailStr);

        // then
        assertThat(email).isNotNull();
        assertThat(email.value()).isEqualTo(emailStr);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("이메일 null 또는 빈 문자열 테스트")
    void of_Fail_By_Blank_or_Null(String emailStr) {
        assertThatThrownBy(() -> Email.of(emailStr))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이메일은 필수 입력 값입니다.");
    }

    @Test
    @DisplayName("이메일 255자 초과")
    void of_Fail_By_Length_Over() {
        // given
        String prefix = "a".repeat(250);
        String emailStr = prefix + "@kangwon.ac.kr";

        // when
        assertThatThrownBy(() -> Email.of(emailStr))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이메일은 255자를 초과할 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"gamza@naver.com", "gamza@gmail.com", "gamzakangwon.ac.kr"})
    @DisplayName("학교 이메일 도메인 불일치")
    void of_Fail_By_Invalid_Format(String emailStr) {
        assertThatThrownBy(() -> Email.of(emailStr))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("학교 이메일은 'ac.kr' 도메인이어야 합니다.");
    }
}

