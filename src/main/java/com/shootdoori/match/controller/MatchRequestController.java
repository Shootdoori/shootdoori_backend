package com.shootdoori.match.controller;

import com.shootdoori.match.dto.*;
import com.shootdoori.match.resolver.LoginUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/api/matches")
public class MatchRequestController {
    @GetMapping("/waiting")
    public ResponseEntity<Slice<MatchWaitingResponseDto>> getWaitingMatches(
        @LoginUser Long loginUserId,
        @RequestParam("selectDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate selectDate,
        @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
        @PageableDefault(sort = "preferredTimeStart", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return null;
    }

    @PostMapping("/{waitingId}/request")
    public ResponseEntity<MatchRequestResponseDto> requestToMatch(
        @LoginUser Long loginUserId,
        @PathVariable Long waitingId,
        @RequestBody MatchRequestRequestDto requestDto
    ) {
        return null;
    }

    @DeleteMapping("/requests/{requestId}")
    public ResponseEntity<MatchRequestResponseDto> cancelMatchRequest(
        @LoginUser Long loginUserId,
        @PathVariable Long requestId
    ) {
        return null;
    }

    @GetMapping("/receive/me/pending")
    public ResponseEntity<Slice<MatchRequestResponseDto>> getReceivedPendingRequests(
        @LoginUser Long loginUserId,
        @PageableDefault(sort = "requestAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return null;
    }

    @PatchMapping("/requests/{requestId}/accept")
    public ResponseEntity<MatchConfirmedResponseDto> acceptRequest(
        @LoginUser Long loginUserId,
        @PathVariable Long requestId
    ) {
        return null;
    }

    @PatchMapping("/requests/{requestId}/reject")
    public ResponseEntity<MatchRequestResponseDto> rejectRequest(
        @LoginUser Long loginUserId,
        @PathVariable Long requestId
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
}
