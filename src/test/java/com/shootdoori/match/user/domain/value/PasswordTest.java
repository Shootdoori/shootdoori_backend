package com.shootdoori.match.user.domain.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class PasswordTest {

    @Test
    @DisplayName("팩토리 메서드 of 테스트")
    void of() {
        // given
        String encodedPassword = "encodedPassword123";

        // when
        Password password = Password.of(encodedPassword);

        // then
        assertThat(password).isNotNull();
        assertThat(password.value()).isEqualTo(encodedPassword);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("인코딩된 패스워드 null 또는 빈 문자열 테스트")
    void of_Fail_By_Blank_or_Null(String encodedPassword) {
        assertThatThrownBy(() -> Password.of(encodedPassword))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("인코딩된 패스워드는 비어 있을 수 없습니다.");
    }

    @Test
    @DisplayName("validateRaw - 정상 패스워드 검증")
    void validateRaw() {
        // given
        String rawPassword = "Password123!";

        // when & then
        Password.validateRaw(rawPassword);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("validateRaw - null 또는 빈 문자열 테스트")
    void validateRaw_Fail_By_Blank_or_Null(String rawPassword) {
        assertThatThrownBy(() -> Password.validateRaw(rawPassword))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("패스워드는 필수 입력 값입니다.");
    }

    @Test
    @DisplayName("validateRaw - 길이 미달 (8자 미만)")
    void validateRaw_Fail_By_Length_Under() {
        // given
        String rawPassword = "Pass1!";

        // when & then
        assertThatThrownBy(() -> Password.validateRaw(rawPassword))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("비밀번호는 8~20자여야 합니다.");
    }

    @Test
    @DisplayName("validateRaw - 길이 초과 (20자 초과)")
    void validateRaw_Fail_By_Length_Over() {
        // given
        String rawPassword = "Password123!Password123!";

        // when & then
        assertThatThrownBy(() -> Password.validateRaw(rawPassword))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("비밀번호는 8~20자여야 합니다.");
    }

    @Test
    @DisplayName("validateRaw - 형식 불일치")
    void validateRaw_Fail_By_Format() {
        // given
        String rawPassword = "Password123";

        // when & then
        assertThatThrownBy(() -> Password.validateRaw(rawPassword))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("비밀번호는 8자 이상 20자 이하로");
    }
}

