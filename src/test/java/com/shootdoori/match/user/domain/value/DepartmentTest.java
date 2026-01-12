package com.shootdoori.match.user.domain.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class DepartmentTest {

    @Test
    @DisplayName("팩토리 메서드 of 테스트")
    void of() {
        // given
        String departmentName = "영업부서";

        // when
        Department department = Department.of(departmentName);

        // then
        assertThat(department).isNotNull();
    }
    
    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("학과 이름 null 또는 빈 문자열 테스트")
    void of_Fail_By_Blank_or_Null(String departmentName) {
        // when
        assertThatThrownBy(() -> Department.of(departmentName)).isInstanceOf(
            IllegalArgumentException.class);
    }

    @Test
    @DisplayName("학과 이름 100자 초과")
    void of_Fail_By_Length_Over() {
        // given
        String departmentName = "*".repeat(101);

        // when
        assertThatThrownBy(() -> Department.of(departmentName)).isInstanceOf(
            IllegalArgumentException.class);
    }
}
