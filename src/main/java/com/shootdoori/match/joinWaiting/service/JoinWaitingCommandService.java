package com.shootdoori.match.joinWaiting.service;

import com.shootdoori.match.joinWaiting.domain.JoinWaiting;
import com.shootdoori.match.joinWaiting.dto.JoinWaitingRequestDto;
import com.shootdoori.match.joinWaiting.dto.JoinWaitingResponseDto;
import com.shootdoori.match.joinWaiting.mapper.JoinWaitingMapper;
import com.shootdoori.match.joinWaiting.repository.JoinWaitingRepository;
import com.shootdoori.match.team.service.TeamQueryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JoinWaitingCommandService {

    private final TeamQueryService teamQueryService;
    private final JoinWaitingRepository joinWaitingRepository;
    private final JoinWaitingMapper joinWaitingMapper;

    public JoinWaitingCommandService(TeamQueryService teamQueryService,
        JoinWaitingRepository joinWaitingRepository, JoinWaitingMapper joinWaitingMapper) {
        this.teamQueryService = teamQueryService;
        this.joinWaitingRepository = joinWaitingRepository;
        this.joinWaitingMapper = joinWaitingMapper;
    }

    public JoinWaitingResponseDto create(Long teamId, Long loginUserId,
        JoinWaitingRequestDto requestDto) {

        teamQueryService.findByIdForEntity(teamId);
        JoinWaiting joinWaiting = JoinWaiting.of(teamId, loginUserId, requestDto.message(),
            requestDto.type());

        return joinWaitingMapper.toResponseDto(joinWaitingRepository.save(joinWaiting));
    }
}
