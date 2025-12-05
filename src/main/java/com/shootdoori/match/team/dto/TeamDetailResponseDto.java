package com.shootdoori.match.team.dto;

public record TeamDetailResponseDto(
    Long id,
    String name,
    String description,
    String university,
    String teamType,
    String createdAt
) {

}