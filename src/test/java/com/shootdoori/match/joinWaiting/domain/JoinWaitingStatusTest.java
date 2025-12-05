package com.shootdoori.match.joinWaiting.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class JoinWaitingStatusTest {

    @ParameterizedTest
    @DisplayName("displayName 테스트")
    @CsvSource({
        "대기중, PENDING",
        "승인됨, APPROVED",
        "거절됨, REJECTED",
        "취소됨, CANCELED"
    })
    void fromDisplayName(String displayName, JoinWaitingStatus expected) {
        // when
        JoinWaitingStatus actual = JoinWaitingStatus.fromDisplayName(displayName);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("displayName 예외 테스트")
    void fromDisplayName_throwsException() {
        // given
        String wrongDisplayName = "예외가 펑";

        // when & then
        assertThatThrownBy(() -> JoinWaitingStatus.fromDisplayName(wrongDisplayName))
            .isInstanceOf(IllegalArgumentException.class);
    }
}