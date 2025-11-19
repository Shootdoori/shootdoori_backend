package com.shootdoori.match.team.service;

import com.shootdoori.match.team.dto.CreateTeamResponseDto;
import com.shootdoori.match.team.dto.TeamDetailResponseDto;
import com.shootdoori.match.team.mapper.TeamMapper;
import com.shootdoori.match.team.dto.TeamRequestDto;
import com.shootdoori.match.team.domain.Team;
import com.shootdoori.match.team.domain.value.UniversityName;
import com.shootdoori.match.exception.common.ErrorCode;
import com.shootdoori.match.exception.common.NotFoundException;
import com.shootdoori.match.team.repository.TeamRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

public class TeamService {

    private final TeamRepository TeamRepository;
    private final TeamMapper teamMapper;

    public TeamService(TeamRepository TeamRepository, TeamMapper teamMapper) {
        this.TeamRepository = TeamRepository;
        this.teamMapper = teamMapper;
    }

    @Transactional
    public CreateTeamResponseDto create(TeamRequestDto requestDto, Long userId) {
        Team team = teamMapper.toEntity(requestDto, userId);

        return teamMapper.toCreateTeamResponse(TeamRepository.save(team));
    }

    @Transactional(readOnly = true)
    public TeamDetailResponseDto findById(Long id) {
        Team team = TeamRepository.findById(id).orElseThrow(() ->
            new NotFoundException(ErrorCode.TEAM_NOT_FOUND, String.valueOf(id)));

        return teamMapper.toTeamDetailResponse(team);
    }

    @Transactional(readOnly = true)
    public Page<TeamDetailResponseDto> findAllByUniversity(int page, int size, String university) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("teamName").ascending());

        Page<Team> teamPage = TeamRepository.findAllByUniversityName(UniversityName.of(university),
            pageable);

        return teamPage.map(teamMapper::toTeamDetailResponse);
    }

    public TeamDetailResponseDto update(Long id, TeamRequestDto requestDto, Long userId) {
        Team team = TeamRepository.findById(id).orElseThrow(() ->
            new NotFoundException(ErrorCode.TEAM_NOT_FOUND, String.valueOf(id)));

        team.changeTeamInfo(requestDto.name(), requestDto.university(), requestDto.description(),
            userId);

        return teamMapper.toTeamDetailResponse(team);
    }

    public void delete(Long id, Long userId) {
        Team team = TeamRepository.findById(id).orElseThrow(() ->
            new NotFoundException(ErrorCode.TEAM_NOT_FOUND, String.valueOf(id)));

        TeamRepository.delete(team);
    }
}
