package com.shootdoori.match.coordination.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class MatchApplicationStatusTest {

    @ParameterizedTest
    @EnumSource(value = MatchApplicationStatus.class, names = "PENDING")
    @DisplayName("신청 대기중 상태인 경우 예외가 발생하지 않는다")
    void validatePending_success(MatchApplicationStatus matchApplicationStatus) {
        // when & then
        assertThatCode(() -> matchApplicationStatus.validatePending()).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @EnumSource(value = MatchApplicationStatus.class, names = {"ACCEPTED", "REJECTED", "CANCELED"})
    @DisplayName("신청 대기중 상태가 아닌 경우 예외가 발생한다")
    void validatePending_fail(MatchApplicationStatus matchApplicationStatus) {
        // when & then
        assertThatCode(() -> matchApplicationStatus.validatePending()).isInstanceOf(
            IllegalStateException.class);
    }
}
