package com.shootdoori.match.coordination.service;

import com.shootdoori.match.coordination.domain.MatchApplication;
import com.shootdoori.match.coordination.domain.MatchApplicationStatus;
import com.shootdoori.match.coordination.repository.MatchApplicationRepository;
import com.shootdoori.match.exception.common.DuplicatedException;
import com.shootdoori.match.exception.common.ErrorCode;
import com.shootdoori.match.exception.common.NotFoundException;

public class MatchApplicationQueryService {

    private final MatchApplicationRepository matchApplicationRepository;

    public MatchApplicationQueryService(MatchApplicationRepository matchApplicationRepository) {
        this.matchApplicationRepository = matchApplicationRepository;
    }

    public MatchApplication findByIdForEntity(Long id) {
        MatchApplication application = matchApplicationRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(ErrorCode.MATCH_REQUEST_NOT_FOUND));

        return application;
    }

    public void checkDuplicate(Long waitingId, Long requestTeamId) {
        if (matchApplicationRepository.existsByMatchIdAndRequestTeamIdAndStatus(waitingId,
            requestTeamId, MatchApplicationStatus.PENDING)) {
            throw new DuplicatedException(ErrorCode.ALREADY_MATCH_REQUEST);
        }
    }
}
