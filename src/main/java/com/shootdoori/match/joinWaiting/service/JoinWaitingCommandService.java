package com.shootdoori.match.joinWaiting.service;

import com.shootdoori.match.joinWaiting.domain.JoinWaiting;
import com.shootdoori.match.joinWaiting.dto.JoinWaitingApproveRequestDto;
import com.shootdoori.match.joinWaiting.dto.JoinWaitingCancelRequestDto;
import com.shootdoori.match.joinWaiting.dto.JoinWaitingRejectRequestDto;
import com.shootdoori.match.joinWaiting.dto.JoinWaitingRequestDto;
import com.shootdoori.match.joinWaiting.dto.JoinWaitingResponseDto;
import com.shootdoori.match.joinWaiting.mapper.JoinWaitingMapper;
import com.shootdoori.match.joinWaiting.repository.JoinWaitingRepository;
import com.shootdoori.match.team.service.TeamMemberQueryService;
import com.shootdoori.match.team.service.TeamQueryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JoinWaitingCommandService {

    private final TeamQueryService teamQueryService;
    private final TeamMemberQueryService teamMemberQueryService;
    private final JoinWaitingQueryService joinWaitingQueryService;
    private final JoinWaitingRepository joinWaitingRepository;
    private final JoinWaitingMapper joinWaitingMapper;

    public JoinWaitingCommandService(TeamQueryService teamQueryService,
        TeamMemberQueryService teamMemberQueryService,
        JoinWaitingQueryService joinWaitingQueryService,
        JoinWaitingRepository joinWaitingRepository, JoinWaitingMapper joinWaitingMapper) {
        this.teamQueryService = teamQueryService;
        this.teamMemberQueryService = teamMemberQueryService;
        this.joinWaitingQueryService = joinWaitingQueryService;
        this.joinWaitingRepository = joinWaitingRepository;
        this.joinWaitingMapper = joinWaitingMapper;
    }

    public JoinWaitingResponseDto create(Long teamId, Long loginUserId,
        JoinWaitingRequestDto requestDto) {

        teamQueryService.findByIdForEntity(teamId);
        JoinWaiting joinWaiting = new JoinWaiting(teamId, loginUserId, requestDto.message(),
            requestDto.type());

        return joinWaitingMapper.toResponseDto(joinWaitingRepository.save(joinWaiting));
    }

    public JoinWaitingResponseDto approve(Long teamId, Long joinWaitingId, Long loginUserId,
        JoinWaitingApproveRequestDto requestDto) {

        JoinWaiting joinWaiting = joinWaitingQueryService.findByIdForEntity(joinWaitingId);

        teamMemberQueryService.validateLeaderOrViceLeader(teamId, loginUserId);

        joinWaiting.approve(loginUserId, requestDto.decisionReason());

        return joinWaitingMapper.toResponseDto(joinWaiting);
    }

    public JoinWaitingResponseDto reject(Long teamId, Long joinWaitingId, Long loginUserId,
        JoinWaitingRejectRequestDto requestDto) {

        JoinWaiting joinWaiting = joinWaitingQueryService.findByIdForEntity(joinWaitingId);

        teamMemberQueryService.validateLeaderOrViceLeader(teamId, loginUserId);

        joinWaiting.reject(loginUserId, requestDto.decisionReason());

        return joinWaitingMapper.toResponseDto(joinWaiting);
    }

    public JoinWaitingResponseDto cancel(Long teamId, Long joinWaitingId, Long loginUserId,
        JoinWaitingCancelRequestDto requestDto) {

        JoinWaiting joinWaiting = joinWaitingQueryService.findByIdForEntity(joinWaitingId);

        joinWaiting.validateApplicant(loginUserId);

        joinWaiting.cancel(loginUserId, requestDto.decisionReason());

        return joinWaitingMapper.toResponseDto(joinWaiting);
    }
}
