package com.shootdoori.match.team.service;

import com.shootdoori.match.exception.common.ErrorCode;
import com.shootdoori.match.exception.common.NotFoundException;
import com.shootdoori.match.team.domain.TeamMember;
import com.shootdoori.match.team.dto.TeamMemberResponseDto;
import com.shootdoori.match.team.mapper.TeamMemberMapper;
import com.shootdoori.match.team.repository.TeamMemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TeamMemberQueryService {

    private final TeamMemberRepository teamMemberRepository;
    private final TeamMemberMapper teamMemberMapper;

    public TeamMemberQueryService(TeamMemberRepository teamMemberRepository,
        TeamMemberMapper teamMemberMapper) {
        this.teamMemberRepository = teamMemberRepository;
        this.teamMemberMapper = teamMemberMapper;
    }

    public TeamMemberResponseDto findByTeamIdAndUserId(Long teamId, Long userId) {
        TeamMember teamMember = findByTeamIdAndUserIdForEntity(teamId, userId);

        return teamMemberMapper.toTeamMemberResponseDto(teamMember);
    }

    public TeamMember findByTeamIdAndUserIdForEntity(Long teamId, Long userId) {
        return teamMemberRepository.findByTeamIdAndUserId(teamId, userId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.TEAM_MEMBER_NOT_FOUND));
    }

    public Page<TeamMemberResponseDto> findAllByTeamId(Long teamId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());

        Page<TeamMember> teamMemberPage = teamMemberRepository.findAllByTeamId(teamId,
            pageable);

        return teamMemberPage.map(teamMemberMapper::toTeamMemberResponseDto);
    }

    public Slice<TeamMemberResponseDto> findSliceByTeamId(Long teamId, Long cursorId, int size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by("id").ascending());

        Slice<TeamMember> teammemberSlice = teamMemberRepository.findSliceByTeam_Id(teamId,
            cursorId, pageable);

        return teammemberSlice.map(teamMemberMapper::toTeamMemberResponseDto);
    }

    public TeamMember findByIdForEntity(Long teamMemberId) {
        return teamMemberRepository.findById(teamMemberId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.TEAM_MEMBER_NOT_FOUND));
    }

    public TeamMember findByUserIdForEntity(Long userId) {
        return teamMemberRepository.findByUserId(userId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.TEAM_MEMBER_NOT_FOUND));
    }

    public void validateLeaderOrViceLeader(Long teamId, Long userId) {
        TeamMember teamMember = teamMemberRepository.findByTeamIdAndUserId(teamId, userId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.TEAM_MEMBER_NOT_FOUND));

        teamMember.validateJoinDecisionAuthority();
    }
}
