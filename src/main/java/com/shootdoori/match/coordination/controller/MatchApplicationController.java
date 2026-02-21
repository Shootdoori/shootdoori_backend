package com.shootdoori.match.coordination.controller;

import com.shootdoori.match.coordination.service.MatchApplicationCommandService;
import com.shootdoori.match.coordination.service.MatchApplicationQueryService;
import com.shootdoori.match.dto.MatchConfirmedResponseDto;
import com.shootdoori.match.dto.MatchApplicationRequestDto;
import com.shootdoori.match.dto.MatchApplicationResponseDto;
import com.shootdoori.match.dto.MatchWaitingResponseDto;
import com.shootdoori.match.resolver.LoginUser;
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
    private final MatchApplicationQueryService matchApplicationQueryService;

    public MatchApplicationController(
        MatchApplicationCommandService matchApplicationCommandService,
        MatchApplicationQueryService matchApplicationQueryService
    ) {
        this.matchApplicationCommandService = matchApplicationCommandService;
        this.matchApplicationQueryService = matchApplicationQueryService;
    }

    @PostMapping("/{waitingId}/request")
    public ResponseEntity<MatchApplicationResponseDto> apply(
        @LoginUser Long loginUserId,
        @PathVariable Long waitingId,
        @RequestBody MatchApplicationRequestDto requestDto
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
        return new ResponseEntity<>(matchApplicationCommandService.accept(loginUserId,
            requestId), HttpStatus.OK);
    }

    @PutMapping("/requests/{requestId}/reject")
    public ResponseEntity<MatchApplicationResponseDto> reject(
        @LoginUser Long loginUserId,
        @PathVariable Long requestId
    ) {
        return new ResponseEntity<>(matchApplicationCommandService.reject(loginUserId,
            requestId), HttpStatus.OK);
    }

    @GetMapping("/receive/me/pending")
    public ResponseEntity<Slice<MatchApplicationResponseDto>> findAllReceivedPending(
        @LoginUser Long loginUserId,
        @PageableDefault(sort = "timeStamp.createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return new ResponseEntity<>(
            matchApplicationQueryService.findAllReceivedPending(loginUserId, pageable),
            HttpStatus.OK
        );
    }

    @GetMapping("/requests/me")
    public ResponseEntity<Slice<MatchApplicationResponseDto>> findSentByRequestTeamId(
        @LoginUser Long loginUserId,
        @PageableDefault(sort = "timeStamp.createdAt", direction = Sort.Direction.DESC)
        Pageable pageable) {
        return new ResponseEntity<>(
            matchApplicationQueryService.findSentByRequestTeamId(loginUserId, pageable),
            HttpStatus.OK
        );
    }

    @DeleteMapping("/requests/{requestId}")
    public ResponseEntity<MatchApplicationResponseDto> cancel(
        @LoginUser Long loginUserId,
        @PathVariable Long requestId
    ) {
        return new ResponseEntity<>(matchApplicationCommandService.cancel(loginUserId,
            requestId), HttpStatus.OK);
    }
}
