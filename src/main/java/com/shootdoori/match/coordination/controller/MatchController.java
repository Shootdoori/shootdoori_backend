package com.shootdoori.match.coordination.controller;

import com.shootdoori.match.coordination.service.MatchCommandService;
import com.shootdoori.match.dto.EnemyTeamResponseDto;
import com.shootdoori.match.dto.MatchCreateRequestDto;
import com.shootdoori.match.dto.MatchCreateResponseDto;
import com.shootdoori.match.dto.MatchWaitingCancelResponseDto;
import com.shootdoori.match.dto.MatchWaitingResponseDto;
import com.shootdoori.match.resolver.LoginUser;
import com.shootdoori.match.team.service.TeamMemberQueryService;
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

    private final MatchCommandService matchCommandService;
    private final TeamMemberQueryService teamMemberQueryService;

    public MatchController(MatchCommandService matchCommandService,
        TeamMemberQueryService teamMemberQueryService) {
        this.matchCommandService = matchCommandService;
        this.teamMemberQueryService = teamMemberQueryService;
    }

    @PostMapping
    public ResponseEntity<MatchCreateResponseDto> create(
        @LoginUser Long loginUserId,
        @RequestBody MatchCreateRequestDto matchCreateRequestDto
    ) {
        Long homeTeamId = teamMemberQueryService.getTeamIdByUserId(loginUserId);
        MatchCreateResponseDto responseDto = matchCommandService.create(homeTeamId,
            matchCreateRequestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping("/waiting/{matchWaitingId}/cancel")
    public ResponseEntity<MatchWaitingCancelResponseDto> cancel(
        @LoginUser Long loginUserId,
        @PathVariable Long matchWaitingId
    ) {
        return null;
    }

    @GetMapping("/waiting/me")
    public ResponseEntity<Slice<MatchWaitingResponseDto>> findAll(
        @LoginUser Long loginUserId,
        @PageableDefault(sort = "audit.createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return null;
    }

    @GetMapping("/{matchId}/enemyTeam")
    public ResponseEntity<EnemyTeamResponseDto> findEnemyTeam(
        @LoginUser Long loginUserId,
        @PathVariable Long matchId
    ) {
        return null;
    }
}
