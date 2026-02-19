package com.shootdoori.match.dto;

import com.shootdoori.match.coordination.domain.Match;
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
    public static MatchConfirmedResponseDto from(Match match) {
        return new MatchConfirmedResponseDto(
            match.getId(),
            match.getHomeTeamId(),
            match.getAwayTeamId(),
            match.getPreferredDate(),
            match.getPreferredTimeStart(),
            match.getVenueId(),
            match.getHomeLineupId(),
            match.getAwayLineupId()
        );
    }
}
