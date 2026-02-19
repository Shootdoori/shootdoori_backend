package com.shootdoori.match.coordination.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.shootdoori.match.coordination.domain.Match;
import com.shootdoori.match.coordination.domain.MatchStatus;
import com.shootdoori.match.coordination.domain.Venue;
import com.shootdoori.match.coordination.repository.MatchRepository;
import com.shootdoori.match.dto.MatchCreateRequestDto;
import com.shootdoori.match.dto.MatchCreateResponseDto;
import com.shootdoori.match.dto.MatchWaitingCancelResponseDto;
import com.shootdoori.match.team.service.TeamMemberQueryService;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class MatchCommandServiceTest {

    @Mock
    private MatchQueryService matchQueryService;
    @Mock
    private MatchRepository matchRepository;
    @Mock
    private MatchApplicationCommandService matchApplicationCommandService;
    @Mock
    private TeamMemberQueryService teamMemberQueryService;
    @Mock
    private VenueQueryService venueQueryService;

    private MatchCommandService matchCommandService;

    @BeforeEach
    void setUp() {
        matchCommandService = new MatchCommandService(
            matchQueryService,
            matchRepository,
            matchApplicationCommandService,
            teamMemberQueryService,
            venueQueryService
        );
    }

    @Test
    @DisplayName("매치 조율을 생성하면 팀 정보를 변환해 저장하고 응답을 반환한다")
    void create_success() {
        Long loginUserId = 10L;
        Long homeTeamId = 100L;
        Long venueId = 5L;

        MatchCreateRequestDto requestDto = new MatchCreateRequestDto(
            LocalDate.of(2026, 3, 20),
            LocalTime.of(14, 0),
            LocalTime.of(16, 0),
            venueId,
            true,
            "즐겜해요",
            7L
        );

        Venue venue = mock(Venue.class);
        given(venue.getId()).willReturn(venueId);
        given(venue.getName()).willReturn("강원대학교 대운동장");
        given(venue.getAddress()).willReturn("춘천");

        given(teamMemberQueryService.getTeamIdByUserId(loginUserId)).willReturn(homeTeamId);
        given(venueQueryService.findByIdForEntity(venueId)).willReturn(venue);

        Match saved = mock(Match.class);
        given(matchRepository.save(any(Match.class))).willReturn(saved);

        MatchCreateResponseDto response = matchCommandService.create(loginUserId, requestDto);

        assertThat(response.teamId()).isEqualTo(homeTeamId);
        assertThat(response.venueId()).isEqualTo(venueId);
    }

    @Test
    @DisplayName("매치 조율을 취소하면 취소 처리 후 대기 중인 요청을 거절한다")
    void cancel_success() {
        Long loginUserId = 10L;
        Long loginTeamId = 100L;
        Long waitingId = 1000L;

        Match waiting = new Match(
            loginTeamId,
            LocalDate.of(2026, 3, 20),
            LocalTime.of(14, 0),
            LocalTime.of(16, 0),
            5L,
            true,
            "매치 요청",
            7L
        );
        ReflectionTestUtils.setField(waiting, "id", waitingId);

        given(teamMemberQueryService.getTeamIdByUserId(loginUserId)).willReturn(loginTeamId);
        given(matchQueryService.findById(waitingId)).willReturn(waiting);

        MatchWaitingCancelResponseDto response = matchCommandService.cancel(loginUserId, waitingId);

        assertThat(response.waitingId()).isEqualTo(waitingId);
        assertThat(response.teamId()).isEqualTo(loginTeamId);
        assertThat(response.expiresAt()).isEqualTo(waiting.getExpiresAt());
        assertThat(waiting.getStatus()).isEqualTo(MatchStatus.CANCELED);
    }
}
