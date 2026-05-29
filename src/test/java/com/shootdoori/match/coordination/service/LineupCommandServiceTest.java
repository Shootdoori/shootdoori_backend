package com.shootdoori.match.coordination.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import com.shootdoori.match.coordination.domain.Lineup;
import com.shootdoori.match.coordination.repository.LineupRepository;
import com.shootdoori.match.dto.LineupMemberRequestDto;
import com.shootdoori.match.dto.LineupMemberResponseDto;
import com.shootdoori.match.entity.common.Position;
import com.shootdoori.match.exception.common.CreationFailException;
import com.shootdoori.match.exception.common.NoPermissionException;
import com.shootdoori.match.exception.common.NotFoundException;
import com.shootdoori.match.team.domain.TeamMember;
import com.shootdoori.match.team.service.TeamMemberQueryService;
import com.shootdoori.match.user.domain.User;
import com.shootdoori.match.user.service.UserQueryService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LineupCommandServiceTest {

    @Mock
    private TeamMemberQueryService teamMemberQueryService;
    @Mock
    private LineupRepository lineupRepository;
    @Mock
    private UserQueryService userQueryService;

    private LineupCommandService lineupCommandService;

    @BeforeEach
    void setUp() {
        lineupCommandService = new LineupCommandService(
            teamMemberQueryService, lineupRepository, userQueryService
        );
    }

    @Test
    @DisplayName("라인업 생성 성공")
    void create_success() {
        // given
        Long loginUserId = 1L;
        Long matchId = 10L;
        Long loginTeamId = 100L;
        List<LineupMemberRequestDto> requestDtos = createValidLineupRequest();
        
        given(teamMemberQueryService.getTeamIdByUserId(loginUserId)).willReturn(loginTeamId);
        
        for (int i = 1; i <= 11; i++) {
            TeamMember teamMember = mock(TeamMember.class);
            given(teamMember.getId()).willReturn((long) i);
            given(teamMember.getUserId()).willReturn((long) i);
            given(teamMemberQueryService.findByIdForEntity((long) i)).willReturn(teamMember);
            
            User user = mock(User.class);
            given(user.getPosition()).willReturn(Position.AM);
            given(userQueryService.findByIdForEntity((long) i)).willReturn(user);
        }
        
        given(lineupRepository.save(any(Lineup.class))).willReturn(new Lineup(matchId, loginTeamId));

        // when
        List<LineupMemberResponseDto> result = lineupCommandService.create(loginUserId, matchId, requestDtos);

        // then
        assertThat(result).isNotNull();
        then(lineupRepository).should().save(any(Lineup.class));
    }

    @Test
    @DisplayName("라인업 생성 실패 - 타 팀 멤버 포함")
    void create_fail_notOwnedTeamMember() {
        // given
        Long loginUserId = 1L;
        Long matchId = 10L;
        Long loginTeamId = 100L;
        List<LineupMemberRequestDto> requestDtos = List.of(
            new LineupMemberRequestDto(1L, true)
        );
        
        given(teamMemberQueryService.getTeamIdByUserId(loginUserId)).willReturn(loginTeamId);
        
        TeamMember teamMember = mock(TeamMember.class);
        given(teamMemberQueryService.findByIdForEntity(1L)).willReturn(teamMember);
        doThrow(new NoPermissionException()).when(teamMember).validateBelongsToTeam(loginTeamId);

        // when & then
        assertThatThrownBy(() -> lineupCommandService.create(loginUserId, matchId, requestDtos))
            .isInstanceOf(NoPermissionException.class);
    }

    @Test
    @DisplayName("라인업 수정 실패 - 유효하지 않은 라인업 (1명)")
    void update_fail_invalidLineup() {
        // given
        Long loginUserId = 1L;
        Long lineupId = 999L;
        Long loginTeamId = 100L;
        List<LineupMemberRequestDto> requestDtos = List.of(
            new LineupMemberRequestDto(1L, true)
        );
        
        given(teamMemberQueryService.getTeamIdByUserId(loginUserId)).willReturn(loginTeamId);
        
        Lineup lineup = new Lineup(10L, loginTeamId);
        given(lineupRepository.findByIdAndTeamId(lineupId, loginTeamId)).willReturn(Optional.of(lineup));
        
        TeamMember teamMember = mock(TeamMember.class);
        given(teamMember.getId()).willReturn(1L);
        given(teamMember.getUserId()).willReturn(1L);
        given(teamMemberQueryService.findByIdForEntity(1L)).willReturn(teamMember);
        
        User user = mock(User.class);
        given(user.getPosition()).willReturn(Position.AM);
        given(userQueryService.findByIdForEntity(1L)).willReturn(user);

        // when & then
        assertThatThrownBy(() -> lineupCommandService.update(loginUserId, lineupId, requestDtos))
            .isInstanceOf(CreationFailException.class);
    }

    @Test
    @DisplayName("라인업 수정 실패 - 소유하지 않은 라인업")
    void update_fail_notFound() {
        // given
        Long loginUserId = 1L;
        Long lineupId = 999L;
        Long loginTeamId = 100L;
        List<LineupMemberRequestDto> requestDtos = createValidLineupRequest();
        
        given(teamMemberQueryService.getTeamIdByUserId(loginUserId)).willReturn(loginTeamId);
        given(lineupRepository.findByIdAndTeamId(lineupId, loginTeamId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> lineupCommandService.update(loginUserId, lineupId, requestDtos))
            .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("라인업 삭제 성공")
    void delete_success() {
        // given
        Long loginUserId = 1L;
        Long lineupId = 999L;
        Long loginTeamId = 100L;
        
        given(teamMemberQueryService.getTeamIdByUserId(loginUserId)).willReturn(loginTeamId);
        
        Lineup lineup = new Lineup(10L, loginTeamId);
        given(lineupRepository.findByIdAndTeamId(lineupId, loginTeamId)).willReturn(Optional.of(lineup));

        // when
        lineupCommandService.delete(loginUserId, lineupId);

        // then
        then(lineupRepository).should().delete(lineup);
    }

    @Test
    @DisplayName("라인업 삭제 실패 - 소유하지 않은 라인업")
    void delete_fail_notFound() {
        // given
        Long loginUserId = 1L;
        Long lineupId = 999L;
        Long loginTeamId = 100L;
        
        given(teamMemberQueryService.getTeamIdByUserId(loginUserId)).willReturn(loginTeamId);
        given(lineupRepository.findByIdAndTeamId(lineupId, loginTeamId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> lineupCommandService.delete(loginUserId, lineupId))
            .isInstanceOf(NotFoundException.class);
    }

    private List<LineupMemberRequestDto> createValidLineupRequest() {
        return List.of(
            new LineupMemberRequestDto(1L, true),
            new LineupMemberRequestDto(2L, true),
            new LineupMemberRequestDto(3L, true),
            new LineupMemberRequestDto(4L, true),
            new LineupMemberRequestDto(5L, true),
            new LineupMemberRequestDto(6L, true),
            new LineupMemberRequestDto(7L, true),
            new LineupMemberRequestDto(8L, true),
            new LineupMemberRequestDto(9L, true),
            new LineupMemberRequestDto(10L, true),
            new LineupMemberRequestDto(11L, true)
        );
    }
}