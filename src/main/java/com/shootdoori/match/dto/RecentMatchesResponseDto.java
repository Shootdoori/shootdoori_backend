package com.shootdoori.match.dto;

import com.shootdoori.match.coordination.domain.Match;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record RecentMatchesResponseDto(
    Long matchId,
    Long homeTeamId,
    Long awayTeamId,
    Long homeLineupId,
    Long awayLineupId,
    LocalDate matchDate,
    LocalTime matchTime,
    Long venueId,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public static RecentMatchesResponseDto from(Match match) {
        LocalDateTime matchAt = match.getMatchAt();

        return new RecentMatchesResponseDto(
            match.getId(),
            match.getHomeTeamId(),
            match.getAwayTeamId(),
            match.getHomeLineupId(),
            match.getAwayLineupId(),
            matchAt.toLocalDate(),
            matchAt.toLocalTime(),
            match.getVenueId(),
            match.getCreatedAt(),
            match.getUpdatedAt()
        );
    }
}
