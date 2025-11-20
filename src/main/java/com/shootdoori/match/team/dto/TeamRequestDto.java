package com.shootdoori.match.team.dto;

public record TeamRequestDto(
    String name,
    String description,
    String university,
    String skillLevel,
    String teamType
) {

}