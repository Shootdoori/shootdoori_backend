package com.shootdoori.match.dto;

import java.time.LocalDateTime;

public record MatchCreateResponseDto(
    Long waitingId,
    Long teamId,
    LocalDateTime expiresAt
) {
}
