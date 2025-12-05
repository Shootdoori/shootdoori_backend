package com.shootdoori.match.team.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.shootdoori.match.dto.UpdateTeamMemberRequestDto;
import com.shootdoori.match.exception.common.NoPermissionException;
import com.shootdoori.match.team.domain.Team;
import com.shootdoori.match.team.domain.TeamMember;
import com.shootdoori.match.team.domain.TeamMemberRole;
import com.shootdoori.match.team.domain.TeamType;
import com.shootdoori.match.team.mapper.TeamMemberMapper;
import com.shootdoori.match.team.repository.TeamMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("TeamMemberCommandService 테스트")
class TeamMemberCommandServiceTest {

    @Mock
    private TeamQueryService teamQueryService;
    @Mock
    private TeamMemberQueryService teamMemberQueryService;
    @Mock
    private TeamMemberRepository teamMemberRepository;
    @Mock
    private TeamMemberMapper teamMemberMapper;

    private TeamMemberCommandService teamMemberCommandService;

    private final Long teamId = 1L;

    private final Long leaderUserId = 1L;
    private final Long viceLeaderUserId = 2L;
    private final Long basicUserId = 3L;

    private Team team;

    private TeamMember leaderMember;
    private TeamMember viceLeaderMember;
    private TeamMember basicMember;

    @BeforeEach
    void setUp() {
        teamMemberCommandService = new TeamMemberCommandService(teamQueryService,
            teamMemberQueryService, teamMemberRepository, teamMemberMapper);

        team = Team.of("감자빵 FC", "강원대학교", TeamType.CENTRAL_CLUB, "주 2회 축구합니다.");

        leaderMember = TeamMember.of(team, leaderUserId, TeamMemberRole.LEADER.getDisplayName());
        viceLeaderMember = TeamMember.of(team, viceLeaderUserId,
            TeamMemberRole.VICE_LEADER.getDisplayName());
        basicMember = TeamMember.of(team, basicUserId, TeamMemberRole.MEMBER.getDisplayName());
    }

    @Test
    @DisplayName("update - 회장이 다른 멤버 역할 수정 성공")
    void update_Success() {
        // given
        UpdateTeamMemberRequestDto requestDto = new UpdateTeamMemberRequestDto("부회장");

        given(teamQueryService.findByIdForEntity(teamId)).willReturn(team);
        given(teamMemberQueryService.findByTeamIdAndUserIdForEntity(teamId, leaderUserId))
            .willReturn(leaderMember);
        given(teamMemberQueryService.findByTeamIdAndUserIdForEntity(teamId, basicUserId))
            .willReturn(basicMember);

        // when
        teamMemberCommandService.update(teamId, basicUserId, requestDto, leaderUserId);

        // then
        assertThat(basicMember.getRole().getDisplayName()).isEqualTo("부회장");
    }

    @Test
    @DisplayName("update - 권한이 없는 멤버가 수정 시도 시 예외 발생")
    void update_Fail_NoAuthority() {
        // given
        UpdateTeamMemberRequestDto requestDto = new UpdateTeamMemberRequestDto("부회장");

        given(teamQueryService.findByIdForEntity(teamId)).willReturn(team);
        given(teamMemberQueryService.findByTeamIdAndUserIdForEntity(teamId, basicUserId))
            .willReturn(basicMember);

        // when & then
        assertThatThrownBy(
            () -> teamMemberCommandService.update(teamId, viceLeaderUserId, requestDto,
                basicUserId))
            .isInstanceOf(NoPermissionException.class);
    }

    @Test
    @DisplayName("leave - 멤버 스스로 탈퇴 성공")
    void leave_Success() {
        // given
        given(teamMemberQueryService.findByTeamIdAndUserIdForEntity(teamId, basicUserId))
            .willReturn(basicMember);

        // when
        teamMemberCommandService.leave(teamId, basicUserId);

        // then
        then(teamMemberRepository).should().delete(basicMember);
    }

    @Test
    @DisplayName("kick - 회장이 일반 멤버를 추방 성공")
    void kick_Success() {
        // given
        given(teamMemberQueryService.findByTeamIdAndUserIdForEntity(teamId, leaderUserId))
            .willReturn(leaderMember);
        given(teamMemberQueryService.findByTeamIdAndUserIdForEntity(teamId, basicUserId))
            .willReturn(basicMember);

        // when
        teamMemberCommandService.kick(teamId, basicUserId, leaderUserId);

        // then
        then(teamMemberRepository).should().delete(basicMember);
    }

    @Test
    @DisplayName("kick - 추방 권한이 없는 경우(예: 일반 멤버가 추방 시도) 예외 발생")
    void kick_Fail_NoAuthority() {
        // given
        given(teamMemberQueryService.findByTeamIdAndUserIdForEntity(teamId, basicUserId))
            .willReturn(basicMember);

        // when & then
        assertThatThrownBy(() -> teamMemberCommandService.kick(teamId, leaderUserId, basicUserId))
            .isInstanceOf(NoPermissionException.class);
    }

    @Test
    @DisplayName("delegateRole - 회장이 역할을 위임 성공")
    void delegateRole_Success() {
        // given
        Long targetMemberId = 100L;
        given(teamMemberQueryService.findByTeamIdAndUserIdForEntity(teamId, leaderUserId))
            .willReturn(leaderMember);
        given(teamMemberQueryService.findByIdForEntity(targetMemberId))
            .willReturn(viceLeaderMember);

        // when
        teamMemberCommandService.delegateRole(teamId, leaderUserId, targetMemberId,
            TeamMemberRole.LEADER);

        // then
        assertThat(leaderMember.getRole()).isEqualTo(TeamMemberRole.MEMBER);
        assertThat(viceLeaderMember.getRole()).isEqualTo(TeamMemberRole.LEADER);
    }
}
