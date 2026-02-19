package com.shootdoori.match.coordination.controller;

import com.shootdoori.match.coordination.service.MatchCommandService;
import com.shootdoori.match.coordination.service.EnemyTeamQueryService;
import com.shootdoori.match.coordination.service.MatchQueryService;
import com.shootdoori.match.dto.EnemyTeamResponseDto;
import com.shootdoori.match.dto.MatchCreateRequestDto;
import com.shootdoori.match.dto.MatchCreateResponseDto;
import com.shootdoori.match.dto.MatchWaitingCancelResponseDto;
import com.shootdoori.match.dto.MatchWaitingResponseDto;
import com.shootdoori.match.resolver.LoginUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final MatchQueryService matchQueryService;
    private final MatchCommandService matchCommandService;
    private final EnemyTeamQueryService enemyTeamQueryService;
    public MatchController(
        MatchQueryService matchQueryService,
        MatchCommandService matchCommandService,
        EnemyTeamQueryService enemyTeamQueryService
    ) {
        this.matchQueryService = matchQueryService;
        this.matchCommandService = matchCommandService;
        this.enemyTeamQueryService = enemyTeamQueryService;
    }

    @PostMapping
    public ResponseEntity<MatchCreateResponseDto> create(
        @LoginUser Long loginUserId,
        @RequestBody MatchCreateRequestDto matchCreateRequestDto
    ) {
        return new ResponseEntity<>(matchCommandService.create(loginUserId, matchCreateRequestDto),
            HttpStatus.CREATED);
    }

    @PutMapping("/waiting/{matchWaitingId}/cancel")
    public ResponseEntity<MatchWaitingCancelResponseDto> cancel(
        @LoginUser Long loginUserId,
        @PathVariable Long matchWaitingId
    ) {
        return new ResponseEntity<>(matchCommandService.cancel(loginUserId, matchWaitingId),
            HttpStatus.OK);
    }

    @GetMapping("/waiting/me")
    public ResponseEntity<Slice<MatchWaitingResponseDto>> findAll(
        @LoginUser Long loginUserId,
        @PageableDefault(sort = "timeStamp.createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return new ResponseEntity<>(matchQueryService.findAll(loginUserId, pageable), HttpStatus.OK);
    }

    @GetMapping("/{matchId}/enemyTeam")
    public ResponseEntity<EnemyTeamResponseDto> findEnemyTeam(
        @LoginUser Long loginUserId,
        @PathVariable Long matchId
    ) {
        return new ResponseEntity<>(enemyTeamQueryService.findEnemyTeam(loginUserId, matchId),
            HttpStatus.OK);
    }
}
