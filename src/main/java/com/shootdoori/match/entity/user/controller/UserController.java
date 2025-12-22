package com.shootdoori.match.entity.user.controller;

import com.shootdoori.match.entity.user.dto.UserCreateRequest;
import com.shootdoori.match.entity.user.dto.ProfileResponse;
import com.shootdoori.match.entity.user.dto.ProfileUpdateRequest;
import com.shootdoori.match.entity.user.service.UserCommandService;
import com.shootdoori.match.resolver.LoginUser;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
public class UserController {
    private final UserCommandService userCommandService;

    public UserController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    @PostMapping
    public ResponseEntity<ProfileResponse> postProfile(
        @Valid @RequestBody UserCreateRequest request
    ) {
        return new ResponseEntity<>(userCommandService.createUser(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponse> getProfile(
        @PathVariable Long id
    ) {
        return new ResponseEntity<>(userCommandService.getUser(id), HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<ProfileResponse> getMyProfile(
        @LoginUser Long id
    ) {
        return new ResponseEntity<>(userCommandService.getUser(id), HttpStatus.OK);
    }

    @PutMapping("/me")
    public ResponseEntity<ProfileResponse> updateProfile(
        @LoginUser Long userId,
        @Valid @RequestBody ProfileUpdateRequest request
    ) {
        return new ResponseEntity<>(userCommandService.updateUser(userId, request), HttpStatus.OK);
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteProfile(
        @LoginUser Long userId
    ) {
        userCommandService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}