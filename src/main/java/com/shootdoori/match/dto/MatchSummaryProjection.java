package com.shootdoori.match.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record MatchSummaryProjection(
    Long matchId,
    LocalDate matchDate,
    LocalTime matchTime,
    Long createTeamId,
    Long requestTeamId,
    Long createTeamLineupId,
    Long requestTeamLineupId,
    String createTeamName,
    String requestTeamName,
    String venueName,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
