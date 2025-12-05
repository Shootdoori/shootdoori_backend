package com.shootdoori.match.entity.user.dto;

import java.time.LocalDateTime;

public record ProfileResponse(
    String name,
    String skillLevel,
    String email,
    String kakaoTalkId,
    String position,
    String university,
    String department,
    String studentYear,
    String bio,
    LocalDateTime createdAt
) {
}
