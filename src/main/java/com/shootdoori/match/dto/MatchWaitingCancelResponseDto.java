package com.shootdoori.match.dto;

import java.time.LocalDateTime;

public record MatchWaitingCancelResponseDto(
    Long waitingId,
    Long teamId,
    LocalDateTime expiresAt
) {
}
