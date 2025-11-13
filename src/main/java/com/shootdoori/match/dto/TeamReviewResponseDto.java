package com.shootdoori.match.dto;

public record TeamReviewResponseDto(Long teamReviewId,
                                    Long matchId,
                                    Long reviewerTeamId,
                                    Long reviewedTeamId,
                                    Integer rating
) {
}
