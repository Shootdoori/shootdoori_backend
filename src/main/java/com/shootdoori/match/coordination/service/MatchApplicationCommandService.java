package com.shootdoori.match.coordination.service;

import com.shootdoori.match.coordination.domain.Match;
import com.shootdoori.match.coordination.domain.MatchApplication;
import com.shootdoori.match.coordination.domain.MatchApplicationStatus;
import com.shootdoori.match.coordination.repository.MatchApplicationRepository;
import com.shootdoori.match.dto.MatchRequestRequestDto;
import com.shootdoori.match.dto.MatchRequestResponseDto;
import com.shootdoori.match.team.service.TeamMemberQueryService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MatchApplicationCommandService {

    private final TeamMemberQueryService teamMemberQueryService;
    private final MatchQueryService matchQueryService;
    private final MatchApplicationQueryService matchApplicationQueryService;
    private final MatchApplicationRepository matchApplicationRepository;

    public MatchApplicationCommandService(
        TeamMemberQueryService teamMemberQueryService, MatchQueryService matchQueryService,
        MatchApplicationQueryService matchApplicationQueryService,
        MatchApplicationRepository matchApplicationRepository
    ) {
        this.teamMemberQueryService = teamMemberQueryService;
        this.matchQueryService = matchQueryService;
        this.matchApplicationQueryService = matchApplicationQueryService;
        this.matchApplicationRepository = matchApplicationRepository;
    }

    public MatchRequestResponseDto apply(Long loginUserId, Long waitingId,
        MatchRequestRequestDto requestDto) {
        Long requestTeamId = teamMemberQueryService.getTeamIdByUserId(loginUserId);
        Match waiting = matchQueryService.findById(waitingId);

        waiting.validateNotApplyingToOwnTeam(requestTeamId);
        matchApplicationQueryService.checkDuplicate(waitingId, requestTeamId);

        MatchApplication matchApplication = new MatchApplication(waitingId, requestTeamId, requestDto.lineupId(),
            requestDto.requestMessage());
        MatchApplication saved = matchApplicationRepository.save(matchApplication);

        return MatchRequestResponseDto.from(saved, waiting);
    }

    public MatchRequestResponseDto reject(Long loginUserId, Long requestId) {
        Long loginTeamId = teamMemberQueryService.getTeamIdByUserId(loginUserId);
        MatchApplication application = matchApplicationQueryService.findByIdForEntity(requestId);

        Match waiting = matchQueryService.findById(application.getMatchId());
        waiting.validateHomeTeam(loginTeamId);

        application.reject(loginTeamId);

        return MatchRequestResponseDto.from(application, waiting);
    }

    public void rejectAllPending(Long matchId, Long processorTeamId) {
        List<MatchApplication> pendingList = matchApplicationRepository.findAllByMatchIdAndStatus(
            matchId, MatchApplicationStatus.PENDING);

        for (MatchApplication pending : pendingList) {
            pending.reject(processorTeamId);
        }
    }
}
