package com.shootdoori.match.joinWaiting.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class JoinWaitingTypeTest {

    @ParameterizedTest
    @DisplayName("displayName 테스트")
    @CsvSource({
        "팀원, MEMBER",
        "용병, MERCENARY"
    })
    void fromDisplayName(String displayName, JoinWaitingType expected) {
        // when
        JoinWaitingType actual = JoinWaitingType.fromDisplayName(displayName);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("displayName 예외 테스트")
    void fromDisplayName_throwsException() {
        // given
        String wrongDisplayName = "예외가 펑";

        // when & then
        assertThatThrownBy(() -> JoinWaitingType.fromDisplayName(wrongDisplayName))
            .isInstanceOf(IllegalArgumentException.class);
    }
}