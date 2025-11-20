package com.shootdoori.match.team.service;

import com.shootdoori.match.team.domain.Team;
import com.shootdoori.match.team.dto.CreateTeamResponseDto;
import com.shootdoori.match.team.dto.TeamDetailResponseDto;
import com.shootdoori.match.team.dto.TeamRequestDto;
import com.shootdoori.match.team.mapper.TeamMapper;
import com.shootdoori.match.team.repository.TeamRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class TeamCommandService {

    private final TeamQueryService teamQueryService;
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    public TeamCommandService(TeamQueryService teamQueryService, TeamRepository teamRepository, TeamMapper teamMapper) {
        this.teamQueryService = teamQueryService;
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
    }

    public CreateTeamResponseDto create(TeamRequestDto requestDto, Long userId) {
        Team team = teamMapper.toEntity(requestDto, userId);

        return teamMapper.toCreateTeamResponse(teamRepository.save(team));
    }

    public TeamDetailResponseDto update(Long id, TeamRequestDto requestDto, Long userId) {
        Team team = teamQueryService.findByIdForEntity(id);

        team.changeTeamInfo(requestDto.name(), requestDto.university(), requestDto.description(),
            userId);

        return teamMapper.toTeamDetailResponse(team);
    }

    public void delete(Long id, Long userId) {
        Team team = teamQueryService.findByIdForEntity(id);

        teamRepository.delete(team);
    }
}
