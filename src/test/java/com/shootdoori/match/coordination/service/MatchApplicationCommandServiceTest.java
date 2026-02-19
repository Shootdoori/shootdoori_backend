package com.shootdoori.match.coordination.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.shootdoori.match.coordination.domain.Match;
import com.shootdoori.match.coordination.domain.MatchApplication;
import com.shootdoori.match.coordination.domain.MatchApplicationStatus;
import com.shootdoori.match.coordination.repository.MatchApplicationRepository;
import com.shootdoori.match.dto.MatchRequestRequestDto;
import com.shootdoori.match.dto.MatchRequestResponseDto;
import com.shootdoori.match.exception.common.DuplicatedException;
import com.shootdoori.match.exception.common.ErrorCode;
import com.shootdoori.match.team.service.TeamMemberQueryService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MatchApplicationCommandServiceTest {

    @Mock
    private TeamMemberQueryService teamMemberQueryService;
    @Mock
    private MatchQueryService matchQueryService;
    @Mock
    private MatchApplicationQueryService matchApplicationQueryService;
    @Mock
    private MatchApplicationRepository matchApplicationRepository;
    @Mock
    private Match waiting;

    private MatchApplicationCommandService matchApplicationCommandService;

    @BeforeEach
    void setUp() {
        matchApplicationCommandService = new MatchApplicationCommandService(
            teamMemberQueryService,
            matchQueryService,
            matchApplicationQueryService,
            matchApplicationRepository
        );
    }

    @Test
    @DisplayName("매치 신청 성공 시 요청 정보가 저장되고 응답 DTO가 반환된다")
    void apply_success() {
        // given
        Long loginUserId = 1L;
        Long requestTeamId = 10L;
        Long waitingId = 20L;
        MatchRequestRequestDto dto = new MatchRequestRequestDto("요청", 30L);

        MatchApplication saved = mock(MatchApplication.class);
        given(teamMemberQueryService.getTeamIdByUserId(loginUserId)).willReturn(requestTeamId);
        given(matchQueryService.findById(waitingId)).willReturn(waiting);
        given(matchApplicationRepository.save(any(MatchApplication.class))).willReturn(saved);
        given(saved.getId()).willReturn(1L);
        given(saved.getRequestTeamId()).willReturn(requestTeamId);
        given(saved.getRequestMessage()).willReturn("요청");
        given(saved.getLineupId()).willReturn(30L);
        given(waiting.getHomeTeamId()).willReturn(999L);

        // when
        MatchRequestResponseDto response = matchApplicationCommandService.apply(
            loginUserId, waitingId, dto);

        // then
        assertThat(response.requestId()).isEqualTo(1L);
        assertThat(response.requestTeamId()).isEqualTo(requestTeamId);
        assertThat(response.targetTeamId()).isEqualTo(999L);
    }

    @Test
    @DisplayName("자기 팀에 신청하면 예외가 발생한다")
    void apply_fail_when_applying_to_own_team() {
        // given
        Long loginUserId = 1L;
        Long requestTeamId = 10L;
        Long waitingId = 20L;
        MatchRequestRequestDto dto = new MatchRequestRequestDto("요청", 30L);

        given(teamMemberQueryService.getTeamIdByUserId(loginUserId)).willReturn(requestTeamId);
        given(matchQueryService.findById(waitingId)).willReturn(waiting);
        doThrow(new IllegalStateException("자기 팀에는 매치 신청할 수 없습니다."))
            .when(waiting).validateNotApplyingToOwnTeam(requestTeamId);

        // when & then
        assertThatThrownBy(() ->
            matchApplicationCommandService.apply(loginUserId, waitingId, dto))
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("중복 신청이면 예외가 발생한다")
    void apply_fail_when_duplicate() {
        // given
        Long loginUserId = 1L;
        Long requestTeamId = 10L;
        Long waitingId = 20L;
        MatchRequestRequestDto dto = new MatchRequestRequestDto("요청", 30L);

        given(teamMemberQueryService.getTeamIdByUserId(loginUserId)).willReturn(requestTeamId);
        given(matchQueryService.findById(waitingId)).willReturn(waiting);
        doThrow(new DuplicatedException(ErrorCode.ALREADY_MATCH_REQUEST))
            .when(matchApplicationQueryService).checkDuplicate(waitingId, requestTeamId);

        // when & then
        assertThatThrownBy(() ->
            matchApplicationCommandService.apply(loginUserId, waitingId, dto))
            .isInstanceOf(DuplicatedException.class);
    }

    @Test
    @DisplayName("모든 PENDING(대기중) 신청을 거절 처리한다")
    void rejectAllPending_success() {
        // given
        Long matchId = 100L;
        Long processorTeamId = 200L;

        MatchApplication pending1 = mock(MatchApplication.class);
        MatchApplication pending2 = mock(MatchApplication.class);

        given(matchApplicationRepository.findAllByMatchIdAndStatus(matchId,
            MatchApplicationStatus.PENDING)).willReturn(List.of(pending1, pending2));

        // when
        matchApplicationCommandService.rejectAllPending(matchId, processorTeamId);

        // then
        verify(pending1).reject(processorTeamId);
        verify(pending2).reject(processorTeamId);
    }
}
