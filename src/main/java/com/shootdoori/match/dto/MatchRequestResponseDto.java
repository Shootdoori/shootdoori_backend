package com.shootdoori.match.dto;

public record MatchRequestResponseDto(
    Long requestId,
    Long requestTeamId,
    Long targetTeamId,
    String requestMessage,
    Long requestTeamLineupId
) {
}
