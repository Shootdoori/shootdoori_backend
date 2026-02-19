package com.shootdoori.match.coordination.service;

import com.shootdoori.match.coordination.domain.Match;
import com.shootdoori.match.coordination.domain.Venue;
import com.shootdoori.match.coordination.repository.MatchRepository;
import com.shootdoori.match.dto.MatchCreateRequestDto;
import com.shootdoori.match.dto.MatchCreateResponseDto;
import com.shootdoori.match.dto.MatchWaitingCancelResponseDto;
import com.shootdoori.match.team.service.TeamMemberQueryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class MatchCommandService {

    private final MatchQueryService matchQueryService;
    private final MatchRepository matchRepository;
    private final MatchApplicationCommandService matchApplicationCommandService;
    private final TeamMemberQueryService teamMemberQueryService;
    private final VenueQueryService venueQueryService;

    public MatchCommandService(
        MatchQueryService matchQueryService,
        MatchRepository matchRepository,
        MatchApplicationCommandService matchApplicationCommandService,
        TeamMemberQueryService teamMemberQueryService,
        VenueQueryService venueQueryService
    ) {
        this.matchQueryService = matchQueryService;
        this.matchRepository = matchRepository;
        this.matchApplicationCommandService = matchApplicationCommandService;
        this.teamMemberQueryService = teamMemberQueryService;
        this.venueQueryService = venueQueryService;
    }

    public MatchCreateResponseDto create(Long loginUserId, MatchCreateRequestDto requestDto) {
        Long homeTeamId = teamMemberQueryService.getTeamIdByUserId(loginUserId);
        Venue venue = venueQueryService.findByIdForEntity(requestDto.preferredVenueId());

        Match match = createWaiting(homeTeamId, requestDto, venue);
        Match saved = matchRepository.save(match);
        
        return MatchCreateResponseDto.from(saved, homeTeamId, venue);
    }

    public MatchWaitingCancelResponseDto cancel(Long loginUserId, Long waitingId) {
        Long loginTeamId = teamMemberQueryService.getTeamIdByUserId(loginUserId);

        Match waiting = matchQueryService.findById(waitingId);
        waiting.validateHomeTeam(loginTeamId);

        waiting.cancel();
        matchApplicationCommandService.rejectAllPending(waiting.getId(), loginTeamId);

        return new MatchWaitingCancelResponseDto(
            waiting.getId(),
            waiting.getHomeTeamId(),
            waiting.getExpiresAt()
        );
    }

    private Match createWaiting(Long homeTeamId, MatchCreateRequestDto requestDto, Venue venue) {
        return new Match(
            homeTeamId,
            requestDto.preferredDate(),
            requestDto.preferredTimeStart(),
            requestDto.preferredTimeEnd(),
            venue.getId(),
            requestDto.universityOnly(),
            requestDto.message(),
            requestDto.lineupId()
        );
    }
}
