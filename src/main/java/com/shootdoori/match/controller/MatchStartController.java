package com.shootdoori.match.controller;

import com.shootdoori.match.dto.RecentMatchesResponseDto;
import com.shootdoori.match.resolver.LoginUser;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class MatchStartController {

    @GetMapping("/me/matches")
    public ResponseEntity<List<RecentMatchesResponseDto>> getRecentCompletedMatches(
        @LoginUser Long loginUserId,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate cursorDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime cursorTime,
        @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size
    ) {
        return null;
    }
}
