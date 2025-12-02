package com.shootdoori.match.joinWaiting.mapper;

import com.shootdoori.match.joinWaiting.domain.JoinWaiting;
import com.shootdoori.match.joinWaiting.dto.JoinWaitingResponseDto;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class JoinWaitingMapper {
    public JoinWaitingResponseDto toResponseDto(JoinWaiting joinWaiting) {
        LocalDateTime decidedAt = null;

        if (!joinWaiting.getStatus().isPending()) {
            decidedAt = joinWaiting.getUpdatedAt();
        }

        return new JoinWaitingResponseDto(
            joinWaiting.getId(),
            joinWaiting.getTeamId(),
            joinWaiting.getApplicantId(),
            joinWaiting.getStatus().getDisplayName(),
            joinWaiting.getDecisionReason(),
            joinWaiting.getProcessorId(),
            decidedAt,
            joinWaiting.getJoinWaitingType().getDisplayName()
        );
    }
}
