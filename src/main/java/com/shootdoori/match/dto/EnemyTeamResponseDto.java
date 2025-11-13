package com.shootdoori.match.dto;

public record EnemyTeamResponseDto(Long teamId,
                                   String teamName,
                                   Long captainId,
                                   String captainName,
                                   String universityName,
                                   Integer memberCount,
                                   String description
) {
}
