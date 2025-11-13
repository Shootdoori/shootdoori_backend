package com.shootdoori.match.dto;

public record TeamReviewRequestDto(Long matchId,
                                   Long reviewerTeamId,
                                   Long reviewedTeamId,
                                   Integer rating
) {
}
