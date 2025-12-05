package com.shootdoori.match.team.dto;

import java.time.LocalDateTime;

public record TeamMemberResponseDto(
    Long id,
    Long userId,
    String role,
    LocalDateTime createdAt
) {

}
