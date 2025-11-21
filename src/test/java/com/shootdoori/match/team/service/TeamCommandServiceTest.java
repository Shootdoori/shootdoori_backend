package com.shootdoori.match.team.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.shootdoori.match.team.domain.Team;
import com.shootdoori.match.team.domain.TeamType;
import com.shootdoori.match.team.dto.TeamDetailResponseDto;
import com.shootdoori.match.team.dto.TeamRequestDto;
import com.shootdoori.match.team.mapper.TeamMapper;
import com.shootdoori.match.team.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("TeamCommandService 테스트")
class TeamCommandServiceTest {

    @Mock
    private TeamQueryService teamQueryService;
    @Mock
    private TeamMemberQueryService teamMemberQueryService;
    @Mock
    private TeamRepository teamRepository;
    @Mock
    private TeamMapper teamMapper;

    private TeamCommandService teamCommandService;

    @BeforeEach
    void setUp() {
        teamCommandService = new TeamCommandService(teamQueryService, teamMemberQueryService,
            teamRepository, teamMapper);
    }

    @Test
    @DisplayName("팀 정보 수정 성공 테스트")
    void update() {
        // given
        Long teamId = 1L;
        Long userId = 1L;
        TeamRequestDto requestDto = new TeamRequestDto("수정된이름", "수정된설명", "수정된대학", "과동아리");

        Team team = Team.of("원래이름", "원래대학", TeamType.CENTRAL_CLUB, "원래설명");
        TeamDetailResponseDto expectedResponse = new TeamDetailResponseDto(teamId, "수정된이름", "수정된설명", "수정된대학", "과동아리", "20251121");

        given(teamQueryService.findByIdForEntity(teamId)).willReturn(team);
        given(teamMapper.toTeamDetailResponse(team)).willReturn(expectedResponse);

        // when
        teamCommandService.update(teamId, requestDto, userId);

        // then
        assertThat(team.getTeamName().teamName()).isEqualTo("수정된이름");
        assertThat(team.getUniversityName().universityName()).isEqualTo("수정된대학");
        assertThat(team.getDescription().description()).isEqualTo("수정된설명");
    }
}