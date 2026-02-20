package com.shootdoori.match.dto;

public record MatchApplicationRequestDto(
    String requestMessage,
    Long lineupId
) {
}
