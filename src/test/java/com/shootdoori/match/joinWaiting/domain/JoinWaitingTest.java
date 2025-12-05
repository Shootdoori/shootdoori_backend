package com.shootdoori.match.joinWaiting.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.shootdoori.match.exception.common.NoPermissionException;
import com.shootdoori.match.exception.domain.joinwaiting.JoinWaitingNotPendingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JoinWaitingTest {

    private JoinWaiting joinWaiting;

    @BeforeEach
    void setUp() {
        joinWaiting = JoinWaiting.of(1L, 1L, "열심히 하겠습니다!", JoinWaitingType.MEMBER);
    }

    @Test
    @DisplayName("가입 대기 승인")
    void approve() {
        // when
        joinWaiting.approve(1L, "환영합니다!");

        // then
        assertThat(joinWaiting.getStatus()).isEqualTo(JoinWaitingStatus.APPROVED);
    }

    @Test
    @DisplayName("가입 대기 거절")
    void reject() {
        // when
        joinWaiting.reject(1L, "죄송합니다!");

        // then
        assertThat(joinWaiting.getStatus()).isEqualTo(JoinWaitingStatus.REJECTED);
    }

    @Test
    @DisplayName("가입 대기 취소")
    void cancel() {
        // when
        joinWaiting.cancel(1L, "개인 사정으로 취소합니다.");

        // then
        assertThat(joinWaiting.getStatus()).isEqualTo(JoinWaitingStatus.CANCELED);
    }

    @Test
    @DisplayName("신청자 검증 성공")
    void validateApplicant() {
        // given
        Long applicantId = 1L;

        // when & then
        joinWaiting.validateApplicant(applicantId);
    }

    @Test
    @DisplayName("대기 상태가 아닐 때 승인하면 예외 발생")
    void approve_fail_not_pending() {
        // given
        joinWaiting.approve(1L, "이미 승인됨");

        // when & then
        assertThatThrownBy(() -> joinWaiting.approve(1L, "승인 안됨"))
            .isInstanceOf(JoinWaitingNotPendingException.class);
    }

    @Test
    @DisplayName("대기 상태가 아닐 때 거절하면 예외 발생")
    void reject_fail_not_pending() {
        // given
        joinWaiting.approve(1L, "이미 승인됨");

        // when & then
        assertThatThrownBy(() -> joinWaiting.reject(1L, "거절 안됨"))
            .isInstanceOf(JoinWaitingNotPendingException.class);
    }

    @Test
    @DisplayName("대기 상태가 아닐 때 취소하면 예외 발생")
    void cancel_fail_not_pending() {
        // given
        joinWaiting.approve(1L, "이미 승인됨");

        // when & then
        assertThatThrownBy(() -> joinWaiting.cancel(1L, "취소 안됨"))
            .isInstanceOf(JoinWaitingNotPendingException.class);
    }

    @Test
    @DisplayName("신청자 본인이 아니면 예외 발생")
    void validateApplicant_fail() {
        // given
        Long otherUserId = 999L;

        // when & then
        assertThatThrownBy(() -> joinWaiting.validateApplicant(otherUserId))
            .isInstanceOf(NoPermissionException.class);
    }
}