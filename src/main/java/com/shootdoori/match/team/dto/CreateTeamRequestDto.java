package com.shootdoori.match.team.dto;

public record CreateTeamRequestDto(
    String name,
    String description,
    String university,
    String teamType
) {

}