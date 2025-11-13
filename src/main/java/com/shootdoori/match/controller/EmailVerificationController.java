package com.shootdoori.match.controller;

import com.shootdoori.match.dto.MessageResponse;
import com.shootdoori.match.dto.SendCodeRequest;
import com.shootdoori.match.dto.VerifyCodeRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/signup/email")
public class EmailVerificationController {

    @PostMapping("/send-code")
    public ResponseEntity<MessageResponse> sendCode(@Valid @RequestBody SendCodeRequest request) {
        return new ResponseEntity<>(new MessageResponse("인증번호가 이메일로 발송되었습니다."),
                HttpStatus.OK);
    }

    @PostMapping("/verify-code")
    public ResponseEntity<MessageResponse> verifyCode(@Valid @RequestBody VerifyCodeRequest request) {
        return new ResponseEntity<>(new MessageResponse("이메일 인증이 완료되었습니다."),
                HttpStatus.OK);
    }
}