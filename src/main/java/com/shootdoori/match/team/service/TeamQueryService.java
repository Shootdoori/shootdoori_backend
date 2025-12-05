package com.shootdoori.match.team.service;

import com.shootdoori.match.exception.common.ErrorCode;
import com.shootdoori.match.exception.common.NotFoundException;
import com.shootdoori.match.team.domain.Team;
import com.shootdoori.match.team.domain.value.UniversityName;
import com.shootdoori.match.team.dto.TeamDetailResponseDto;
import com.shootdoori.match.team.mapper.TeamMapper;
import com.shootdoori.match.team.repository.TeamRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TeamQueryService {
    private TeamRepository teamRepository;
    private TeamMapper teamMapper;

    public TeamQueryService(TeamRepository teamRepository, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
    }

    public TeamDetailResponseDto findById(Long id) {
        Team team = findByIdForEntity(id);

        return teamMapper.toTeamDetailResponse(team);
    }

    public Team findByIdForEntity(Long id) {
        return teamRepository.findById(id).orElseThrow(() ->
            new NotFoundException(ErrorCode.TEAM_NOT_FOUND, String.valueOf(id)));
    }

    public Page<TeamDetailResponseDto> findAllByUniversity(int page, int size, String university) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("teamName").ascending());

        Page<Team> teamPage = teamRepository.findAllByUniversityName(UniversityName.of(university),
            pageable);

        return teamPage.map(teamMapper::toTeamDetailResponse);
    }
}
