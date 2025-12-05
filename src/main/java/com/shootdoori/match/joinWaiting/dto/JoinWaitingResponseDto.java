package com.shootdoori.match.joinWaiting.dto;

import java.time.LocalDateTime;

public record JoinWaitingResponseDto(Long id,
                                     Long teamId,
                                     Long applicantId,
                                     String status,
                                     String decisionReason,
                                     Long processorId,
                                     LocalDateTime decidedAt,
                                     String joinWaitingType) {

}
