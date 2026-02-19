package com.shootdoori.match.coordination.service;

import com.shootdoori.match.coordination.domain.Match;
import com.shootdoori.match.dto.EnemyTeamResponseDto;
import com.shootdoori.match.team.domain.Team;
import com.shootdoori.match.team.service.TeamMemberQueryService;
import com.shootdoori.match.team.service.TeamQueryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class EnemyTeamQueryService {

    private final TeamQueryService teamQueryService;
    private final TeamMemberQueryService teamMemberQueryService;
    private final MatchQueryService matchQueryService;

    public EnemyTeamQueryService(
        TeamQueryService teamQueryService, TeamMemberQueryService teamMemberQueryService,
        MatchQueryService matchQueryService
    ) {
        this.teamQueryService = teamQueryService;
        this.teamMemberQueryService = teamMemberQueryService;
        this.matchQueryService = matchQueryService;
    }

    public EnemyTeamResponseDto findEnemyTeam(Long loginUserId, Long matchId) {
        Long loginTeamId = teamMemberQueryService.getTeamIdByUserId(loginUserId);

        Match match = matchQueryService.findById(matchId);

        Long enemyTeamId = match.findEnemyTeamId(loginTeamId);
        Team enemyTeam = teamQueryService.findByIdForEntity(enemyTeamId);

        return EnemyTeamResponseDto.from(enemyTeam);
    }
}
