package com.shootdoori.match.team.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TeamTypeTest {

    @ParameterizedTest
    @DisplayName("displayName 테스트")
    @CsvSource({
        "중앙동아리, CENTRAL_CLUB",
        "과동아리, DEPARTMENT_CLUB",
        "기타, OTHER"
    })
    void fromDisplayName(String displayName, TeamType expected) {
        // when
        TeamType actual = TeamType.fromDisplayName(displayName);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("displayName 예외 테스트")
    void fromDisplayName_throwsException() {
        // given
        String wrongDisplayName = "예외가 펑";

        // when & then
        assertThatThrownBy(() -> TeamType.fromDisplayName(wrongDisplayName))
            .isInstanceOf(IllegalArgumentException.class);
    }
}