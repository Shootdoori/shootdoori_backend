package com.shootdoori.match.controller;

import com.shootdoori.match.dto.MercenaryReviewRequestDto;
import com.shootdoori.match.dto.MercenaryReviewResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mercenary-reviews")
public class MercenaryReviewController {
    @GetMapping()
    public ResponseEntity<List<MercenaryReviewResponseDto>> getAll(@RequestParam Long profileId) {
        return null;
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<MercenaryReviewResponseDto> get(@RequestParam Long profileId, @PathVariable Long reviewId) {
        return null;
    }

    @PostMapping()
    public ResponseEntity<Void> post(@RequestBody MercenaryReviewRequestDto request) {
        return null;
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Void> update(@PathVariable Long reviewId, @RequestBody MercenaryReviewRequestDto request) {
        return null;
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> delete(@PathVariable Long reviewId) {
        return null;
    }
}
