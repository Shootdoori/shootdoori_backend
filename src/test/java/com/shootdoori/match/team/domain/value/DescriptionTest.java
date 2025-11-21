package com.shootdoori.match.team.domain.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Description VO 테스트")
class DescriptionTest {

    @Test
    @DisplayName("정상적인 설명 생성 테스트 (1000자 이하)")
    void of_Success() {
        // given
        String validDescription = "이것은 정상적인 설명입니다.";
        
        // when
        Description description = Description.of(validDescription);
        
        // then
        assertThat(description.description()).isEqualTo(validDescription);
    }
    
    @Test
    @DisplayName("설명이 1000자를 초과하면 예외 발생")
    void of_Fail_MaxLengthExceeded() {
        // given
        String longDescription = "a".repeat(1001);
        
        // when & then
        assertThatThrownBy(() -> Description.of(longDescription))
            .isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    @DisplayName("설명이 null이면 null로 저장된다")
    void of_Null() {
        // given
        String nullDescription = null;
        
        // when
        Description description = Description.of(nullDescription);
        
        // then
        assertThat(description.description()).isEqualTo(null);
    }

    @Test
    @DisplayName("설명이 빈 문자열이거나 공백이면 null로 저장된다")
    void of_Blank_To_Null() {
        // given
        String blankDescription = "   ";

        // when
        Description description = Description.of(blankDescription);

        // then
        assertThat(description.description()).isEqualTo(null);
    }
}
