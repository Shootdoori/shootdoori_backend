package com.shootdoori.match.controller;

import com.shootdoori.match.dto.TeamReviewRequestDto;
import com.shootdoori.match.dto.TeamReviewResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/team-reviews")
public class TeamReviewController {
    @GetMapping(params = "reviewedTeamId")
    public ResponseEntity<List<TeamReviewResponseDto>> getAllByReviewedTeamId(@RequestParam Long reviewedTeamId) {
        return null;
    }

    @GetMapping(params = "reviewerTeamId")
    public ResponseEntity<List<TeamReviewResponseDto>> getAllByReviewerTeamId(@RequestParam Long reviewerTeamId) {
        return null;
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<TeamReviewResponseDto> get(@RequestParam Long teamId, @PathVariable Long reviewId) {
        return null;
    }

    @PostMapping()
    public ResponseEntity<Void> post(@RequestBody TeamReviewRequestDto request) {
        return null;
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Void> update(@PathVariable Long reviewId, @RequestBody TeamReviewRequestDto request) {
        return null;
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> delete(@PathVariable Long reviewId) {
        return null;
    }
}
