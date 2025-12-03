package com.shootdoori.match.team.domain.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.shootdoori.match.exception.domain.team.TeamNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("TeamName VO 테스트")
class TeamNameTest {

    @Test
    @DisplayName("정상적인 팀 이름 생성 테스트")
    void of_Success() {
        // given
        String validName = "정상적인 팀이름";

        // when
        TeamName teamName = TeamName.of(validName);

        // then
        assertThat(teamName.getTeamName()).isEqualTo(validName);
    }

    @Test
    @DisplayName("팀 이름이 100자를 초과하면 예외 발생")
    void of_Fail_MaxLengthExceeded() {
        // given
        String longName = "a".repeat(101);

        // when & then
        assertThatThrownBy(() -> TeamName.of(longName))
            .isInstanceOf(TeamNameException.class);
    }

    @Test
    @DisplayName("팀 이름이 null이면 예외 발생")
    void of_Fail_Null() {
        // given
        String nullName = null;

        // when & then
        assertThatThrownBy(() -> TeamName.of(nullName))
            .isInstanceOf(TeamNameException.class);
    }

    @Test
    @DisplayName("팀 이름이 빈 문자열이거나 공백이면 예외 발생")
    void of_Fail_Blank() {
        // given
        String blankName = "   ";

        // when & then
        assertThatThrownBy(() -> TeamName.of(blankName))
            .isInstanceOf(TeamNameException.class);
    }
}
