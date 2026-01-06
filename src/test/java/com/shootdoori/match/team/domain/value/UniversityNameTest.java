package com.shootdoori.match.team.domain.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("UniversityName VO 테스트")
class UniversityNameTest {

    @Test
    @DisplayName("정상적인 대학교 이름 생성 테스트")
    void constructor_Success() {
        // given
        String validName = "강원대학교";

        // when
        UniversityName universityName = new UniversityName(validName);

        // then
        assertThat(universityName.getUniversityName()).isEqualTo(validName);
    }

    @Test
    @DisplayName("대학교 이름이 100자를 초과하면 예외 발생")
    void constructor_Fail_MaxLengthExceeded() {
        // given
        String longName = "a".repeat(101);

        // when & then
        assertThatThrownBy(() -> new UniversityName(longName))
            .isInstanceOf(IllegalArgumentException.class);
    }
}

