package com.shootdoori.match.coordination.service;

import com.shootdoori.match.coordination.domain.MatchApplication;
import com.shootdoori.match.coordination.domain.MatchApplicationStatus;
import com.shootdoori.match.coordination.repository.MatchApplicationRepository;
import com.shootdoori.match.dto.MatchApplicationResponseDto;
import com.shootdoori.match.exception.common.DuplicatedException;
import com.shootdoori.match.exception.common.ErrorCode;
import com.shootdoori.match.exception.common.NotFoundException;
import com.shootdoori.match.team.service.TeamMemberQueryService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MatchApplicationQueryService {

    private final TeamMemberQueryService teamMemberQueryService;
    private final MatchApplicationRepository matchApplicationRepository;

    public MatchApplicationQueryService(
        TeamMemberQueryService teamMemberQueryService,
        MatchApplicationRepository matchApplicationRepository
    ) {
        this.teamMemberQueryService = teamMemberQueryService;
        this.matchApplicationRepository = matchApplicationRepository;
    }

    public MatchApplication findByIdForEntity(Long id) {
        MatchApplication application = matchApplicationRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(ErrorCode.MATCH_REQUEST_NOT_FOUND));

        return application;
    }

    public Slice<MatchApplicationResponseDto> findAllReceivedPending(Long loginUserId, Pageable pageable) {
        Long loginTeamId = teamMemberQueryService.getTeamIdByUserId(loginUserId);
        return matchApplicationRepository.findReceivedPendingByHomeTeamId(loginTeamId, pageable);
    }

    public Slice<MatchApplicationResponseDto> findSentByRequestTeamId(Long loginUserId,
        Pageable pageable) {
        Long loginTeamId = teamMemberQueryService.getTeamIdByUserId(loginUserId);
        return matchApplicationRepository.findSentByRequestTeamId(loginTeamId, pageable);
    }

    public void checkDuplicate(Long waitingId, Long requestTeamId) {
        if (matchApplicationRepository.existsByMatchIdAndRequestTeamIdAndStatus(waitingId,
            requestTeamId, MatchApplicationStatus.PENDING)) {
            throw new DuplicatedException(ErrorCode.ALREADY_MATCH_REQUEST);
        }
    }
}
