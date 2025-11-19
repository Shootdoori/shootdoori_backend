package com.shootdoori.match.team.mapper;

import com.shootdoori.match.team.domain.Team;
import com.shootdoori.match.team.domain.TeamType;
import com.shootdoori.match.team.dto.CreateTeamResponseDto;
import com.shootdoori.match.team.dto.TeamDetailResponseDto;
import com.shootdoori.match.team.dto.TeamRequestDto;

public class TeamMapper {

    private TeamMapper() {
    }

    public Team toEntity(TeamRequestDto requestDto, Long captainId) {
        return Team.of(
            captainId,
            requestDto.name(),
            requestDto.university(),
            parseToTeamType(requestDto.teamType()),
            requestDto.description()
        );
    }

    public CreateTeamResponseDto toCreateTeamResponse(Team team) {
        Long id = team.getId();
        return new CreateTeamResponseDto(id, "팀이 성공적으로 생성되었습니다.", "/api/teams/" + id);
    }

    public TeamDetailResponseDto toTeamDetailResponse(Team team) {
        return new TeamDetailResponseDto(
            team.getId(),
            team.getTeamName().teamName(),
            team.getDescription().description(),
            team.getUniversityName().universityName(),
            team.getTeamType().getDisplayName(),
            team.getCreatedAt().toString());
    }

    private static TeamType parseToTeamType(String value) {
        if (value == null) {
            return TeamType.OTHER;
        }

        return TeamType.fromDisplayName(value);
    }
}

