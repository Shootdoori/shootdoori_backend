package com.shootdoori.match.dto;

import java.util.List;

public record LineupCreateRequestDto(Long matchId,
                                     List<LineupMemberRequestDto> members) {
}
