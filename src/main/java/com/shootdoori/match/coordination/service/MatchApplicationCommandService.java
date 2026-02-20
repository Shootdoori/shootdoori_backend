package com.shootdoori.match.coordination.service;

import com.shootdoori.match.coordination.domain.Match;
import com.shootdoori.match.coordination.domain.MatchApplication;
import com.shootdoori.match.coordination.domain.MatchApplicationStatus;
import com.shootdoori.match.coordination.repository.MatchApplicationRepository;
import com.shootdoori.match.dto.MatchConfirmedResponseDto;
import com.shootdoori.match.dto.MatchApplicationRequestDto;
import com.shootdoori.match.dto.MatchApplicationResponseDto;
import com.shootdoori.match.team.service.TeamMemberQueryService;
import java.time.LocalDateTime;
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

    public MatchApplicationResponseDto apply(Long loginUserId, Long waitingId,
        MatchApplicationRequestDto requestDto) {
        Long requestTeamId = teamMemberQueryService.getTeamIdByUserId(loginUserId);
        Match waiting = matchQueryService.findById(waitingId);

        waiting.validateNotApplyingToOwnTeam(requestTeamId);
        matchApplicationQueryService.checkDuplicate(waitingId, requestTeamId);

        MatchApplication matchApplication = new MatchApplication(waitingId, requestTeamId, requestDto.lineupId(),
            requestDto.requestMessage());
        MatchApplication saved = matchApplicationRepository.save(matchApplication);

        return MatchApplicationResponseDto.from(saved, waiting);
    }

    public MatchConfirmedResponseDto accept(Long loginUserId, Long requestId) {
        Long loginTeamId = teamMemberQueryService.getTeamIdByUserId(loginUserId);
        MatchApplication accepted = matchApplicationQueryService.findByIdForEntity(requestId);

        Match waiting = matchQueryService.findById(accepted.getMatchId());
        waiting.validateHomeTeam(loginTeamId);

        accepted.accept(loginTeamId);
        rejectAllPending(waiting.getId(), loginTeamId);

        LocalDateTime confirmedAt = waiting.getPreferredDate()
            .atTime(waiting.getPreferredTimeStart());
        waiting.match(accepted.getRequestTeamId(), accepted.getLineupId(), confirmedAt);

        return MatchConfirmedResponseDto.from(waiting);
    }

    public MatchApplicationResponseDto reject(Long loginUserId, Long requestId) {
        Long loginTeamId = teamMemberQueryService.getTeamIdByUserId(loginUserId);
        MatchApplication application = matchApplicationQueryService.findByIdForEntity(requestId);

        Match waiting = matchQueryService.findById(application.getMatchId());
        waiting.validateHomeTeam(loginTeamId);

        application.reject(loginTeamId);

        return MatchApplicationResponseDto.from(application, waiting);
    }

    public MatchApplicationResponseDto cancel(Long loginUserId, Long requestId) {
        Long loginTeamId = teamMemberQueryService.getTeamIdByUserId(loginUserId);
        MatchApplication application = matchApplicationQueryService.findByIdForEntity(requestId);

        application.cancel(loginTeamId);

        Match waiting = matchQueryService.findById(application.getMatchId());

        return MatchApplicationResponseDto.from(application, waiting);
    }

    public void rejectAllPending(Long matchId, Long processorTeamId) {
        List<MatchApplication> pendingList = matchApplicationRepository.findAllByMatchIdAndStatus(
            matchId, MatchApplicationStatus.PENDING);

        for (MatchApplication pending : pendingList) {
            pending.reject(processorTeamId);
        }
    }
}
