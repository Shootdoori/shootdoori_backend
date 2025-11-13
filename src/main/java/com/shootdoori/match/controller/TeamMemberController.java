package com.shootdoori.match.controller;

import com.shootdoori.match.dto.TeamMemberRequestDto;
import com.shootdoori.match.dto.TeamMemberResponseDto;
import com.shootdoori.match.dto.UpdateTeamMemberRequestDto;
import com.shootdoori.match.resolver.LoginUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teams/{teamId}")
public class TeamMemberController {
    @PostMapping("/users")
    public ResponseEntity<TeamMemberResponseDto> create(@PathVariable Long teamId,
                                                        @RequestBody TeamMemberRequestDto requestDto,
                                                        @LoginUser Long userId) {
        return null;
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<TeamMemberResponseDto> findByTeamIdAndUserId(@PathVariable Long teamId,
        @PathVariable Long userId) {
        return null;
    }

    @GetMapping("/users")
    public ResponseEntity<Page<TeamMemberResponseDto>> findAllByTeamId(@PathVariable Long teamId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        return null;
    }

    @GetMapping("/users/slice")
    public ResponseEntity<Slice<TeamMemberResponseDto>> findSliceByTeamId(@PathVariable Long teamId,
    @RequestParam(required = false) Long cursorId,
    @RequestParam(defaultValue = "10") int size) {
        return null;
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<TeamMemberResponseDto> update(@PathVariable Long teamId,
                                                        @PathVariable Long userId,
                                                        @RequestBody UpdateTeamMemberRequestDto requestDto,
                                                        @LoginUser Long loginUserId) {
        return null;
    }

    @DeleteMapping("/users/me")
    public ResponseEntity<Void> leave(@PathVariable Long teamId,
                                     @LoginUser Long loginUserId) {
        return null;
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> kick(@PathVariable Long teamId,
        @PathVariable Long userId,
        @LoginUser Long loginUserId) {
        return null;
    }

    @PostMapping("/members/{memberId}/delegate-leadership")
    public ResponseEntity<TeamMemberResponseDto> delegateLeadership(
        @PathVariable Long teamId,
        @PathVariable Long memberId,
        @LoginUser Long loginUserId
    ) {
        return null;
    }

    @PostMapping("/members/{memberId}/delegate-vice-leadership")
    public ResponseEntity<TeamMemberResponseDto> delegateViceLeadership(
        @PathVariable Long teamId,
        @PathVariable Long memberId,
        @LoginUser Long loginUserId
    ) {
        return null;
    }
}
