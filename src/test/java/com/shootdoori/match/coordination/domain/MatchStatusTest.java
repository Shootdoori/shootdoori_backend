package com.shootdoori.match.coordination.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class MatchStatusTest {

    @ParameterizedTest
    @EnumSource(value = MatchStatus.class, names = "WAITING")
    @DisplayName("대기중 상태는 매칭이 가능하다")
    void validateMatchable_success(MatchStatus status) {
        // when & then
        assertThatCode(() -> status.validateMatchable()).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @EnumSource(value = MatchStatus.class, names = {"MATCHED", "CANCELED", "FINISHED"})
    @DisplayName("매치됨/취소됨/종료됨 상태는 매칭이 불가능하다")
    void validateMatchable_fail(MatchStatus status) {
        // when & then
        assertThatThrownBy(() -> status.validateMatchable()).isInstanceOf(IllegalStateException.class);
    }

    @ParameterizedTest
    @EnumSource(value = MatchStatus.class, names = {"WAITING", "MATCHED"})
    @DisplayName("대기중/매치됨 상태는 취소가 가능하다")
    void validateCancelable_success(MatchStatus status) {
        // when & then
        assertThatCode(() -> status.validateCancelable()).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @EnumSource(value = MatchStatus.class, names = {"CANCELED", "FINISHED"})
    @DisplayName("취소됨/종료됨 상태는 취소가 불가능하다")
    void validateCancelable_fail(MatchStatus status) {
        // when & then
        assertThatThrownBy(() -> status.validateCancelable()).isInstanceOf(IllegalStateException.class);
    }

    @ParameterizedTest
    @EnumSource(value = MatchStatus.class, names = "MATCHED")
    @DisplayName("매치됨 상태는 종료가 가능하다")
    void validateFinishable_success(MatchStatus status) {
        // when & then
        assertThatCode(() -> status.validateFinishable()).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @EnumSource(value = MatchStatus.class, names = {"WAITING", "CANCELED", "FINISHED"})
    @DisplayName("대기중/취소됨/종료됨 상태는 종료가 불가능하다")
    void validateFinishable_fail(MatchStatus status) {
        // when & then
        assertThatThrownBy(() -> status.validateFinishable()).isInstanceOf(IllegalStateException.class);
    }
}