package com.shootdoori.match.team.domain.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Description VO 테스트")
class DescriptionTest {

    @Test
    @DisplayName("정상적인 설명 생성 테스트 (1000자 이하)")
    void constructor_Success() {
        // given
        String validDescription = "이것은 정상적인 설명입니다.";
        
        // when
        Description description = new Description(validDescription);
        
        // then
        assertThat(description.getDescription()).isEqualTo(validDescription);
    }
    
    @Test
    @DisplayName("설명이 1000자를 초과하면 예외 발생")
    void constructor_Fail_MaxLengthExceeded() {
        // given
        String longDescription = "a".repeat(1001);
        
        // when & then
        assertThatThrownBy(() -> new Description(longDescription))
            .isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    @DisplayName("설명이 null이면 null로 저장된다")
    void constructor_Null() {
        // given
        String nullDescription = null;
        
        // when
        Description description = new Description(nullDescription);
        
        // then
        assertThat(description.getDescription()).isEqualTo(null);
    }

    @Test
    @DisplayName("설명이 빈 문자열이거나 공백이면 null로 저장된다")
    void constructor_Blank_To_Null() {
        // given
        String blankDescription = "   ";

        // when
        Description description = new Description(blankDescription);

        // then
        assertThat(description.getDescription()).isEqualTo(null);
    }
}
