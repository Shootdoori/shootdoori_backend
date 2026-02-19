package com.shootdoori.match.coordination.controller;

import com.shootdoori.match.coordination.service.MatchApplicationCommandService;
import com.shootdoori.match.dto.MatchConfirmedResponseDto;
import com.shootdoori.match.dto.MatchRequestHistoryResponseDto;
import com.shootdoori.match.dto.MatchRequestRequestDto;
import com.shootdoori.match.dto.MatchRequestResponseDto;
import com.shootdoori.match.dto.MatchWaitingResponseDto;
import com.shootdoori.match.resolver.LoginUser;
import com.shootdoori.match.team.service.TeamMemberQueryService;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
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
@RequestMapping("/api/matches")
public class MatchApplicationController {

    private final MatchApplicationCommandService matchApplicationCommandService;

    public MatchApplicationController(
        MatchApplicationCommandService matchApplicationCommandService
    ) {
        this.matchApplicationCommandService = matchApplicationCommandService;
    }

    @PostMapping("/{waitingId}/request")
    public ResponseEntity<MatchRequestResponseDto> apply(
        @LoginUser Long loginUserId,
        @PathVariable Long waitingId,
        @RequestBody MatchRequestRequestDto requestDto
    ) {
        return new ResponseEntity<>(
            matchApplicationCommandService.apply(loginUserId, waitingId, requestDto),
            HttpStatus.CREATED);
    }

    @PutMapping("/requests/{requestId}/accept")
    public ResponseEntity<MatchConfirmedResponseDto> accept(
        @LoginUser Long loginUserId,
        @PathVariable Long requestId
    ) {
        return null;
    }

    @PutMapping("/requests/{requestId}/reject")
    public ResponseEntity<MatchRequestResponseDto> reject(
        @LoginUser Long loginUserId,
        @PathVariable Long requestId
    ) {
        return new ResponseEntity<>(matchApplicationCommandService.reject(loginUserId,
            requestId), HttpStatus.OK);
    }

    @GetMapping("/waiting")
    public ResponseEntity<Slice<MatchWaitingResponseDto>> findAll(
        @LoginUser Long loginUserId,
        @RequestParam("selectDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate selectDate,
        @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
        @PageableDefault(sort = "preferredTimeStart", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return null;
    }

    @GetMapping("/receive/me/pending")
    public ResponseEntity<Slice<MatchRequestResponseDto>> findReceivedPendingRequests(
        @LoginUser Long loginUserId,
        @PageableDefault(sort = "requestAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return null;
    }

    @GetMapping("/requests/me")
    public ResponseEntity<Slice<MatchRequestHistoryResponseDto>> getSentRequestsByMyTeam(
        @LoginUser Long loginUserId,
        @PageableDefault(sort = "requestAt", direction = org.springframework.data.domain.Sort.Direction.DESC)
        Pageable pageable) {
        return null;
    }

    @DeleteMapping("/requests/{requestId}")
    public ResponseEntity<MatchRequestResponseDto> cancel(
        @LoginUser Long loginUserId,
        @PathVariable Long requestId
    ) {
        return new ResponseEntity<>(matchApplicationCommandService.cancel(loginUserId,
            requestId), HttpStatus.OK);
    }
}
