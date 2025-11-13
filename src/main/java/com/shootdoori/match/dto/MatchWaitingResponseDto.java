package com.shootdoori.match.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record MatchWaitingResponseDto(
    Long waitingId,
    Long teamId,
    LocalDate preferredDate,
    LocalTime preferredTimeStart,
    LocalTime preferredTimeEnd,
    Long preferredVenueId,
    Boolean universityOnly,
    String message,
    LocalDateTime expiresAt,
    Long lineup1Id
) {
}
