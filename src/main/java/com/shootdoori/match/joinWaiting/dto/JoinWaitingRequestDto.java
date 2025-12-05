package com.shootdoori.match.joinWaiting.dto;

import com.shootdoori.match.joinWaiting.domain.JoinWaitingType;

public record JoinWaitingRequestDto(
    String message,
    JoinWaitingType type
) {

}
