package com.shootdoori.match.dto;

import com.shootdoori.match.coordination.domain.Match;
import com.shootdoori.match.coordination.domain.MatchApplication;

public record MatchRequestResponseDto(
    Long requestId,
    Long requestTeamId,
    Long targetTeamId,
    String requestMessage,
    Long requestTeamLineupId
) {

    public static MatchRequestResponseDto from(MatchApplication matchApplication, Match match) {
        return new MatchRequestResponseDto(
            matchApplication.getId(),
            matchApplication.getRequestTeamId(),
            match.getHomeTeamId(),
            matchApplication.getRequestMessage(),
            matchApplication.getLineupId()
        );
    }
}
