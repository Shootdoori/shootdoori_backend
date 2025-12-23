package com.shootdoori.match.user.domain.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class UserNameTest {

    @Test
    @DisplayName("팩토리 메서드 of 테스트")
    void of() {
        // given
        String name = "정상수";

        // when
        UserName userName = UserName.of(name);

        // then
        assertThat(userName).isNotNull();
        assertThat(userName.value()).isEqualTo(name);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("이름 null 또는 빈 문자열 테스트")
    void of_Fail_By_Blank_or_Null(String name) {
        assertThatThrownBy(() -> UserName.of(name))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이름은 필수 입력 값입니다.");
    }

    @Test
    @DisplayName("이름 2자 미만")
    void of_Fail_By_Length_Under() {
        // given
        String name = "김";

        // when
        assertThatThrownBy(() -> UserName.of(name))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이름은 2자 이상 100자 이하로 입력해주세요.");
    }

    @Test
    @DisplayName("이름 100자 초과")
    void of_Fail_By_Length_Over() {
        // given
        String name = "정".repeat(101);

        // when
        assertThatThrownBy(() -> UserName.of(name))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이름은 2자 이상 100자 이하로 입력해주세요.");
    }
}
