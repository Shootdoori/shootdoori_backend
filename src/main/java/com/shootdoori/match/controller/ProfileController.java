package com.shootdoori.match.controller;

import com.shootdoori.match.dto.ProfileCreateRequest;
import com.shootdoori.match.dto.ProfileResponse;
import com.shootdoori.match.dto.ProfileUpdateRequest;
import com.shootdoori.match.resolver.LoginUser;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {
    @PostMapping
    public ResponseEntity<ProfileResponse> postProfile(
        @Valid @RequestBody ProfileCreateRequest request
    ) {
        return null;
    }

    @GetMapping
    public ResponseEntity<List<ProfileResponse>> getAllProfiles() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponse> getProfile(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/me")
    public ResponseEntity<ProfileResponse> getMyProfile(@LoginUser Long userId) {
        return null;
    }

    @PutMapping("/me")
    public ResponseEntity<ProfileResponse> updateProfile(
        @LoginUser Long userId,
        @Valid @RequestBody ProfileUpdateRequest request
    ) {
        return null;
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteProfile(@LoginUser Long userId) {
        return null;
    }
}