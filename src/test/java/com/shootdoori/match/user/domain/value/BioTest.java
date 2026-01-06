package com.shootdoori.match.user.domain.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BioTest {

    @Test
    @DisplayName("생성자 테스트 - 정상")
    void constructor() {
        // given
        String bioStr = "안녕하세요. 정상수입니다.";

        // when
        Bio bio = new Bio(bioStr);

        // then
        assertThat(bio).isNotNull();
        assertThat(bio.value()).isEqualTo(bioStr);
    }

    @Test
    @DisplayName("생성자 테스트 - null 입력 가능")
    void constructor_Null() {
        // given
        String bioStr = null;

        // when
        Bio bio = new Bio(bioStr);

        // then
        assertThat(bio).isNotNull();
        assertThat(bio.value()).isNull();
    }

    @Test
    @DisplayName("자기소개 500자 초과")
    void constructor_Fail_By_Length_Over() {
        // given
        String bioStr = "a".repeat(501);

        // when
        assertThatThrownBy(() -> new Bio(bioStr))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("자기소개는 500자를 초과할 수 없습니다.");
    }
}
