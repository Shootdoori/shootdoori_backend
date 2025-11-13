package com.shootdoori.match.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record MatchCreateRequestDto(
    LocalDate preferredDate,
    LocalTime preferredTimeStart,
    LocalTime preferredTimeEnd,
    Long preferredVenueId,
    Boolean universityOnly,
    String message,
    Long lineupId
) {
}
