package com.shootdoori.match.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record RecruitmentResponse(
    Long recruitmentId,
    Long teamId,
    String teamName,
    String universityName,
    LocalDate matchDate,
    LocalTime matchTime,
    String message,
    String position,
    String skillLevel,
    String recruitmentStatus,
    LocalDateTime createdAt
) {

}
