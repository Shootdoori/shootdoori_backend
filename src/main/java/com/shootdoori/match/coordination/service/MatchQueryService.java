package com.shootdoori.match.coordination.service;

import com.shootdoori.match.coordination.domain.Match;
import com.shootdoori.match.coordination.domain.MatchStatus;
import com.shootdoori.match.coordination.repository.MatchRepository;
import com.shootdoori.match.dto.MatchWaitingResponseDto;
import com.shootdoori.match.exception.common.ErrorCode;
import com.shootdoori.match.exception.common.NotFoundException;
import com.shootdoori.match.team.service.TeamMemberQueryService;
import java.time.LocalDateTime;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MatchQueryService {

    private final TeamMemberQueryService teamMemberQueryService;
    private final MatchRepository matchRepository;

    public MatchQueryService(TeamMemberQueryService teamMemberQueryService,
        MatchRepository matchRepository) {
        this.teamMemberQueryService = teamMemberQueryService;
        this.matchRepository = matchRepository;
    }

    public Match findById(Long waitingId) {
        Match waiting = matchRepository.findById(waitingId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.MATCH_WAITING_NOT_FOUND));
        waiting.validateWaitingStatus();

        return waiting;
    }

    public Slice<MatchWaitingResponseDto> findAll(Long loginUserId, Pageable pageable) {
        Long loginTeamId = teamMemberQueryService.getTeamIdByUserId(loginUserId);

        return matchRepository.findAllByHomeTeamIdAndStatusAndExpiresAtAfter(
                loginTeamId,
                MatchStatus.WAITING,
                LocalDateTime.now(),
                pageable
            )
            .map(MatchWaitingResponseDto::from);
    }
}
