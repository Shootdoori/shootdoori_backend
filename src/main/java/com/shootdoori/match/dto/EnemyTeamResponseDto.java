package com.shootdoori.match.dto;

import com.shootdoori.match.team.domain.Team;

public record EnemyTeamResponseDto(Long teamId,
                                   String teamName,
                                   String universityName,
                                   String description
) {

    public static EnemyTeamResponseDto from(Team enemyTeam) {
        return new EnemyTeamResponseDto(
            enemyTeam.getId(),
            enemyTeam.getTeamName(),
            enemyTeam.getUniversityName(),
            enemyTeam.getDescription()
        );
    }
}
