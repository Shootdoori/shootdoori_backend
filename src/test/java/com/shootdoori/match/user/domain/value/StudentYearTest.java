package com.shootdoori.match.user.domain.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class StudentYearTest {

    @Test
    @DisplayName("팩토리 메서드 of 테스트")
    void of() {
        // given
        String year = "23";

        // when
        StudentYear studentYear = StudentYear.of(year);

        // then
        assertThat(studentYear).isNotNull();
        assertThat(studentYear.value()).isEqualTo(year);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("입학년도 null 또는 빈 문자열 테스트")
    void of_Fail_By_Blank_or_Null(String year) {
        assertThatThrownBy(() -> StudentYear.of(year))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("입학년도는 필수 입력 값입니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"2023", "1", "ab", "2a"})
    @DisplayName("입학년도 형식 불일치 (2자리 숫자)")
    void of_Fail_By_Invalid_Format(String year) {
        assertThatThrownBy(() -> StudentYear.of(year))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("입학년도는 2자리 숫자로 입력해주세요. (예: 25)");
    }
}
