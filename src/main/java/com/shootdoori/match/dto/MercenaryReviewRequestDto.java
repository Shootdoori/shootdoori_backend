package com.shootdoori.match.dto;

public record MercenaryReviewRequestDto(Long matchId,
                                        Long reviewerTeamId,
                                        Long userId,
                                        Integer rating
) {
}
