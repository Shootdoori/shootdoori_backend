package com.shootdoori.match.dto;

import com.shootdoori.match.coordination.domain.Lineup;
import com.shootdoori.match.coordination.domain.LineupMember;
import com.shootdoori.match.team.domain.TeamMember;
import com.shootdoori.match.team.service.TeamMemberQueryService;
import com.shootdoori.match.user.domain.User;
import com.shootdoori.match.user.service.UserQueryService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record LineupMemberResponseDto(Long id,
                                      Long lineupId,
                                      Long teamMemberId,
                                      Long teamId,
                                      String userName,
                                      Boolean isStarter,
                                      LocalDateTime createdAt,
                                      LocalDateTime updatedAt) {
    
    public static LineupMemberResponseDto from(LineupMember member, Lineup lineup, User user) {
        return new LineupMemberResponseDto(
            member.getId(),
            lineup.getId(),
            member.getTeamMemberId(),
            lineup.getTeamId(),
            user.getName().value(),
            member.isStarter(),
            member.getCreatedAt(),
            member.getUpdatedAt()
        );
    }
    
    public static List<LineupMemberResponseDto> fromLineup(Lineup lineup, 
            TeamMemberQueryService teamMemberQueryService, 
            UserQueryService userQueryService) {
        List<LineupMemberResponseDto> responseDtos = new ArrayList<>();
        
        for (LineupMember member : lineup.getMembers().getMembers()) {
            TeamMember teamMember = teamMemberQueryService.findByIdForEntity(member.getTeamMemberId());
            User user = userQueryService.findByIdForEntity(teamMember.getUserId());
            
            responseDtos.add(from(member, lineup, user));
        }
        
        return responseDtos;
    }
}
