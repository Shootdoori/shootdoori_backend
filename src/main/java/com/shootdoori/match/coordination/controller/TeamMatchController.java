package com.shootdoori.match.coordination.controller;

import com.shootdoori.match.coordination.service.TeamMatchQueryService;
import com.shootdoori.match.dto.RecentMatchesResponseDto;
import com.shootdoori.match.resolver.LoginUser;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teams")
public class TeamMatchController {

    private final TeamMatchQueryService teamMatchQueryService;

    public TeamMatchController(TeamMatchQueryService teamMatchQueryService) {
        this.teamMatchQueryService = teamMatchQueryService;
    }

    @GetMapping("/me/matches")
    public ResponseEntity<List<RecentMatchesResponseDto>> findRecentCompletedMatches(
        @LoginUser Long loginUserId,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate cursorDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime cursorTime,
        @RequestParam(defaultValue = "10") @Min(1) @Max(10) int size
    ) {
        return new ResponseEntity<>(
            teamMatchQueryService.findRecentCompletedMatches(loginUserId, cursorDate, cursorTime,
                size), HttpStatus.OK);
    }
}

