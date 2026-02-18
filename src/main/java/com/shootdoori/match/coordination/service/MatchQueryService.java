package com.shootdoori.match.coordination.service;

import com.shootdoori.match.coordination.domain.Match;
import com.shootdoori.match.coordination.domain.MatchStatus;
import com.shootdoori.match.coordination.repository.MatchRepository;
import com.shootdoori.match.exception.common.BusinessException;
import com.shootdoori.match.exception.common.ErrorCode;
import com.shootdoori.match.exception.common.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MatchQueryService {
    private final MatchRepository matchRepository;

    public MatchQueryService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public Match findById(Long waitingId) {
        Match waiting = matchRepository.findById(waitingId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.MATCH_WAITING_NOT_FOUND));

        if (waiting.getStatus() != MatchStatus.WAITING) {
            throw new NotFoundException(ErrorCode.MATCH_WAITING_NOT_FOUND);
        }

        return waiting;
    }
}
