package com.shootdoori.match.team.service;

import com.shootdoori.match.dto.UpdateTeamMemberRequestDto;
import com.shootdoori.match.team.domain.Team;
import com.shootdoori.match.team.domain.TeamMember;
import com.shootdoori.match.team.domain.TeamMemberRole;
import com.shootdoori.match.team.dto.TeamMemberRequestDto;
import com.shootdoori.match.team.dto.TeamMemberResponseDto;
import com.shootdoori.match.team.mapper.TeamMemberMapper;
import com.shootdoori.match.team.repository.TeamMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TeamMemberCommandService {

    private final TeamQueryService teamQueryService;
    private final TeamMemberQueryService teamMemberQueryService;
    private final TeamMemberRepository teamMemberRepository;
    private final TeamMemberMapper teamMemberMapper;

    public TeamMemberCommandService(TeamQueryService teamQueryService,
        TeamMemberQueryService teamMemberQueryService,
        TeamMemberRepository teamMemberRepository,
        TeamMemberMapper teamMemberMapper) {
        this.teamQueryService = teamQueryService;
        this.teamMemberQueryService = teamMemberQueryService;
        this.teamMemberRepository = teamMemberRepository;
        this.teamMemberMapper = teamMemberMapper;
    }

    public TeamMemberResponseDto create(Long teamId, TeamMemberRequestDto requestDto, Long userId) {
        Team team = teamQueryService.findByIdForEntity(teamId);

        TeamMember teamMember = new TeamMember(team, requestDto.userId(),
            TeamMemberRole.VICE_LEADER.getDisplayName());

        return teamMemberMapper.toTeamMemberResponseDto(teamMemberRepository.save(teamMember));
    }

    public TeamMemberResponseDto update(Long teamId, Long targetUserId,
        UpdateTeamMemberRequestDto requestDto, Long loginUserId) {
        Team team = teamQueryService.findByIdForEntity(teamId);
        TeamMember loginMember = teamMemberQueryService.findByTeamIdAndUserIdForEntity(teamId,
            loginUserId);
        loginMember.validateJoinDecisionAuthority();

        TeamMember targetMember = teamMemberQueryService.findByTeamIdAndUserIdForEntity(teamId,
            targetUserId);
        targetMember.changeRole(requestDto.role());

        return teamMemberMapper.toTeamMemberResponseDto(targetMember);
    }

    public void leave(Long teamId, Long loginUserId) {
        TeamMember loginMember = teamMemberQueryService.findByTeamIdAndUserIdForEntity(teamId,
            loginUserId);

        teamMemberRepository.delete(loginMember);
    }

    public void kick(Long teamId, Long userId, Long loginUserId) {
        TeamMember loginMember = teamMemberQueryService.findByTeamIdAndUserIdForEntity(teamId, loginUserId);
        loginMember.validateJoinDecisionAuthority();

        TeamMember targetMember = teamMemberQueryService.findByTeamIdAndUserIdForEntity(teamId, userId);
        loginMember.validateKickAuthority(targetMember);

        teamMemberRepository.delete(targetMember);
    }

    public TeamMemberResponseDto delegateRole(Long teamId, Long loginUserId,
        Long targetMemberId, TeamMemberRole role) {
        TeamMember loginMember = teamMemberQueryService.findByTeamIdAndUserIdForEntity(teamId, loginUserId);
        TeamMember targetMember = teamMemberQueryService.findByIdForEntity(targetMemberId);

        loginMember.delegateRole(targetMember, role);
        return teamMemberMapper.toTeamMemberResponseDto(targetMember);
    }
}
