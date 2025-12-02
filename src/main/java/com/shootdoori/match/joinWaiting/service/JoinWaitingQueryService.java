package com.shootdoori.match.joinWaiting.service;

import com.shootdoori.match.joinWaiting.domain.JoinWaitingStatus;
import com.shootdoori.match.joinWaiting.domain.JoinWaitingType;
import com.shootdoori.match.joinWaiting.dto.JoinWaitingResponseDto;
import com.shootdoori.match.joinWaiting.mapper.JoinWaitingMapper;
import com.shootdoori.match.joinWaiting.repository.JoinWaitingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class JoinWaitingQueryService {

    private final JoinWaitingRepository joinWaitingRepository;

    private final JoinWaitingMapper joinWaitingMapper;

    public JoinWaitingQueryService(JoinWaitingRepository joinWaitingRepository,
        JoinWaitingMapper joinWaitingMapper) {
        this.joinWaitingRepository = joinWaitingRepository;
        this.joinWaitingMapper = joinWaitingMapper;
    }

    public Page<JoinWaitingResponseDto> findPending(Long teamId, JoinWaitingStatus status,
        JoinWaitingType type, Pageable pageable) {
        return joinWaitingRepository.findByTeamIdAndStatusAndJoinWaitingType(teamId, status, type,
            pageable).map(joinWaitingMapper::toResponseDto);
    }

    public Page<JoinWaitingResponseDto> findAllByApplicantIdAndJoinWaitingType(Long applicantId,
        JoinWaitingType type, Pageable pageable) {
        return joinWaitingRepository.findAllByApplicantIdAndJoinWaitingType(applicantId, type, pageable)
            .map(joinWaitingMapper::toResponseDto);
    }
}
