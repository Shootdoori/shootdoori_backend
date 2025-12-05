package com.shootdoori.match.controller;

import com.shootdoori.match.dto.*;
import com.shootdoori.match.resolver.LoginUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class JoinWaitingController {

    @PostMapping("/api/teams/{teamId}/join-waiting")
    public ResponseEntity<JoinWaitingResponseDto> create(
        @PathVariable Long teamId,
        @LoginUser Long loginUserId,
        @RequestBody JoinWaitingRequestDto requestDto
    ) {
        return null;
    }

    @PostMapping("/api/teams/{teamId}/join-waiting/{joinWaitingId}/approve")
    public ResponseEntity<JoinWaitingResponseDto> approve(
        @PathVariable Long teamId,
        @PathVariable Long joinWaitingId,
        @LoginUser Long loginUserId,
        @RequestBody JoinWaitingApproveRequestDto requestDto
    ) {
        return null;
    }

    @PostMapping("/api/teams/{teamId}/join-waiting/{joinWaitingId}/reject")
    public ResponseEntity<JoinWaitingResponseDto> reject(
        @PathVariable Long teamId,
        @PathVariable Long joinWaitingId,
        @LoginUser Long loginUserId,
        @RequestBody JoinWaitingRejectRequestDto requestDto
    ) {
        return null;
    }

    @PostMapping("/api/teams/{teamId}/join-waiting/{joinWaitingId}/cancel")
    public ResponseEntity<JoinWaitingResponseDto> cancel(
        @PathVariable Long teamId,
        @PathVariable Long joinWaitingId,
        @LoginUser Long loginUserId,
        @RequestBody JoinWaitingCancelRequestDto requestDto
    ) {
        return null;
    }

    @GetMapping("/api/teams/{teamId}/join-waiting")
    public ResponseEntity<Page<JoinWaitingResponseDto>> findPending(
        @PathVariable Long teamId,
        @RequestParam(defaultValue = "false") boolean isMercenary,
        @PageableDefault(size = 10, sort = "audit.createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return null;
    }

    @GetMapping("/api/users/me/join-waiting")
    public ResponseEntity<Page<JoinWaitingResponseDto>> findByApplicant(
            @LoginUser Long loginUserId,
            @RequestParam(defaultValue = "false") boolean isMercenary,
            @PageableDefault(size = 10, sort = "audit.createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return null;
    }
}
