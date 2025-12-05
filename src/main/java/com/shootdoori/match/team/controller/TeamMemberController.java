package com.shootdoori.match.team.controller;

import com.shootdoori.match.dto.UpdateTeamMemberRequestDto;
import com.shootdoori.match.resolver.LoginUser;
import com.shootdoori.match.team.domain.TeamMemberRole;
import com.shootdoori.match.team.dto.TeamMemberRequestDto;
import com.shootdoori.match.team.dto.TeamMemberResponseDto;
import com.shootdoori.match.team.service.TeamMemberCommandService;
import com.shootdoori.match.team.service.TeamMemberQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teams/{teamId}")
public class TeamMemberController {

    private final TeamMemberQueryService teamMemberQueryService;
    private final TeamMemberCommandService teamMemberCommandService;

    public TeamMemberController(TeamMemberQueryService teamMemberQueryService,
        TeamMemberCommandService teamMemberCommandService) {
        this.teamMemberQueryService = teamMemberQueryService;
        this.teamMemberCommandService = teamMemberCommandService;
    }

    @PostMapping("/users")
    public ResponseEntity<TeamMemberResponseDto> create(@PathVariable Long teamId,
        @RequestBody TeamMemberRequestDto requestDto,
        @LoginUser Long userId) {
        return new ResponseEntity<>(teamMemberCommandService.create(teamId, requestDto, userId),
            HttpStatus.CREATED);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<TeamMemberResponseDto> findByTeamIdAndUserId(@PathVariable Long teamId,
        @PathVariable Long userId) {
        return new ResponseEntity<>(teamMemberQueryService.findByTeamIdAndUserId(teamId, userId),
            HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<Page<TeamMemberResponseDto>> findAllByTeamId(@PathVariable Long teamId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(teamMemberQueryService.findAllByTeamId(teamId, page, size),
            HttpStatus.OK);
    }

    @GetMapping("/users/slice")
    public ResponseEntity<Slice<TeamMemberResponseDto>> findSliceByTeamId(@PathVariable Long teamId,
        @RequestParam(required = false) Long cursorId,
        @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(
            teamMemberQueryService.findSliceByTeamId(teamId, cursorId, size), HttpStatus.OK);
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<TeamMemberResponseDto> update(@PathVariable Long teamId,
        @PathVariable Long userId,
        @RequestBody UpdateTeamMemberRequestDto requestDto,
        @LoginUser Long loginUserId) {
        return new ResponseEntity<>(
            teamMemberCommandService.update(teamId, userId, requestDto, loginUserId),
            HttpStatus.OK);
    }

    @DeleteMapping("/users/me")
    public ResponseEntity<Void> leave(@PathVariable Long teamId,
        @LoginUser Long loginUserId) {
        teamMemberCommandService.leave(teamId, loginUserId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> kick(@PathVariable Long teamId,
        @PathVariable Long userId,
        @LoginUser Long loginUserId) {
        teamMemberCommandService.kick(teamId, userId, loginUserId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/members/{memberId}/delegate-leadership")
    public ResponseEntity<TeamMemberResponseDto> delegateLeadership(
        @PathVariable Long teamId,
        @PathVariable Long memberId,
        @LoginUser Long loginUserId
    ) {
        return new ResponseEntity<>(
            teamMemberCommandService.delegateRole(teamId, loginUserId, memberId,
                TeamMemberRole.LEADER), HttpStatus.OK);
    }

    @PostMapping("/members/{memberId}/delegate-vice-leadership")
    public ResponseEntity<TeamMemberResponseDto> delegateViceLeadership(
        @PathVariable Long teamId,
        @PathVariable Long memberId,
        @LoginUser Long loginUserId
    ) {
        return new ResponseEntity<>(
            teamMemberCommandService.delegateRole(teamId, loginUserId, memberId,
                TeamMemberRole.VICE_LEADER), HttpStatus.OK);
    }
}
