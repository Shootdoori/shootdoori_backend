package com.shootdoori.match.controller;

import com.shootdoori.match.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/password-reset")
public class PasswordResetController {

    private static final long SECONDS_PER_MINUTE = 60L;
    @PostMapping("/send-code")
    public ResponseEntity<PasswordResetResponse> sendVerificationCode(@Valid @RequestBody SendCodeRequest request) {
        return null;
    }

    @PostMapping("/verify-code")
    public ResponseEntity<PasswordTokenResponse> verifyCode(@Valid @RequestBody VerifyCodeRequest request) {
        return null;
    }

    @PostMapping("/confirm")
    public ResponseEntity<PasswordResetResponse> confirmPasswordReset(@Valid @RequestBody ResetPasswordRequest request) {
        return null;
    }
}