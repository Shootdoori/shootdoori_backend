package com.shootdoori.match.dto;

public record MercenaryReviewResponseDto(Long mercenaryReviewId,
                                         Long matchId,
                                         Long reviewerTeamId,
                                         Long userId,
                                         Integer rating
) {
}
