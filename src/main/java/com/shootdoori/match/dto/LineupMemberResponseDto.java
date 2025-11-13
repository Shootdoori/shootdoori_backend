package com.shootdoori.match.dto;

import java.time.LocalDateTime;

public record LineupMemberResponseDto(Long id,
                                      Long lineupId,
                                      Long teamMemberId,
                                      Long teamId,
                                      String userName,
                                      Boolean isStarter,
                                      LocalDateTime createdAt,
                                      LocalDateTime updatedAt) {
}
