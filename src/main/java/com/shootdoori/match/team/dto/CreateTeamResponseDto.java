package com.shootdoori.match.team.dto;

public record CreateTeamResponseDto(
    Long teamId,
    String message,
    String teamUrl
) {

}
