package com.shootdoori.match.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record RecentMatchesResponseDto(
    Long matchId,
    Long createTeamId,
    Long requestTeamId,
    Long lineup1Id,
    Long lineup2Id,
    String createTeamName,
    String requestTeamName,
    LocalDate matchDate,
    LocalTime matchTime,
    String venueName,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static RecentMatchesResponseDto from(MatchSummaryProjection matchSummaryProjection) {
        return new RecentMatchesResponseDto(
            matchSummaryProjection.matchId(),
            matchSummaryProjection.createTeamId(),
            matchSummaryProjection.requestTeamId(),
            matchSummaryProjection.createTeamLineupId(),
            matchSummaryProjection.requestTeamLineupId(),
            matchSummaryProjection.createTeamName(),
            matchSummaryProjection.requestTeamName(),
            matchSummaryProjection.matchDate(),
            matchSummaryProjection.matchTime(),
            matchSummaryProjection.venueName(),
            matchSummaryProjection.createdAt(),
            matchSummaryProjection.updatedAt()
        );
    }
}
