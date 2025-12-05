package com.shootdoori.match.controller;

import com.shootdoori.match.dto.EnemyTeamResponseDto;
import com.shootdoori.match.resolver.LoginUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/matches")
public class MatchCompleteController {

    @GetMapping("/{matchId}/enemyTeam")
    public ResponseEntity<EnemyTeamResponseDto> findEnemyTeam(@LoginUser Long loginUserId,
                                                             @PathVariable Long matchId) {
        return null;
    }
}
