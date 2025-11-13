package com.shootdoori.match.dto;

public record TeamDetailResponseDto(
    Long id,
    String name,
    String description,
    String university,
    Integer memberCount,
    String createdAt
) {

}