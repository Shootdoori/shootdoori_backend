package com.shootdoori.match.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record MatchConfirmedResponseDto(
    Long matchId,
    Long team1Id,
    Long team2Id,
    LocalDate matchDate,
    LocalTime matchTime,
    Long venueId,
    Long lineup1Id,
    Long lineup2Id
) {
}
