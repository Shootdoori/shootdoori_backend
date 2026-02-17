package com.shootdoori.match.dto;

import com.shootdoori.match.coordination.domain.Match;
import com.shootdoori.match.coordination.domain.Venue;
import java.time.LocalDateTime;

public record MatchCreateResponseDto(
    Long waitingId,
    Long teamId,
    LocalDateTime expiresAt,
    Long venueId,
    String venueName,
    String venueAddress
) {

    public static MatchCreateResponseDto from(Match match, Long teamId, Venue venue) {
        return new MatchCreateResponseDto(
            match.getId(),
            teamId,
            match.getExpiresAt(),
            venue.getId(),
            venue.getName(),
            venue.getAddress()
        );
    }
}
