package com.shootdoori.match.controller;

import com.shootdoori.match.dto.MatchCreateRequestDto;
import com.shootdoori.match.dto.MatchCreateResponseDto;
import com.shootdoori.match.dto.MatchWaitingCancelResponseDto;
import com.shootdoori.match.dto.MatchWaitingResponseDto;
import com.shootdoori.match.resolver.LoginUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/matches")
public class MatchCreateController {

    @PostMapping
    public ResponseEntity<MatchCreateResponseDto> createMatch(
        @LoginUser Long loginUserId,
        @RequestBody MatchCreateRequestDto matchCreateRequestDto
    ) {
        return null;
    }

    @PatchMapping("/waiting/{matchWaitingId}/cancel")
    public ResponseEntity<MatchWaitingCancelResponseDto> cancelMatchWaiting(
        @LoginUser Long loginUserId,
        @PathVariable Long matchWaitingId
    ) {
        return null;
    }

    @GetMapping("/waiting/me")
    public ResponseEntity<Slice<MatchWaitingResponseDto>> getMyWaitingMatches(
        @LoginUser Long loginUserId,
        @PageableDefault(size = 10, sort = "audit.createdAt", direction = org.springframework.data.domain.Sort.Direction.DESC) Pageable pageable
    ) {
        return null;
    }

}
