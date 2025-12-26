package com.shootdoori.match.joinWaiting.controller;

import com.shootdoori.match.joinWaiting.domain.JoinWaitingStatus;
import com.shootdoori.match.joinWaiting.domain.JoinWaitingType;
import com.shootdoori.match.joinWaiting.dto.JoinWaitingApproveRequestDto;
import com.shootdoori.match.joinWaiting.dto.JoinWaitingCancelRequestDto;
import com.shootdoori.match.joinWaiting.dto.JoinWaitingRejectRequestDto;
import com.shootdoori.match.joinWaiting.dto.JoinWaitingRequestDto;
import com.shootdoori.match.joinWaiting.dto.JoinWaitingResponseDto;
import com.shootdoori.match.joinWaiting.service.JoinWaitingCommandService;
import com.shootdoori.match.joinWaiting.service.JoinWaitingQueryService;
import com.shootdoori.match.resolver.LoginUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JoinWaitingController {

    private final JoinWaitingQueryService joinWaitingQueryService;
    private final JoinWaitingCommandService joinWaitingCommandService;

    public JoinWaitingController(JoinWaitingQueryService joinWaitingQueryService,
        JoinWaitingCommandService joinWaitingCommandService) {
        this.joinWaitingQueryService = joinWaitingQueryService;
        this.joinWaitingCommandService = joinWaitingCommandService;
    }

    @PostMapping("/api/teams/{teamId}/join-waiting")
    public ResponseEntity<JoinWaitingResponseDto> create(
        @PathVariable Long teamId,
        @LoginUser Long loginUserId,
        @RequestBody JoinWaitingRequestDto requestDto
    ) {
        return new ResponseEntity<>(
            joinWaitingCommandService.create(teamId, 1L, requestDto), HttpStatus.CREATED);
    }

    @PostMapping("/api/teams/{teamId}/join-waiting/{joinWaitingId}/approve")
    public ResponseEntity<JoinWaitingResponseDto> approve(
        @PathVariable Long teamId,
        @PathVariable Long joinWaitingId,
        @LoginUser Long loginUserId,
        @RequestBody JoinWaitingApproveRequestDto requestDto
    ) {
        return new ResponseEntity<>(
            joinWaitingCommandService.approve(teamId, joinWaitingId, loginUserId, requestDto),
            HttpStatus.OK);
    }

    @PostMapping("/api/teams/{teamId}/join-waiting/{joinWaitingId}/reject")
    public ResponseEntity<JoinWaitingResponseDto> reject(
        @PathVariable Long teamId,
        @PathVariable Long joinWaitingId,
        @LoginUser Long loginUserId,
        @RequestBody JoinWaitingRejectRequestDto requestDto
    ) {
        return new ResponseEntity<>(
            joinWaitingCommandService.reject(teamId, joinWaitingId, loginUserId, requestDto),
            HttpStatus.OK);
    }

    @PostMapping("/api/teams/{teamId}/join-waiting/{joinWaitingId}/cancel")
    public ResponseEntity<JoinWaitingResponseDto> cancel(
        @PathVariable Long teamId,
        @PathVariable Long joinWaitingId,
        @LoginUser Long loginUserId,
        @RequestBody JoinWaitingCancelRequestDto requestDto
    ) {
        return new ResponseEntity<>(
            joinWaitingCommandService.cancel(teamId, joinWaitingId, loginUserId, requestDto),
            HttpStatus.OK);
    }

    @GetMapping("/api/teams/{teamId}/join-waiting")
    public ResponseEntity<Page<JoinWaitingResponseDto>> findPending(
        @PathVariable Long teamId,
        @RequestParam(defaultValue = "PENDING") JoinWaitingStatus status,
        @RequestParam(defaultValue = "MEMBER") JoinWaitingType type,
        @PageableDefault(sort = "timeStamp.createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return new ResponseEntity<>(
            joinWaitingQueryService.findPending(teamId, status, type, pageable), HttpStatus.OK);
    }

    @GetMapping("/api/users/me/join-waiting")
    public ResponseEntity<Page<JoinWaitingResponseDto>> findByApplicant(
        @LoginUser Long loginUserId,
        @RequestParam(defaultValue = "MEMBER") JoinWaitingType type,
        @PageableDefault(sort = "timeStamp.createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return new ResponseEntity<>(
            joinWaitingQueryService.findAllByApplicantIdAndJoinWaitingType(loginUserId, type,
                pageable), HttpStatus.OK);
    }
}
