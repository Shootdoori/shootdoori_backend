package com.shootdoori.match.team.mapper;

import com.shootdoori.match.team.domain.TeamMember;
import com.shootdoori.match.team.dto.TeamMemberResponseDto;
import org.springframework.stereotype.Component;

@Component
public class TeamMemberMapper {
    private TeamMemberMapper() {

    }

    public TeamMemberResponseDto toTeamMemberResponseDto(TeamMember teamMember) {
        return new TeamMemberResponseDto(teamMember.getId(),
            teamMember.getUserId(),
            teamMember.getRole().toString(),
            teamMember.getCreatedAt());
    }
}
