package com.shootdoori.match.team.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TeamTest {

    private Team team;

    @BeforeEach
    void setUp() {
        team = Team.of("감자빵 FC", "강원대학교", TeamType.CENTRAL_CLUB, "주 2회 축구합니다.");
    }

    @Test
    @DisplayName("팀 정보 수정 테스트")
    void changeTeamInfo() {
        // given
        String newName = "닭갈비 FC";
        String newUniversity = "강원대학교";
        String newDescription = "변경된 설명란";

        // when
        team.changeTeamInfo(newName, newUniversity, newDescription);

        // then
        assertThat(team.getTeamName().teamName()).isEqualTo(newName);
        assertThat(team.getUniversityName().universityName()).isEqualTo(newUniversity);
        assertThat(team.getDescription().description()).isEqualTo(newDescription);
    }
}