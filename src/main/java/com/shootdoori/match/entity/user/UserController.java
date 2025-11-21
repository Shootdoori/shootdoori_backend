package com.shootdoori.match.entity.user;

import com.shootdoori.match.entity.user.dto.UserCreateRequest;
import com.shootdoori.match.entity.user.dto.ProfileResponse;
import com.shootdoori.match.entity.user.dto.ProfileUpdateRequest;
import com.shootdoori.match.entity.user.service.UserService;
import com.shootdoori.match.resolver.LoginUser;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ProfileResponse> postProfile(
        @Valid @RequestBody UserCreateRequest request
    ) {
        return new ResponseEntity<>(userService.createUser(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponse> getProfile(
        @PathVariable Long id
    ) {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<ProfileResponse> getMyProfile(
        @LoginUser Long id
    ) {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @PutMapping("/me")
    public ResponseEntity<ProfileResponse> updateProfile(
        @LoginUser Long userId,
        @Valid @RequestBody ProfileUpdateRequest request
    ) {
        return new ResponseEntity<>(userService.updateUser(userId, request), HttpStatus.OK);
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteProfile(
        @LoginUser Long userId
    ) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}