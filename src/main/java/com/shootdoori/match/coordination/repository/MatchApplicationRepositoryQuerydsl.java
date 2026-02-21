package com.shootdoori.match.coordination.repository;

import com.shootdoori.match.dto.MatchApplicationResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface MatchApplicationRepositoryQuerydsl {

    Slice<MatchApplicationResponseDto> findReceivedPendingByHomeTeamId(Long homeTeamId,
        Pageable pageable);

    Slice<MatchApplicationResponseDto> findSentByRequestTeamId(Long requestTeamId, Pageable pageable);
}
