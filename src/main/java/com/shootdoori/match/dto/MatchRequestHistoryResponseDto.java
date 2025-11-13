package com.shootdoori.match.dto;

import java.time.LocalDateTime;

public record MatchRequestHistoryResponseDto(
    Long requestId,
    Long requestTeamId,
    Long targetTeamId,
    String requestMessage,
    LocalDateTime requestAt
) {
}
